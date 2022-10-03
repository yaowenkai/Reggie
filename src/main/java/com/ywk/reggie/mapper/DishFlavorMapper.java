package com.ywk.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ywk.reggie.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2022-06-04 19:54:16
* @Entity com.ywk.reggie.entity.DishFlavor
*/

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

}




