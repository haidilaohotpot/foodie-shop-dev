package com.wonder4work.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户对象",description = "从客户端，由用户传入的数据封装在此实体类中")
@Data
public class UserBO {

    @ApiModelProperty(value = "用户名",name = "username",example = "wonder4work",required = true)
    private String username;
    @ApiModelProperty(value = "密码",name = "password",example = "123456",required = true)
    private String password;
    @ApiModelProperty(value = "确认密码",name = "confirmPassword",example = "123456",required = false)
    private String confirmPassword;

}
