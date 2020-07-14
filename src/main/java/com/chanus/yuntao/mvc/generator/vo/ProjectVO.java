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
package com.chanus.yuntao.mvc.generator.vo;

import java.io.Serializable;

/**
 * 项目基础信息
 *
 * @author Chanus
 * @date 2020-07-05 14:15:37
 * @since 1.0.0
 */
public class ProjectVO implements Serializable {
    private static final long serialVersionUID = -814815019462607727L;

    /**
     * 项目类型
     */
    private ProjectTypeEnum projectType;
    /**
     * 云道后台管理系统版本号
     */
    private String yuntaoVersion;
    /**
     * 项目名
     */
    private String name;
    /**
     * 项目 groupId
     */
    private String groupId;
    /**
     * 项目版本号
     */
    private String version;
    /**
     * 系统名称
     */
    private String projectName;
    /**
     * 项目作者
     */
    private String author;
    /**
     * 系统声明
     */
    private String copyright;
    /**
     * jdbc 配置信息
     */
    private JdbcConfig jdbc;
    /**
     * 是否启用 redis 缓存数据库，true 启用，false 不启用，默认为 false
     */
    private boolean enableRedis = false;
    /**
     * redis 缓存数据库配置信息
     */
    private RedisConfig redis;

    public ProjectTypeEnum getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectTypeEnum projectType) {
        this.projectType = projectType;
    }

    public String getYuntaoVersion() {
        return yuntaoVersion;
    }

    public void setYuntaoVersion(String yuntaoVersion) {
        this.yuntaoVersion = yuntaoVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public JdbcConfig getJdbc() {
        return jdbc;
    }

    public void setJdbc(JdbcConfig jdbc) {
        this.jdbc = jdbc;
    }

    public boolean getEnableRedis() {
        return enableRedis;
    }

    public void setEnableRedis(boolean enableRedis) {
        this.enableRedis = enableRedis;
    }

    public RedisConfig getRedis() {
        return redis;
    }

    public void setRedis(RedisConfig redis) {
        this.redis = redis;
    }
}
