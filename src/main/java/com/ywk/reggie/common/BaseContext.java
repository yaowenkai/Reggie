package com.ywk.reggie.common;

/**
 * @ClassName: BaseContext
 * @Description:基于threadlocal封装工具类,用户保存和获取当前登录用户id
 * @Author: ywk
 * @Date: 2022/6/1 15:43
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal =new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
       return threadLocal.get();
    }
}
