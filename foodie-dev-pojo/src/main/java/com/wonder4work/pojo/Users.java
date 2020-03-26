package com.wonder4work.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表 
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Data
@Accessors(chain = true)
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id 用户id
     */
    private String id;
    /**
     * 用户名 用户名
     */
    private String username;
    /**
     * 密码 密码
     */
    private String password;
    /**
     * 昵称 昵称
     */
    private String nickname;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 头像
     */
    private String face;
    /**
     * 手机号 手机号
     */
    private String mobile;
    /**
     * 邮箱地址 邮箱地址
     */
    private String email;
    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;
    /**
     * 生日 生日
     */
    private Date birthday;
    /**
     * 创建时间 创建时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 更新时间 更新时间
     */
    @TableField("updated_time")
    private Date updatedTime;


}
