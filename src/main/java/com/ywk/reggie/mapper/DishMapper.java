package com.ywk.reggie.mapper;

import com.ywk.reggie.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ywk
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-06-01 17:00:00
* @Entity com.ywk.reggie.entity.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}




