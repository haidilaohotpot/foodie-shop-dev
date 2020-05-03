package com.wonder4work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wonder4work.pojo.ItemsComments;
import com.wonder4work.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品评价表  Mapper 接口
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Mapper
public interface ItemsCommentsCustomMapper {

    public void saveComments(@Param("map") Map<String, Object> map);

    public List<MyCommentVO> queryMyComments(@Param("map") Map<String, Object> map);

}
