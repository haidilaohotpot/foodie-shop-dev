package com.wonder4work.controller;

import com.wonder4work.enums.YesOrNo;
import com.wonder4work.pojo.Carousel;
import com.wonder4work.pojo.Category;
import com.wonder4work.pojo.vo.CategoryVO;
import com.wonder4work.pojo.vo.NewItemsVO;
import com.wonder4work.service.CarouselService;
import com.wonder4work.service.CategoryService;
import com.wonder4work.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @since 1.0.0 2020/4/1
 */
@Api(value = "首页",tags = "首页展示的相关接口")
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;


    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public JSONResult carousel() {
        List<Carousel> carouselList = carouselService.queryAll(YesOrNo.YES.type);
        return JSONResult.ok(carouselList);
    }


    /**
     * 首页分类展示需求
     * 1. 第一次刷新主页查询大分类，渲染
     * 2. 当鼠标移动到相关分类时，实现懒加载，获取下一级分类
     * 3. 此用户已经获取此分类后，不再请求，存在前端
     */

    @ApiOperation(value = "获取商品一级分类", notes = "获取商品一级分类", httpMethod = "GET")
    @GetMapping("/cats")
    public JSONResult cats() {

        List<Category> categoryList = categoryService.queryAllRootLevelCat();
        return JSONResult.ok(categoryList);
    }

    /**
     * 懒加载 鼠标移动到分类上 获取商品子分类
     */
    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCat(
            @PathVariable("rootCatId")
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true) Integer rootCatId) {

        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }

        List<CategoryVO> list = categoryService.getSubCatList(rootCatId);

        return JSONResult.ok(list);
    }


    /**
     * 懒加载 查询每个一级分类下的六条商品信息
     */
    @ApiOperation(value = "查询每个一级分类下的六条商品信息", notes = "查询每个一级分类下的六条商品信息", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JSONResult sixNewItems(
            @PathVariable("rootCatId")
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true) Integer rootCatId) {

        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }

        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);

        return JSONResult.ok(list);
    }


}
