package com.wonder4work.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wonder4work.pojo.Items;
import com.wonder4work.pojo.ItemsImg;
import com.wonder4work.pojo.ItemsParam;
import com.wonder4work.pojo.ItemsSpec;
import com.wonder4work.pojo.vo.CommentLevelCountsVO;
import com.wonder4work.pojo.vo.ItemCommentsVO;
import com.wonder4work.pojo.vo.ShopcartVO;
import com.wonder4work.utils.PagedGridResult;

import java.util.List;

/**
 * <p>
 * 商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表 服务类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
public interface ItemsService extends IService<Items> {


    /**
     * 根据商品id查询详情
     * @param id
     * @return
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     * @param itemId
     * @return
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品的等级数量
     * @param itemId
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id和评价等级查询商品评价 分页
     * @param itemId
     * @param level
     * @return
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);


    /**
     * 搜索商品列表
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);


    /**
     * 根据分类id搜索商品列表
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据规格ids查询最新的购物车中的商品数据 渲染
     * @param specIds
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(String specIds);

}
