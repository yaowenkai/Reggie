package com.ywk.reggie.dto;


import com.ywk.reggie.entity.Setmeal;
import com.ywk.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
