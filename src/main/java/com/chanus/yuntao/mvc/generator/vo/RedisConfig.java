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
 * Redis 配置信息
 *
 * @author Chanus
 * @date 2020-07-05 14:26:20
 * @since 1.0.0
 */
public class RedisConfig implements Serializable {
    private static final long serialVersionUID = -1129898629719508769L;

    /**
     * redis 服务器地址
     */
    private String host;
    /**
     * redis 服务端口号
     */
    private String port;
    /**
     * redis 连接密码
     */
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
