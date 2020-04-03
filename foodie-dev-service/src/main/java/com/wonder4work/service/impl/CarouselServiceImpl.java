package com.wonder4work.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonder4work.mapper.CarouselMapper;
import com.wonder4work.pojo.Carousel;
import com.wonder4work.service.CarouselService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 轮播图  服务实现类
 * </p>
 *
 * @author wonder4work
 * @since 2020-03-26
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Carousel> queryAll(Integer isShow) {

        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("is_show", isShow);
        queryWrapper.orderByDesc("sort");
        List<Carousel> carouselList = this.list(queryWrapper);
        return carouselList;
    }
}
