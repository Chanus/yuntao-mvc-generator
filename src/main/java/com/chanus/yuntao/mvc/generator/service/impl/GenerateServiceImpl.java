/*
 * Copyright (c) 2020 Chanus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chanus.yuntao.mvc.generator.service.impl;

import com.chanus.yuntao.mvc.generator.service.GenerateService;
import com.chanus.yuntao.mvc.generator.vo.*;
import com.chanus.yuntao.utils.core.CharsetUtils;
import com.chanus.yuntao.utils.core.IOUtils;
import com.chanus.yuntao.utils.core.StreamUtils;
import com.chanus.yuntao.utils.core.encrypt.AESUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 项目构建相关接口实现
 *
 * @author Chanus
 * @date 2020-07-01 22:30:15
 * @since 1.0.0
 */
@Service
public class GenerateServiceImpl implements GenerateService {
    @Override
    public byte[] generate(ProjectVO projectVO) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        // 项目构建
        if (projectVO.getProjectType().equals(ProjectTypeEnum.SIMPLE))
            generateSimpleProject((SimpleProjectVO) projectVO, zip);
        else if (projectVO.getProjectType().equals(ProjectTypeEnum.MULTI))
            generateMultiProject((MultiProjectVO) projectVO, zip);
        else if (projectVO.getProjectType().equals(ProjectTypeEnum.MULTI_SPLITTING))
            generateMultiSplittingProject((MultiSplittingProjectVO) projectVO, zip);

        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 基于 yuntao-manager 构建一个简单的单模块项目
     */
    private void generateSimpleProject(SimpleProjectVO simpleProjectVO, ZipOutputStream zip) {
        initVelocity();

        String jdbcPassword = simpleProjectVO.getJdbc().getPassword();
        simpleProjectVO.getJdbc().setPassword(AESUtils.encrypt(jdbcPassword, "ChanusYuntaoJDBC"));

        // 封装模板数据
        Map<String, Object> params = new HashMap<>();
        params.put("projectVO", simpleProjectVO);
        params.put("multiModule", "0");
        params.put("apiPackage", simpleProjectVO.getPackageName());
        params.put("webPackage", simpleProjectVO.getPackageName());
        params.put("scanPackage", simpleProjectVO.getPackageName());
        VelocityContext context = new VelocityContext(params);

        StringWriter stringWriter = new StringWriter();
        try {
            // 项目 pom.xml
            Template template = Velocity.getTemplate("velocity/templates/simple/pom.xml.vm", "UTF-8");
            template.merge(context, stringWriter);
            zip.putNextEntry(new ZipEntry(simpleProjectVO.getName() + "/pom.xml"));
            StreamUtils.write(zip, CharsetUtils.CHARSET_UTF_8, stringWriter.toString());
            // 项目 java 类目录
            String javaPath = simpleProjectVO.getName() + "/src/main/java/";
            String packagePath = javaPath + simpleProjectVO.getPackagePath() + File.separator;
            zip.putNextEntry(new ZipEntry(packagePath + "controller/"));
            zip.putNextEntry(new ZipEntry(packagePath + "service/impl/"));
            zip.putNextEntry(new ZipEntry(packagePath + "model/"));
            zip.putNextEntry(new ZipEntry(packagePath + "mapper/"));
            // 项目资源目录
            String resourcesPath = simpleProjectVO.getName() + "/src/main/resources/";
            zip.putNextEntry(new ZipEntry(resourcesPath + simpleProjectVO.getPackagePath() + "/mapper/"));
            createWebSources(context, zip, resourcesPath);
            // 前端资源目录
            String webappPath = simpleProjectVO.getName() + "/src/main/webapp/";
            createWebappSources(context, zip, webappPath, simpleProjectVO.getEnableRedis());
            // web.xml
            createWebXML(context, zip, webappPath + "WEB-INF/");
            // 项目测试资源目录
            createTestFolder(zip, simpleProjectVO.getName());

            zip.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(stringWriter);
        }
    }

