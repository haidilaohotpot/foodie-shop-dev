package com.wonder4work.controller;

import com.wonder4work.pojo.bo.ShopcatBO;
import com.wonder4work.utils.JSONResult;
import com.wonder4work.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @since 1.0.0 2020/4/11
 */
@Slf4j
@Api(value = "购物车接口",tags = "购物车接口")
@RestController
@RequestMapping("/shopcart")
public class ShopcartController {


    @ApiOperation(value = "添加商品到购物车",notes = "添加商品到购物车",httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestParam String userId,
                          @RequestBody ShopcatBO shopcatBO,
                          HttpServletRequest request,
                          HttpServletResponse response){

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }

        log.info(JsonUtils.objectToJson(shopcatBO));
        // TODO: 2020/4/11 前端用户再登录的情况下 添加商品到购物车，会同时再后端同步购物车到redis中


        return JSONResult.ok();
    }

    @ApiOperation(value = "删除购物车中的商品",notes = "删除购物车中的商品",httpMethod = "POST")
    @PostMapping("/del")
    public JSONResult del(@RequestParam String userId,
                          @RequestParam String itemSpecId,
                          HttpServletRequest request,
                          HttpServletResponse response){

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("参数不能为空");
        }
        if (StringUtils.isBlank(itemSpecId)) {
            return JSONResult.errorMsg("参数不能为空");
        }

        // TODO: 2020/4/12  用户已经登录 同步删除后端的数据


        return JSONResult.ok();
    }

}
