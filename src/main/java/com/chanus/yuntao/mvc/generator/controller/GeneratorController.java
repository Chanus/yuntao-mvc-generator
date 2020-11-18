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

        download(response, simpleProjectVO);
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

        download(response, multiProjectVO);
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

        download(response, multiSplittingProjectVO);
    }

    private void download(HttpServletResponse response, ProjectVO projectVO) {
        byte[] data = generateService.generate(projectVO);
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
