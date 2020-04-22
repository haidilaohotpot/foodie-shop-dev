package com.wonder4work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wonder4work.enums.CommentLevel;
import com.wonder4work.mapper.ItemsMapper;
import com.wonder4work.mapper.ItemsMapperCustom;
import com.wonder4work.pojo.*;
import com.wonder4work.pojo.vo.CommentLevelCountsVO;
import com.wonder4work.pojo.vo.ItemCommentsVO;
import com.wonder4work.pojo.vo.SearchItemsVO;
import com.wonder4work.pojo.vo.ShopcartVO;
import com.wonder4work.service.*;
import com.wonder4work.utils.DesensitizationUtil;
import com.wonder4work.utils.JSONResult;
import com.wonder4work.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表 服务实现类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Service
public class ItemsServiceImpl extends ServiceImpl<ItemsMapper, Items> implements ItemsService {

    @Autowired
    private ItemsParamService itemsParamService;

    @Autowired
    private ItemsImgService itemsImgService;

    @Autowired
    private ItemsSpecService itemsSpecService;

    @Autowired
    private ItemsCommentsService itemsCommentsService;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        return this.getById(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        QueryWrapper<ItemsImg> queryWrapper = new QueryWrapper<ItemsImg>();
        queryWrapper.eq("item_id", itemId);
        return itemsImgService.list(queryWrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        QueryWrapper<ItemsSpec> queryWrapper = new QueryWrapper<ItemsSpec>();
        queryWrapper.eq("item_id", itemId);
        return itemsSpecService.list(queryWrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        QueryWrapper<ItemsParam> queryWrapper = new QueryWrapper<ItemsParam>();
        queryWrapper.eq("item_id", itemId);
        return itemsParamService.getOne(queryWrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {

        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCount = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCount + badCounts;

        CommentLevelCountsVO countsVO = new CommentLevelCountsVO();
        countsVO.setTotalCounts(totalCounts);
        countsVO.setBadCounts(badCounts);
        countsVO.setGoodCounts(goodCounts);
        countsVO.setNormalCounts(normalCount);

        return countsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level,Integer page,Integer pageSize) {

        Map<String, Object> map = new HashMap<>();

        map.put("itemId", itemId);
        map.put("level", level);

        // 分页
        PageHelper.startPage(page,pageSize);
        List<ItemCommentsVO> list = itemsMapperCustom.queryItemComments(map);

        list.forEach(itemCommentsVO -> {
            itemCommentsVO.setNickname(DesensitizationUtil.commonDisplay(itemCommentsVO.getNickname()));
        });
        PagedGridResult grid = setterPagedGrid(page, list);

        return grid;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();

        map.put("keywords", keywords);
        map.put("sort", sort);

        // 分页
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItems(map);

        PagedGridResult grid = setterPagedGrid(page, list);

        return grid;

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();

        map.put("catId", catId);
        map.put("sort", sort);

        // 分页
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItemsByThirdCat(map);

        PagedGridResult grid = setterPagedGrid(page, list);

        return grid;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {

        String ids[] =  specIds.split(",");
        List<String> specIdsList = new ArrayList<>();
        Collections.addAll(specIdsList, ids);
        List<ShopcartVO> shopcartVOS = itemsMapperCustom.queryItemsBySpecIds(specIdsList);
        return shopcartVOS;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void decreaseItemSpecStock(String specId, Integer buyCounts) throws RuntimeException {

        // 不推荐使用synchronized 集群下无用 性能低


        // 可使用redis加锁 或zookeeper 分布式锁
        // TODO: 2020/4/15  使用redis加锁 实现分布式锁
        // 查询库存
 /*       int stock = 2;

        // 判断库存是否能够减少到0以下
        if (stock - buyCounts < 0) {
            // 提示用户库存不够
        }*/

        int result = itemsMapperCustom.decreaseItemSpecStock(specId, buyCounts);

        if (result != 1) {
            throw new RuntimeException("订单创建失败，原因：库存不足");
        }
    }

    private PagedGridResult setterPagedGrid(Integer page, List<?> list) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentCounts(String itemId, Integer level) {

        ItemsComments condition = new ItemsComments();

        condition.setItemId(itemId);
        if (level != null) {
            condition.setCommentLevel(level);
        }
        QueryWrapper<ItemsComments> queryWrapper = new QueryWrapper<>();

        queryWrapper.setEntity(condition);
        int count = itemsCommentsService.count(queryWrapper);
        return count;
    }


}
