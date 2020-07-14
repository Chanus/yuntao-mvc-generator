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
 * JDBC 配置信息
 *
 * @author Chanus
 * @date 2020-07-05 14:24:24
 * @since 1.0.0
 */
public class JdbcConfig implements Serializable {
    private static final long serialVersionUID = -6354975542513645692L;

    /**
     * 数据库驱动类
     */
    private String driverClassName;
    /**
     * 数据库服务器地址
     */
    private String host;
    /**
     * 数据库实例
     */
    private String schema;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
