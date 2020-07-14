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

/**
 * 项目类型
 *
 * @author Chanus
 * @date 2020-07-05 14:49:23
 * @since 1.0.0
 */
public enum ProjectTypeEnum {
    // 单模块项目
    SIMPLE,
    // 多模块项目，web 模块不分离
    MULTI,
    // 多模块项目，web 模块分离
    MULTI_SPLITTING
}
