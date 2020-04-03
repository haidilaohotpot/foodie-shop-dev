package com.wonder4work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonder4work.enums.CategoryLevel;
import com.wonder4work.mapper.CategoryMapper;
import com.wonder4work.mapper.CategoryMapperCustom;
import com.wonder4work.pojo.Category;
import com.wonder4work.pojo.vo.CategoryVO;
import com.wonder4work.pojo.vo.NewItemsVO;
import com.wonder4work.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类  服务实现类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {

        QueryWrapper<Category> queryWrapper = new QueryWrapper();

        // 获取一级分类
        queryWrapper.eq("type",CategoryLevel.ROOT.type);

        List<Category> categoryList = list(queryWrapper);

        return categoryList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        return categoryMapperCustom.getSubCatList(rootCatId);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);
        return categoryMapperCustom.getSixNewItemsLazy(map);
    }
}
