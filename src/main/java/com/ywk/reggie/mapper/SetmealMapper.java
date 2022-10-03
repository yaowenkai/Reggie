package com.ywk.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ywk.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ywk
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Mapper
* @createDate 2022-06-01 17:04:37
* @Entity com.ywk.reggie.entity.Setmeal
*/
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

}




