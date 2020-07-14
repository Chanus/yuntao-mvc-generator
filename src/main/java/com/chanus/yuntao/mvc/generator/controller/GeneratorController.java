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
package com.chanus.yuntao.mvc.generator.controller;

import com.chanus.yuntao.mvc.generator.service.GenerateService;
import com.chanus.yuntao.mvc.generator.vo.*;
import com.chanus.yuntao.utils.core.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 项目构建
 *
 * @author Chanus
 * @date 2020-07-01 22:17:47
 * @since 1.0.0
 */
@RestController
public class GeneratorController {
    @Autowired
    private GenerateService generateService;

    /**
     * 单模块项目构建
     *
     * @param response        HttpServletResponse
     * @param simpleProjectVO 项目构建信息
     */
    @GetMapping(value = "generate-simple", produces = "application/json; charset=utf-8")
    public void generateSimple(HttpServletResponse response, SimpleProjectVO simpleProjectVO) {
        simpleProjectVO.setProjectType(ProjectTypeEnum.SIMPLE);
        simpleProjectVO.setYuntaoVersion("0.2.3");
        simpleProjectVO.setName("yuntao-simple");
        simpleProjectVO.setGroupId("com.chanus");
        simpleProjectVO.setArtifactId("yuntao-simple");
        simpleProjectVO.setVersion("1.0.0");
        simpleProjectVO.setProjectName("云道管理系统简单版");
        simpleProjectVO.setAuthor("Chanus");
        simpleProjectVO.setCopyright("Copyright © 2020 Chanus. All Rights Reserved.");
        simpleProjectVO.setPackageName("com.chanus.yuntao.simple.manager");

        JdbcConfig jdbc = new JdbcConfig();
        jdbc.setDriverClassName("com.mysql.cj.jdbc.Driver");
        jdbc.setHost("localhost");
        jdbc.setSchema("yuntao-manager-core-read");
        jdbc.setUsername("root");
        jdbc.setPassword("123456");
        simpleProjectVO.setJdbc(jdbc);

        RedisConfig redis = new RedisConfig();
        redis.setHost("localhost");
        redis.setPort("6379");
        redis.setPassword("123456");
        simpleProjectVO.setEnableRedis(true);
        simpleProjectVO.setRedis(redis);

        byte[] data = generateService.generate(simpleProjectVO);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"yuntao-manager-project.zip\"");
        response.addHeader("Content-Length", String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");

        try {
            StreamUtils.write(data, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多模块项目构建，后台管理模块不分离
     *
     * @param response       HttpServletResponse
     * @param multiProjectVO 项目构建信息
     */
    @GetMapping(value = "generate-multi", produces = "application/json; charset=utf-8")
    public void generateMulti(HttpServletResponse response, MultiProjectVO multiProjectVO) {
        multiProjectVO.setProjectType(ProjectTypeEnum.MULTI);
        multiProjectVO.setYuntaoVersion("0.2.3");
        multiProjectVO.setName("yuntao-multi");
        multiProjectVO.setGroupId("com.chanus");
        multiProjectVO.setParentArtifactId("yuntao-multi");
        multiProjectVO.setManagerArtifactId("yuntao-multi-manager");
        multiProjectVO.setVersion("1.0.0");
        multiProjectVO.setProjectName("云道管理系统多模块版");
        multiProjectVO.setAuthor("Chanus");
        multiProjectVO.setCopyright("Copyright © 2020 Chanus. All Rights Reserved.");
        multiProjectVO.setPackageName("com.chanus.yuntao.multi.manager");

        JdbcConfig jdbc = new JdbcConfig();
        jdbc.setDriverClassName("com.mysql.cj.jdbc.Driver");
        jdbc.setHost("localhost");
        jdbc.setSchema("yuntao-manager-core-read");
        jdbc.setUsername("root");
        jdbc.setPassword("123456");
        multiProjectVO.setJdbc(jdbc);

        RedisConfig redis = new RedisConfig();
        redis.setHost("localhost");
        redis.setPort("6379");
        redis.setPassword("123456");
        multiProjectVO.setEnableRedis(true);
        multiProjectVO.setRedis(redis);

        byte[] data = generateService.generate(multiProjectVO);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"yuntao-manager-project.zip\"");
        response.addHeader("Content-Length", String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");

        try {
            StreamUtils.write(data, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多模块项目构建，后台管理模块分离
     *
     * @param response                HttpServletResponse
     * @param multiSplittingProjectVO 项目构建信息
     */
    @GetMapping(value = "generate-multi-splitting", produces = "application/json; charset=utf-8")
    public void generateMultiSplitting(HttpServletResponse response, MultiSplittingProjectVO multiSplittingProjectVO) {
        multiSplittingProjectVO.setProjectType(ProjectTypeEnum.MULTI_SPLITTING);
        multiSplittingProjectVO.setYuntaoVersion("0.2.3");
        multiSplittingProjectVO.setName("yuntao-multi-splitting");
        multiSplittingProjectVO.setGroupId("com.chanus");
        multiSplittingProjectVO.setParentArtifactId("yuntao-multi-splitting");
        multiSplittingProjectVO.setApiArtifactId("yuntao-multi-manager-api");
        multiSplittingProjectVO.setWebArtifactId("yuntao-multi-manager-web");
        multiSplittingProjectVO.setVersion("1.0.0");
        multiSplittingProjectVO.setProjectName("云道管理系统多模块版");
        multiSplittingProjectVO.setAuthor("Chanus");
        multiSplittingProjectVO.setCopyright("Copyright © 2020 Chanus. All Rights Reserved.");
        multiSplittingProjectVO.setApiPackageName("com.chanus.yuntao.multi.manager.api");
        multiSplittingProjectVO.setWebPackageName("com.chanus.yuntao.multi.manager.web");

        JdbcConfig jdbc = new JdbcConfig();
        jdbc.setDriverClassName("com.mysql.cj.jdbc.Driver");
        jdbc.setHost("localhost");
        jdbc.setSchema("yuntao-manager-core-read");
        jdbc.setUsername("root");
        jdbc.setPassword("123456");
        multiSplittingProjectVO.setJdbc(jdbc);

        RedisConfig redis = new RedisConfig();
        redis.setHost("localhost");
        redis.setPort("6379");
        redis.setPassword("123456");
        multiSplittingProjectVO.setEnableRedis(true);
        multiSplittingProjectVO.setRedis(redis);

        byte[] data = generateService.generate(multiSplittingProjectVO);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"yuntao-manager-project.zip\"");
        response.addHeader("Content-Length", String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");

        try {
            StreamUtils.write(data, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
