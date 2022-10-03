package com.ywk.reggie.service;

import com.ywk.reggie.dto.DishDto;
import com.ywk.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ywk
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-06-01 17:00:00
*/
public interface DishService extends IService<Dish> {

//    新增菜品,同时插入菜品对应的口味数据同时插入两张表.dish dish_flavor
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询菜品信息和口味信息
    public DishDto getByIDWithFlavor(Long id);

   public void updateWithFlavor(DishDto dishDto);
}
