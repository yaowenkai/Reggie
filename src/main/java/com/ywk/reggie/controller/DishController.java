package com.ywk.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ywk.reggie.common.R;
import com.ywk.reggie.dto.DishDto;
import com.ywk.reggie.dto.SetmealDto;
import com.ywk.reggie.entity.Category;
import com.ywk.reggie.entity.Dish;
import com.ywk.reggie.service.CategoryService;
import com.ywk.reggie.service.DishFlavorService;
import com.ywk.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/*
 *
 * 菜品管理*/
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {

        log.info("dishDRO");

        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    //    分页
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("开始菜品类型分页");
        //构造分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> pageInfo1 = new Page<>(page, pageSize);

        //构建条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //模糊查询
        queryWrapper.like(name != null, Dish::getName, name);
        //排序
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        dishService.page(pageInfo, queryWrapper);
        //对象拷贝--->dish没有categoryName属性,需要用dishdto这个实体,所以需要把数据拷过来,但是不需要靠records-->属于展示到页面的数据
        //records是List<Dish>类型的,需要转化为List<DishDto>类型的,就需要取出来重新复制--->
        BeanUtils.copyProperties(pageInfo, pageInfo1, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());
        pageInfo1.setRecords(list);

        return R.success(pageInfo1);
    }

    /*根据id查询*/
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {

        DishDto dishDto = dishService.getByIDWithFlavor(id);
        return R.success(dishDto);
    }

    //修改菜品
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {

        dishService.updateWithFlavor(dishDto);

        return R.success("修改菜品成功");
    }


    /*g根据条件查询菜品
    **/
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());

        //statues表示起售,
        queryWrapper.eq(Dish::getStatus,1);

        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);
        return R.success(list);
    }

    /*保存套餐
    **/


}
