package com.ywk.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ywk.reggie.common.R;
import com.ywk.reggie.dto.SetmealDto;
import com.ywk.reggie.entity.Category;
import com.ywk.reggie.entity.Setmeal;
import com.ywk.reggie.service.CategoryService;
import com.ywk.reggie.service.SetmealDishService;
import com.ywk.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息:{}",setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }



   @GetMapping("/page")
   public R<Page> page(int page, int pageSize, String name){

    //    分页构造器
       Page<Setmeal> setmealpage = new Page<Setmeal>(page, pageSize);
       Page<SetmealDto> setmealDtopage = new Page<>(page, pageSize);

       LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
       queryWrapper.like(name!=null,Setmeal::getName,name);
       queryWrapper.orderByDesc(Setmeal::getUpdateTime);
       setmealService.page(setmealpage, queryWrapper);
       BeanUtils.copyProperties(setmealpage,setmealDtopage,"records");
       List<Setmeal> records = setmealpage.getRecords();
       List<SetmealDto> record = records.stream().map(
               (item) -> {
                   //分类ID
                   //创建一个SetMealDto对象,负责接收item里的数据,和利用item中的categoryid获得categoryName
                   SetmealDto setmealDto = new SetmealDto();
                   BeanUtils.copyProperties(item, setmealDto);
                   //
                   Long categoryId = item.getCategoryId();
                   Category category = categoryService.getById(categoryId);
                   if (category != null) {
                       String categoryName = category.getName();

                       setmealDto.setCategoryName(categoryName);
                   }
                   return setmealDto;
               }
       ).collect(Collectors.toList());
       setmealDtopage.setRecords(record);
       return R.success(setmealDtopage);
   }


   @DeleteMapping
    public R<String> delete(@RequestParam  List<Long> ids){


        log.info("ids={}",ids);
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
   }

}
