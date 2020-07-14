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

import com.chanus.yuntao.utils.core.StringUtils;

import java.io.File;
import java.io.Serializable;

/**
 * 多模块项目信息，web 模块分离
 *
 * @author Chanus
 * @date 2020-07-05 18:22:30
 * @since 1.0.0
 */
public class MultiSplittingProjectVO extends ProjectVO implements Serializable {
    private static final long serialVersionUID = 7783942422004768485L;

    /**
     * 父项目 artifactId
     */
    private String parentArtifactId;
    /**
     * 后台管理 api 模块 artifactId
     */
    private String apiArtifactId;
    /**
     * 后台管理 web 模块 artifactId
     */
    private String webArtifactId;
    /**
     * 后台管理 api 模块包目录，项目构建时会在该包目录下创建子包 model、mapper、service.impl
     */
    private String apiPackageName;
    /**
     * 后台管理 web 模块包目录，项目构建时会在该包目录下创建子包 controller
     */
    private String webPackageName;

    /**
     * 获取后台管理 api 模块包路径
     *
     * @return 包路径
     */
    public String getApiPackagePath() {
        return StringUtils.isBlank(this.apiPackageName) ? null : this.apiPackageName.replace(".", File.separator);
    }

    /**
     * 获取后台管理 web 模块包路径
     *
     * @return 包路径
     */
    public String getWebPackagePath() {
        return StringUtils.isBlank(this.webPackageName) ? null : this.webPackageName.replace(".", File.separator);
    }

    public String getParentArtifactId() {
        return parentArtifactId;
    }

    public void setParentArtifactId(String parentArtifactId) {
        this.parentArtifactId = parentArtifactId;
    }

    public String getApiArtifactId() {
        return apiArtifactId;
    }

    public void setApiArtifactId(String apiArtifactId) {
        this.apiArtifactId = apiArtifactId;
    }

    public String getWebArtifactId() {
        return webArtifactId;
    }

    public void setWebArtifactId(String webArtifactId) {
        this.webArtifactId = webArtifactId;
    }

    public String getApiPackageName() {
        return apiPackageName;
    }

    public void setApiPackageName(String apiPackageName) {
        this.apiPackageName = apiPackageName;
    }

    public String getWebPackageName() {
        return webPackageName;
    }

    public void setWebPackageName(String webPackageName) {
        this.webPackageName = webPackageName;
    }
}
