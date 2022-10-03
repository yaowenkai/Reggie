package com.ywk.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywk.reggie.common.CustomException;
import com.ywk.reggie.entity.Category;
import com.ywk.reggie.entity.Dish;
import com.ywk.reggie.entity.Setmeal;
import com.ywk.reggie.mapper.CategoryMapper;
import com.ywk.reggie.service.CategoryService;
import com.ywk.reggie.service.DishService;
import com.ywk.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: CategoryServiceImpl
 * @Description:
 * @Author: ywk
 * @Date: 2022/6/1 16:08
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /*根据id删除分类,删除之前要进行判断*/
    @Override
    public void remove(Long id) {

//        查询当前分类是否关联了菜品,如果已经关联,跑出异常
//        查询当前分类是否关联了套餐,如果已经关联,跑出异常

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getId, id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);

        if (count > 0) {
//            抛出异常
            throw new CustomException("当前分类下关联了菜品,不能删除");
        }
        if (count1 > 0) {
//            抛出异常
            throw new CustomException("当前分类下关联了套餐,不能删除");
        }

        //    正常删除
        super.removeById(id);

    }


}
