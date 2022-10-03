package com.ywk.reggie.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: MybatisPlusConfig
 * @Description:配置分页插件
 * @Author: ywk
 * @Date: 2022/5/31 17:03
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
/*// TODO: 2022/7/9 获取组件学习
 * 这是给容器添加组件的一种方式,通过函数去返回一个实例对象,并且给方法加上@Bean注解
* 此时方法名作为组件的id 返回类型就是组件类型 返回对象就是组件
* 若是自定义名而不是方法名 则可以使用@Bean("名字")
* 注册的组件是单实例组件
* */
