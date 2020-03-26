package com.wonder4work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wonder4work.pojo.ItemsSpec;

/**
 * <p>
 * 商品规格 每一件商品都有不同的规格，不同的规格又有不同的价格和优惠力度，规格表为此设计 Mapper 接口
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
public interface ItemsSpecMapper extends BaseMapper<ItemsSpec> {

}
