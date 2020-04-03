package com.wonder4work.mapper;

import com.wonder4work.pojo.vo.CategoryVO;
import com.wonder4work.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @since 1.0.0 2020/4/1
 */
@Mapper
public interface CategoryMapperCustom {

    List<CategoryVO> getSubCatList(@Param("rootCatId") Integer rootCatId);

    List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String,Object> map);
}
