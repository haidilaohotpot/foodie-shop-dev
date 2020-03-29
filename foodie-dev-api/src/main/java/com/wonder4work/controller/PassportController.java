package com.wonder4work.controller;

import com.wonder4work.pojo.Users;
import com.wonder4work.pojo.bo.UserBO;
import com.wonder4work.service.UsersService;
import com.wonder4work.utils.CookieUtils;
import com.wonder4work.utils.JSONResult;
import com.wonder4work.utils.JsonUtils;
import com.wonder4work.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @since 1.0.0 2020/3/27
 * 登录校验
 */

@Api(value = "注册登录",tags = "用于注册登录的相关接口")
@RestController
@RequestMapping("/passport")
public class PassportController {

    @Autowired
    private UsersService usersService;

    @ApiOperation(value = "退出登录",notes = "用户退出登录的接口",httpMethod = "POST")
    @PostMapping("/logout")
    public JSONResult logout(@RequestParam String userId,
                             HttpServletRequest request, HttpServletResponse response){

        // 清除用户信息
        CookieUtils.deleteCookie(request, response, "user");

        // TODO: 2020/3/29 退出登录要清空购物车
        // TODO: 2020/3/29 清除分布式会话中的用户数据

        return JSONResult.ok();
    }


    /**
     * 用户登录
     * @param userBO
     * @return
     */
    @ApiOperation(value = "用户登录",notes = "用户登录的接口",httpMethod = "POST")
    @PostMapping("/login")
    public JSONResult login(@RequestBody UserBO userBO,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 0 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }

        // 1 实现登录
        Users dbUser = usersService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        // TODO: 2020/3/29  封装返回的结果

        if (dbUser == null) {
            return JSONResult.errorMsg("用户名或密码不正确");
        }

        Users userResult = setNullProperty(dbUser);
        // 设置cookie
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(userResult),true);

        return JSONResult.ok(userResult);
    }

    private Users setNullProperty(Users dbUser) {
        dbUser.setPassword(null);
        dbUser.setMobile(null);
        dbUser.setEmail(null);
        dbUser.setCreatedTime(null);
        dbUser.setUpdatedTime(null);
        dbUser.setRealname(null);
        dbUser.setBirthday(null);
        return dbUser;
    }

    /**
     * 注册用户信息
     * @param userBO
     * @return
     */
    @ApiOperation(value = "用户注册",notes = "用户注册的接口",httpMethod = "POST")
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody UserBO userBO,
                             HttpServletRequest request, HttpServletResponse response) {

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        // 0 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPwd)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }

        // 1 查询用户名是否存在

        boolean isExist = usersService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已经存在");
        }

        // 2 密码长度不能少于6位

        if (password.length() < 6) {
            return JSONResult.errorMsg("密码长度不能少于6位");
        }
        // 3 判断两次密码是否一致
        if (!password.equals(confirmPwd)) {
            return JSONResult.errorMsg("两次密码不一致");
        }
        // 4 实现注册
        Users dbUser = usersService.createUser(userBO);
        Users userResult = setNullProperty(dbUser);
        // 设置cookie
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(userResult),true);

        return JSONResult.ok();
    }



    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    @ApiOperation(value = "用户名是否存在",notes = "判断用户名是否存在",httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username) {

        // 判断用户名不为空
        if (StringUtils.isBlank(username)) return JSONResult.errorMsg("用户名不能为空");
        // 查找注册的用户名是否存在
        boolean isExist = usersService.queryUsernameIsExist(username);
        if (isExist) return JSONResult.errorMsg("用户名已经存在");
        // 请求成功
        return JSONResult.ok();

    }


}
