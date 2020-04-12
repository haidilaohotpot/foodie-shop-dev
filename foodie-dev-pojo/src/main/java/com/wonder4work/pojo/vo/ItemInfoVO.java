package com.wonder4work.pojo.vo;

import com.wonder4work.pojo.Items;
import com.wonder4work.pojo.ItemsImg;
import com.wonder4work.pojo.ItemsParam;
import com.wonder4work.pojo.ItemsSpec;
import lombok.Data;

import java.util.List;

/**
 * 商品详情
 * @since 1.0.0 2020/4/1
 */
@Data
public class ItemInfoVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private ItemsParam itemParams;
    private List<ItemsSpec> itemSpecList;


}
