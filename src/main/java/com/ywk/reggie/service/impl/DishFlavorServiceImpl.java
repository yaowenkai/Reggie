package com.ywk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywk.reggie.entity.DishFlavor;
import com.ywk.reggie.service.DishFlavorService;
import com.ywk.reggie.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-06-04 19:54:16
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




