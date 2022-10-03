package com.ywk.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ywk.reggie.entity.Category;

public interface CategoryService extends IService<Category> {


    /*判断后再删除*/
    public void remove(Long id);
}
