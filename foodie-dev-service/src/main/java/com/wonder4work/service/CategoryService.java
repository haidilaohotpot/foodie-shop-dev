package com.wonder4work.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wonder4work.pojo.Category;
import com.wonder4work.pojo.vo.CategoryVO;
import com.wonder4work.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * <p>
 * 商品分类  服务类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
public interface CategoryService extends IService<Category> {


    /**
     * 查询所有一级分类
     * @return
     */
    List<Category> queryAllRootLevelCat();


    /**
     * 根据一级分类id获取二级分类
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);


    /**
     * 查询每个一级分类下的六条商品信息
     * @param rootCatId
     * @return
     */
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);


}
