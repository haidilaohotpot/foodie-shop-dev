package com.wonder4work.controller.center;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.wonder4work.controller.BaseController;
import com.wonder4work.pojo.Users;
import com.wonder4work.pojo.bo.center.CenterUserBO;
import com.wonder4work.resource.FileUpload;
import com.wonder4work.service.center.CenterUserService;
import com.wonder4work.utils.CookieUtils;
import com.wonder4work.utils.DateUtil;
import com.wonder4work.utils.JSONResult;
import com.wonder4work.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0.0 2020/4/25
 */
@Api(value = "用户信息接口", tags = "用户信息相关接口")
@RequestMapping("/userInfo")
@RestController
public class CenterUserController extends BaseController {

    @Autowired
    private FileUpload fileUpload;

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "修改用户头像", notes = "修改用户头像", httpMethod = "POST")
    @PostMapping("/uploadFace")
    public JSONResult uploadFace(@ApiParam(name = "userId", value = "用户id", required = true)
                               @RequestParam String userId,
                               @ApiParam(name = "file", value = "用户头像", required = true)
                                       MultipartFile file,
                               HttpServletRequest request, HttpServletResponse response) {

        // 定义头像保存的地址

        String fileSpace = fileUpload.getImageUserFaceLocation();
        FileOutputStream fileOutputStream = null;
        String uploadPathPrefix = File.separator + userId;
        try{
            // 在路径上对每一个用户增加一个userid用于区分不同用户的上传
            // 开始文件上传
            if (file != null) {

                String fileName = file.getOriginalFilename();

                if (StringUtils.isNotBlank(fileName)) {

                    // face-{userid}.png
                    String[] fileNameArr = fileName.split("\\.");
                    // 后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];

                    // 防止上传.sh的其他脚本
                    if (!suffix.equalsIgnoreCase("png")&&
                            !suffix.equalsIgnoreCase("jpg")&&
                            !suffix.equalsIgnoreCase("jpeg")) {
                        return JSONResult.errorMsg("图片格式不正确");
                    }

                    // 覆盖
                    String newFileName = "face-" + userId + "." + suffix;
                    // 上传的头像最终保存的位置
                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;

                    // 用于提供的web地址
                    uploadPathPrefix += ("/" + newFileName);

                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null) {
                        // 创建文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);

                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);

                }

            }else{
                JSONResult.errorMsg("文件不能为空");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }

        // 更新

        String imageServerUrl = fileUpload.getImageServerUrl();

        // 由于浏览器可能存在缓存 所有需要加上时间戳 保证更新
        String finalUserFaceUrl = imageServerUrl + uploadPathPrefix + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);

        Users userResult = centerUserService.updateUserFace(userId, finalUserFaceUrl);
        setNullProperty(userResult);
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(userResult),true);
        // TODO: 2020/4/27 后续需要增加令牌token整合redis 分布式会话
        return JSONResult.ok();
    }


    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(@ApiParam(name = "userId", value = "用户id", required = true)
                               @RequestParam String userId,
                               @RequestBody @Valid CenterUserBO usersBO,
                               BindingResult bindingResult,
                               HttpServletRequest request, HttpServletResponse response) {

        // 判断验证中是否有错误
        if (bindingResult.hasErrors()) {
            Map<String, Object> objectMap = getErrors(bindingResult);
            return JSONResult.errorMap(objectMap);
        }

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("id不能为空");
        }
        Users updateUser = centerUserService.updateUserInfo(userId, usersBO);
        setNullProperty(updateUser);
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(updateUser),true);

        // TODO: 2020/4/25 后续需要增加令牌token整合redis 分布式会话
        return JSONResult.ok();
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

    private Map<String, Object> getErrors(BindingResult result) {

        List<FieldError> errorList = result.getFieldErrors();
        Map<String, Object> map = new HashMap<>();
        for (FieldError fieldError : errorList) {
            // 某一个属性
            String field = fieldError.getField();
            // 验证错误的信息
            String defaultMessage = fieldError.getDefaultMessage();

            map.put(field, defaultMessage);
        }
        return map;
    }

}
