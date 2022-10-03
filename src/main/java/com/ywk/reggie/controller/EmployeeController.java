package com.ywk.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ywk.reggie.common.R;
import com.ywk.reggie.entity.Employee;
import com.ywk.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @ClassName: EmployeeController
 * @Description:
 * @Author: ywk
 * @Date: 2022/5/29 13:12
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    //@ResponseBody
/*员工登录
request 负责使用session
* @RequestBody 负责将传输回来的json转化为java对象*/
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
//         1.将页面提交的密码password进行md5加密处理
//        使用DigestUtils.md5DigestAsHex(password.getBytes());方法可以将明文密码改为md5加密的
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());


//        2.根据页面提交的username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
//        使用,getOne的原因是用户名是唯一的,不重复
        Employee emp = employeeService.getOne(queryWrapper);
//        3.查询是否有结果
        if (emp == null) {
            return R.error("登录失败");
        }
//        4,密码比对,如果不一致拉倒
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误,请检查");
        }
//       5. 查看员工状态
        if (emp.getStatus() == 0) {
            return R.error("账号已经禁用");
        }
//        登陆成功
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /*员工退出*/
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
//        移除Session中保存的当前登录员工的ID
        request.removeAttribute("employee");

        return R.success("退出成功");
    }

    /*增加员工*/
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增了一个员工{}", employee.getIdNumber());
//        设置初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
/*//        获得当前设置人的id
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);*/
        employeeService.save(employee);
        return R.success("申请员工成功");
    }

    /*分页查询*/
//    因为get请求通过?key=value形式返回参数,所以直接接收数据即可,因为要接受record 等,所有需要page,page里面有这些属性
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name) {
        log.info("Page={},pagesize={},name={}", page,pageSize,name);
//        构建分页构造器
        Page<Employee> pageInfo = new Page(page, pageSize);
//        构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
//        添加条件构造
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
//        执行查询
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }
    /*根据id修改信息*/
    @PutMapping
    public R<String > update(HttpServletRequest request, @RequestBody Employee employee){
 /*
//        获取修改人的ID
        Long empId = (Long)request.getSession().getAttribute("employee");
//设置修改时间和修改人
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);*/
        return R.success("员工信息修改成功");
    }

    /*根据id查询员工信息*/
    @GetMapping("/{id}")
//    @PathVariable 是获取路径变量
    public R<Employee> getById(@PathVariable Long id){

        Employee employee = employeeService.getById(id);
        if(employee!=null){

            return R.success(employee);
        }
        return R.error("查询失败");
    }


}
