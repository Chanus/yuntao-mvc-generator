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
 * 多模块项目信息
 *
 * @author Chanus
 * @date 2020-07-05 14:45:19
 * @since 1.0.0
 */
public class MultiProjectVO extends ProjectVO implements Serializable {
    private static final long serialVersionUID = 7555200520643298412L;

    /**
     * 父项目 artifactId
     */
    private String parentArtifactId;
    /**
     * 后台管理模块 artifactId
     */
    private String managerArtifactId;
    /**
     * 项目包目录，项目构建时会在该包目录下创建子包 controller、model、mapper、service.impl
     */
    private String packageName;

    /**
     * 获取包路径
     *
     * @return 包路径
     */
    public String getPackagePath() {
        return StringUtils.isBlank(this.packageName) ? null : this.packageName.replace(".", File.separator);
    }

    public String getParentArtifactId() {
        return parentArtifactId;
    }

    public void setParentArtifactId(String parentArtifactId) {
        this.parentArtifactId = parentArtifactId;
    }

    public String getManagerArtifactId() {
        return managerArtifactId;
    }

    public void setManagerArtifactId(String managerArtifactId) {
        this.managerArtifactId = managerArtifactId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
