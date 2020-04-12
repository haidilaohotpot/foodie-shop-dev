package com.wonder4work.controller;

import com.wonder4work.pojo.Items;
import com.wonder4work.pojo.ItemsImg;
import com.wonder4work.pojo.ItemsParam;
import com.wonder4work.pojo.ItemsSpec;
import com.wonder4work.pojo.vo.CommentLevelCountsVO;
import com.wonder4work.pojo.vo.ItemInfoVO;
import com.wonder4work.pojo.vo.ShopcartVO;
import com.wonder4work.service.ItemsService;
import com.wonder4work.utils.JSONResult;
import com.wonder4work.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @since 1.0.0 2020/4/4
 */
@Api(value = "商品接口", tags = "商品信息展示的相关接口")
@RestController
@RequestMapping("/items")
public class ItemsController extends BaseController {

    @Autowired
    private ItemsService itemsService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JSONResult itemInfo(@ApiParam(name = "itemId",value = "商品id",required = true)
                               @PathVariable String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }

        Items items = itemsService.queryItemById(itemId);

        List<ItemsImg> itemsImgList = itemsService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemsService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemsService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(items);
        itemInfoVO.setItemImgList(itemsImgList);
        itemInfoVO.setItemParams(itemsParam);
        itemInfoVO.setItemSpecList(itemsSpecList);
        return JSONResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JSONResult commentLevel(@ApiParam(name = "itemId",value = "商品id",required = true)
                               @RequestParam String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }

        CommentLevelCountsVO countsVO = itemsService.queryCommentCounts(itemId);

        return JSONResult.ok(countsVO);
    }

    @ApiOperation(value = "查询商品评价", notes = "查询商品评价", httpMethod = "GET")
    @GetMapping("/comments")
    public JSONResult comments(@ApiParam(name = "itemId",value = "商品id",required = true)
                               @RequestParam String itemId,
                               @ApiParam(name = "level",value = "评价等级",required = false)
                               @RequestParam Integer level,
                               @ApiParam(name = "page",value = "查询的页数",required = false)
                               @RequestParam Integer page,
                               @ApiParam(name = "pageSize",value = "每页显示多少条",required = false)
                               @RequestParam Integer pageSize ) {

        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }

        if (page==null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemsService.queryPagedComments(itemId,level,page,pageSize);

        return JSONResult.ok(pagedGridResult);
    }


    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public JSONResult search(@ApiParam(name = "keywords",value = "搜索关键字",required = true)
                               @RequestParam String keywords,
                               @ApiParam(name = "sort",value = "排序方式",required = false)
                               @RequestParam String sort,
                               @ApiParam(name = "page",value = "查询的页数",required = false)
                               @RequestParam Integer page,
                               @ApiParam(name = "pageSize",value = "每页显示多少条",required = false)
                               @RequestParam Integer pageSize ) {

        if (StringUtils.isBlank(keywords)) {
            return JSONResult.errorMsg(null);
        }

        if (page==null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemsService.searchItems(keywords,sort,page,pageSize);

        return JSONResult.ok(pagedGridResult);
    }

@ApiOperation(value = "根据分类搜索商品列表", notes = "根据分类搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public JSONResult catItems(@ApiParam(name = "catId",value = "分类id",required = true)
                               @RequestParam Integer catId,
                               @ApiParam(name = "sort",value = "排序方式",required = false)
                               @RequestParam String sort,
                               @ApiParam(name = "page",value = "查询的页数",required = false)
                               @RequestParam Integer page,
                               @ApiParam(name = "pageSize",value = "每页显示多少条",required = false)
                               @RequestParam Integer pageSize ) {

        if (catId == null) {
            return JSONResult.errorMsg(null);
        }

        if (page==null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemsService.searchItemsByThirdCat(catId,sort,page,pageSize);

        return JSONResult.ok(pagedGridResult);
    }

    // 由于用户长时间未登录网站，刷新购物车中的数据 主要是商品价格
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public JSONResult refresh(@ApiParam(name = "itemSpecIds",value = "拼接的规格ids",required = true,example = "1001,1003,1005")
                               @RequestParam String itemSpecIds) {

        if (StringUtils.isBlank(itemSpecIds)) {
            return JSONResult.ok();
        }

        List<ShopcartVO> shopcartVOList = itemsService.queryItemsBySpecIds(itemSpecIds);

        return JSONResult.ok(shopcartVOList);
    }

}
