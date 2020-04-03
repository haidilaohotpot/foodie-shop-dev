package com.wonder4work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wonder4work.pojo.Category;
import com.wonder4work.pojo.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品分类  Mapper 接口
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
public interface CategoryMapper extends BaseMapper<Category> {

}
