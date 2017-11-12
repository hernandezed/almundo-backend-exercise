package com.callcenter.almundo.controller;

import com.callcenter.almundo.domain.Employee;
import java.util.concurrent.BlockingQueue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    private final BlockingQueue<Employee> employees;

    public EmployeeRestController(BlockingQueue<Employee> employees) {
        this.employees = employees;
    }

    @PostMapping
    public Employee employees(@RequestBody Employee employee) {
        employees.add(employee);
        return employee;
    }

}
