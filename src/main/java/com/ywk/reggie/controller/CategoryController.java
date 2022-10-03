package com.ywk.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ywk.reggie.common.R;
import com.ywk.reggie.entity.Category;
import com.ywk.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: CategoryController
 * @Description:
 * @Author: ywk
 * @Date: 2022/6/1 16:10
 */

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /*新增分类*/
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category = {}",category);
        if(category!=null){
            categoryService.save(category);
            return R.success("菜品添加成功");
        }
        return R.error("菜品添加失败");
    }

    /*页面分页展示*/
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
//        构建分页构造器
        Page<Category> pageInfo = new Page(page, pageSize);
//        构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
//        添加条件构造
        queryWrapper.orderByDesc(Category::getSort);
//        执行查询
       categoryService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }
    /*删除分类*/
    @DeleteMapping
    public R<String> detele(Long ids){


        //categoryService.removeById(ids);
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    //修改菜品
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改category的信息:{}",category);
        categoryService.updateById(category);
        return R.success("修改成功");
    }

//    根据条件来查询分类数据,形成下拉框
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //查询
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        //排序
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
