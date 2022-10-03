package com.ywk.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @ClassName: GlobalExceptionHandler
 * @Description:全局异常处理器
 * @Author: ywk
 * @Date: 2022/5/31 16:42
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry")){
//            要分隔的内容 :Duplicate entry 'zhangsan' for key 'idx_username'
            String[] split =ex.getMessage().split(" ");
            String msg =  split[2]+"已存在";
            return R.error(msg);

        }
        ex.printStackTrace();
        return R.error("未知错误");
    }


    @ExceptionHandler(CustomException.class)
    /*捕获自定义异常*/
    public R<String> exceptionHandler(CustomException ex){
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }

}