    /**
     * 基于 yuntao-manager 构建一个多模块项目
     */
    private void generateMultiProject(MultiProjectVO multiProjectVO, ZipOutputStream zip) {
        initVelocity();

        String jdbcPassword = multiProjectVO.getJdbc().getPassword();
        multiProjectVO.getJdbc().setPassword(AESUtils.encrypt(jdbcPassword, "ChanusYuntaoJDBC"));

        // 封装模板数据
        Map<String, Object> params = new HashMap<>();
        params.put("projectVO", multiProjectVO);
        params.put("multiModule", "0");
        params.put("apiPackage", multiProjectVO.getPackageName());
        params.put("webPackage", multiProjectVO.getPackageName());
        params.put("scanPackage", multiProjectVO.getPackageName());
        VelocityContext context = new VelocityContext(params);

        StringWriter stringWriter = new StringWriter();
        try {
            // 父项目 pom.xml
            Template template = Velocity.getTemplate("velocity/templates/multi/pom-parent.xml.vm", "UTF-8");
            template.merge(context, stringWriter);
            zip.putNextEntry(new ZipEntry(multiProjectVO.getName() + "/pom.xml"));
            StreamUtils.write(zip, CharsetUtils.CHARSET_UTF_8, stringWriter.toString());
            // 后台管理项目 pom.xml
            stringWriter = new StringWriter();
            template = Velocity.getTemplate("velocity/templates/multi/pom-manager.xml.vm", "UTF-8");
            template.merge(context, stringWriter);
            zip.putNextEntry(new ZipEntry(multiProjectVO.getName() + File.separator + multiProjectVO.getManagerArtifactId() + "/pom.xml"));
            StreamUtils.write(zip, CharsetUtils.CHARSET_UTF_8, stringWriter.toString());
            // 项目 java 类目录
            String javaPath = multiProjectVO.getName() + File.separator + multiProjectVO.getManagerArtifactId() + "/src/main/java/";
            String packagePath = javaPath + multiProjectVO.getPackagePath() + File.separator;
            zip.putNextEntry(new ZipEntry(packagePath + "controller/"));
            zip.putNextEntry(new ZipEntry(packagePath + "service/impl/"));
            zip.putNextEntry(new ZipEntry(packagePath + "model/"));
            zip.putNextEntry(new ZipEntry(packagePath + "mapper/"));
            // 项目资源目录
            String resourcesPath = multiProjectVO.getName() + File.separator + multiProjectVO.getManagerArtifactId() + "/src/main/resources/";
            zip.putNextEntry(new ZipEntry(resourcesPath + multiProjectVO.getPackagePath() + "/mapper/"));
            createWebSources(context, zip, resourcesPath);
            // 前端资源目录
            String webappPath = multiProjectVO.getName() + File.separator + multiProjectVO.getManagerArtifactId() + "/src/main/webapp/";
            createWebappSources(context, zip, webappPath, multiProjectVO.getEnableRedis());
            // web.xml
            createWebXML(context, zip, webappPath + "WEB-INF/");
            // 项目测试资源目录
            createTestFolder(zip, multiProjectVO.getName() + File.separator + multiProjectVO.getManagerArtifactId());

            zip.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(stringWriter);
        }
    }

