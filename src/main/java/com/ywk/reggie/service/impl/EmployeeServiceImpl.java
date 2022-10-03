package com.ywk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywk.reggie.entity.Employee;
import com.ywk.reggie.mapper.EmployeeMapper;
import com.ywk.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: EmployeeServiceImp1
 * @Description:
 * @Author: ywk
 * @Date: 2022/5/29 13:06
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {


}
