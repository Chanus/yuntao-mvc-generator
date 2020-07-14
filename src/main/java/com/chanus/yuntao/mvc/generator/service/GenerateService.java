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
package com.chanus.yuntao.mvc.generator.service;

import com.chanus.yuntao.mvc.generator.vo.ProjectVO;

/**
 * 项目构建相关接口
 *
 * @author Chanus
 * @date 2020-07-01 22:28:09
 * @since 1.0.0
 */
public interface GenerateService {
    /**
     * 项目构建
     *
     * @param projectVO 项目信息
     * @return
     */
    byte[] generate(ProjectVO projectVO);
}