    /**
     * 基于 yuntao-manager 构建一个多模块项目，web 模块分离
     */
    private void generateMultiSplittingProject(MultiSplittingProjectVO multiSplittingProjectVO, ZipOutputStream zip) {
        initVelocity();

        String jdbcPassword = multiSplittingProjectVO.getJdbc().getPassword();
        multiSplittingProjectVO.getJdbc().setPassword(AESUtils.encrypt(jdbcPassword, "ChanusYuntaoJDBC"));

        // 封装模板数据
        Map<String, Object> params = new HashMap<>();
        params.put("projectVO", multiSplittingProjectVO);
        params.put("multiModule", "1");
        params.put("apiPackage", multiSplittingProjectVO.getApiPackageName());
        params.put("webPackage", multiSplittingProjectVO.getWebPackageName());
        params.put("scanPackage", multiSplittingProjectVO.getWebPackageName());
        VelocityContext context = new VelocityContext(params);

        StringWriter stringWriter = new StringWriter();
        try {
            // 父项目 pom.xml
            Template template = Velocity.getTemplate("velocity/templates/multi-splitting/pom-parent.xml.vm", "UTF-8");
            template.merge(context, stringWriter);
            zip.putNextEntry(new ZipEntry(multiSplittingProjectVO.getName() + "/pom.xml"));
            StreamUtils.write(zip, CharsetUtils.CHARSET_UTF_8, stringWriter.toString());
            // 后台管理 api 模块 pom.xml
            stringWriter = new StringWriter();
            template = Velocity.getTemplate("velocity/templates/multi-splitting/pom-api.xml.vm", "UTF-8");
            template.merge(context, stringWriter);
            zip.putNextEntry(new ZipEntry(multiSplittingProjectVO.getName() + File.separator + multiSplittingProjectVO.getApiArtifactId() + "/pom.xml"));
            StreamUtils.write(zip, CharsetUtils.CHARSET_UTF_8, stringWriter.toString());
            // 后台管理 api 模块 java 类目录
            String apiJavaPath = multiSplittingProjectVO.getName() + File.separator + multiSplittingProjectVO.getApiArtifactId() + "/src/main/java/";
            String apiPackagePath = apiJavaPath + multiSplittingProjectVO.getApiPackagePath() + File.separator;
            zip.putNextEntry(new ZipEntry(apiPackagePath + "service/impl/"));
            zip.putNextEntry(new ZipEntry(apiPackagePath + "model/"));
            zip.putNextEntry(new ZipEntry(apiPackagePath + "mapper/"));
            // 后台管理 api 模块资源目录
            String apiResourcesPath = multiSplittingProjectVO.getName() + File.separator + multiSplittingProjectVO.getApiArtifactId() + "/src/main/resources/";
            zip.putNextEntry(new ZipEntry(apiResourcesPath + multiSplittingProjectVO.getApiPackagePath() + "/mapper/"));
            // 后台管理 api 模块测试资源目录
            createTestFolder(zip, multiSplittingProjectVO.getName() + File.separator + multiSplittingProjectVO.getApiArtifactId());

            // 后台管理 web 模块 pom.xml
            stringWriter = new StringWriter();
            template = Velocity.getTemplate("velocity/templates/multi-splitting/pom-web.xml.vm", "UTF-8");
            template.merge(context, stringWriter);
            zip.putNextEntry(new ZipEntry(multiSplittingProjectVO.getName() + File.separator + multiSplittingProjectVO.getWebArtifactId() + "/pom.xml"));
            StreamUtils.write(zip, CharsetUtils.CHARSET_UTF_8, stringWriter.toString());
            // 后台管理 web 模块 java 类目录
            String webJavaPath = multiSplittingProjectVO.getName() + File.separator + multiSplittingProjectVO.getWebArtifactId() + "/src/main/java/";
            String webPackagePath = webJavaPath + multiSplittingProjectVO.getWebPackagePath() + File.separator;
            zip.putNextEntry(new ZipEntry(webPackagePath + "controller/"));

            // 后台管理 web 模块资源目录
            String webResourcesPath = multiSplittingProjectVO.getName() + File.separator + multiSplittingProjectVO.getWebArtifactId() + "/src/main/resources/";
            createWebSources(context, zip, webResourcesPath);
            // 前端资源目录
            String webappPath = multiSplittingProjectVO.getName() + File.separator + multiSplittingProjectVO.getWebArtifactId() + "/src/main/webapp/";
            createWebappSources(context, zip, webappPath, multiSplittingProjectVO.getEnableRedis());
            // web.xml
            createWebXML(context, zip, webappPath + "WEB-INF/");
            // 项目测试资源目录
            createTestFolder(zip, multiSplittingProjectVO.getName() + File.separator + multiSplittingProjectVO.getWebArtifactId());

            zip.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(stringWriter);
        }
    }

    /**
     * 设置 velocity 资源加载器
     */
    private void initVelocity() {
        Properties properties = new Properties();
        properties.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(properties);
    }

