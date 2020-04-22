package com.wonder4work.controller;

import com.wonder4work.enums.YesOrNo;
import com.wonder4work.pojo.Carousel;
import com.wonder4work.pojo.Category;
import com.wonder4work.pojo.UserAddress;
import com.wonder4work.pojo.bo.AddressBO;
import com.wonder4work.pojo.vo.CategoryVO;
import com.wonder4work.pojo.vo.NewItemsVO;
import com.wonder4work.service.CarouselService;
import com.wonder4work.service.CategoryService;
import com.wonder4work.service.UserAddressService;
import com.wonder4work.utils.JSONResult;
import com.wonder4work.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @since 1.0.0 2020/4/1
 */
@Api(value = "地址",tags = "收获地址的相关接口")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private UserAddressService addressService;


    @ApiOperation(value = "获取所有的收货地址", notes = "获取所有的收货地址",httpMethod = "POST")
    @PostMapping("/list")
    public JSONResult list(
            @ApiParam(value = "userId",name = "用户id",required = true)
            @RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        List<UserAddress> addressList = addressService.queryAll(userId);
        return JSONResult.ok(addressList);
    }


    @ApiOperation(value = "添加收货地址", notes = "添加收货地址",httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(
            @ApiParam(value = "addressBO",name = "用户地址BO",required = true)
            @RequestBody AddressBO addressBO) {
        JSONResult result = checkAddress(addressBO);
        if (result.getStatus() != 200) {
            return result;
        }
        addressService.addNewUserAddress(addressBO);
        return JSONResult.ok();
    }

    @ApiOperation(value = "修改收货地址", notes = "修改收货地址",httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(
            @ApiParam(value = "addressBO",name = "用户地址BO",required = true)
            @RequestBody AddressBO addressBO) {
        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return JSONResult.errorMsg("修改地址不能为空");
        }

        JSONResult checkAddress = checkAddress(addressBO);
        if (checkAddress.getStatus() != 200) {
            return checkAddress;
        }
        addressService.updateUserAddress(addressBO);
        return JSONResult.ok();
    }

    @ApiOperation(value = "删除收货地址", notes = "删除收货地址",httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(@ApiParam(value = "userId",name = "用户id",required = true)
                                 @RequestParam String userId,
            @ApiParam(value = "addressId",name = "地址id",required = true)
            @RequestParam String addressId) {
        if (StringUtils.isBlank(userId)||StringUtils.isBlank(addressId)){
            return JSONResult.errorMsg("");
        }

        addressService.deleteUserAddress(userId,addressId);
        return JSONResult.ok();
    }


    @ApiOperation(value = "设置默认收货地址", notes = "设置默认收货地址",httpMethod = "POST")
    @PostMapping("/setDefault")
    public JSONResult setDefault(@ApiParam(value = "userId",name = "用户id",required = true)
                                 @RequestParam String userId,
            @ApiParam(value = "addressId",name = "地址id",required = true)
            @RequestParam String addressId) {
        if (StringUtils.isBlank(userId)||StringUtils.isBlank(addressId)){
            return JSONResult.errorMsg("");
        }

        addressService.updateUserAddressToBeDefault(userId,addressId);
        return JSONResult.ok();
    }

    private JSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return JSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return JSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return JSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return JSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return JSONResult.errorMsg("收货地址信息不能为空");
        }

        return JSONResult.ok();
    }




}
