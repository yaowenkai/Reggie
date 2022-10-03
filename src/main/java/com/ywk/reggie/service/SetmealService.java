package com.ywk.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ywk.reggie.dto.SetmealDto;
import com.ywk.reggie.entity.Setmeal;

import java.util.List;

/**
* @author ywk
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Service
* @createDate 2022-06-01 17:04:37
*/
public interface SetmealService extends IService<Setmeal> {

/*
* 新增套餐,同时需要保存套餐和菜品的关联关系 */
    public void saveWithDish(SetmealDto setmealDto);

    //删除套餐和菜品的关联数据
    public void removeWithDish(List<Long> ids);
}
