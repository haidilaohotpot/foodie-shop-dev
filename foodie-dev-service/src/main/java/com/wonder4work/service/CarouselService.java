package com.wonder4work.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wonder4work.pojo.Carousel;

import java.util.List;

/**
 * <p>
 * 轮播图  服务类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
public interface CarouselService extends IService<Carousel> {


    /**
     * 查询所有轮播图列表
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);


}
