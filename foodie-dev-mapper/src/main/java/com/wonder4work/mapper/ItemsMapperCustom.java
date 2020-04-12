package com.wonder4work.mapper;

import com.wonder4work.pojo.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @since 1.0.0 2020/4/1
 */
@Mapper
public interface ItemsMapperCustom {

    List<ItemCommentsVO> queryItemComments(@Param("paramsMap") Map<String, Object> paramsMap);

    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> paramsMap);


    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> paramsMap);

    List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List paramsList);

}