    private void createWebSources(VelocityContext context, ZipOutputStream zip, String webResourcesPath) throws IOException {
        StringWriter stringWriter = new StringWriter();
        Template template = Velocity.getTemplate("velocity/templates/spring-mvc-context.xml.vm", "UTF-8");
        template.merge(context, stringWriter);
        zip.putNextEntry(new ZipEntry(webResourcesPath + "spring-mvc-context.xml"));
        StreamUtils.write(zip, CharsetUtils.CHARSET_UTF_8, stringWriter.toString());
        stringWriter = new StringWriter();
        template = Velocity.getTemplate("velocity/templates/logback.xml.vm", "UTF-8");
        template.merge(context, stringWriter);
        zip.putNextEntry(new ZipEntry(webResourcesPath + "logback.xml"));
        StreamUtils.write(zip, CharsetUtils.CHARSET_UTF_8, stringWriter.toString());
    }

    /**
     * 创建 webapp 目录下的资源文件
     *
     * @param context     VelocityContext
     * @param zip         zip
     * @param webappPath  webapp 路径
     * @param enableRedis 是否启用 Redis 缓存数据库
     * @throws IOException IOException
     */
    private void createWebappSources(VelocityContext context, ZipOutputStream zip, String webappPath, boolean enableRedis) throws IOException {
        // 前端资源目录
        zip.putNextEntry(new ZipEntry(webappPath + "resources/css/"));
        zip.putNextEntry(new ZipEntry(webappPath + "resources/js/"));
        zip.putNextEntry(new ZipEntry(webappPath + "resources/images/"));
        zip.putNextEntry(new ZipEntry(webappPath + "resources/lib/"));
        String webinfPath = webappPath + "WEB-INF/";
        zip.putNextEntry(new ZipEntry(webinfPath + "view/"));
        // config.properties
        StringWriter stringWriter = new StringWriter();
        Template template = Velocity.getTemplate("velocity/templates/config.properties.vm", "UTF-8");
        template.merge(context, stringWriter);
        zip.putNextEntry(new ZipEntry(webinfPath + "classes/config.properties"));
        StreamUtils.write(zip, CharsetUtils.CHARSET_UTF_8, stringWriter.toString());
        // jdbc.properties
        stringWriter = new StringWriter();
        template = Velocity.getTemplate("velocity/templates/jdbc.properties.vm", "UTF-8");
        template.merge(context, stringWriter);
        zip.putNextEntry(new ZipEntry(webinfPath + "classes/jdbc.properties"));
        StreamUtils.write(zip, CharsetUtils.CHARSET_UTF_8, stringWriter.toString());
        // redis.properties
        if (enableRedis) {
            stringWriter = new StringWriter();
            template = Velocity.getTemplate("velocity/templates/redis.properties.vm", "UTF-8");
            template.merge(context, stringWriter);
            zip.putNextEntry(new ZipEntry(webinfPath + "classes/redis.properties"));
            StreamUtils.write(zip, CharsetUtils.CHARSET_UTF_8, stringWriter.toString());
        }
    }

    /**
     * 创建 web.xml 文件
     *
     * @param context    VelocityContext
     * @param zip        zip
     * @param webinfPath WEB-INF 路径
     * @throws IOException IOException
     */
    private void createWebXML(VelocityContext context, ZipOutputStream zip, String webinfPath) throws IOException {
        StringWriter stringWriter = new StringWriter();
        Template template = Velocity.getTemplate("velocity/templates/web.xml.vm", "UTF-8");
        template.merge(context, stringWriter);
        zip.putNextEntry(new ZipEntry(webinfPath + "web.xml"));
        StreamUtils.write(zip, CharsetUtils.CHARSET_UTF_8, stringWriter.toString());
    }

    /**
     * 创建测试资源目录
     *
     * @param zip         zip
     * @param projectPath 项目路径
     * @throws IOException IOException
     */
    private void createTestFolder(ZipOutputStream zip, String projectPath) throws IOException {
        zip.putNextEntry(new ZipEntry(projectPath + "/src/test/java/"));
        zip.putNextEntry(new ZipEntry(projectPath + "/src/test/resources/"));
    }
}
