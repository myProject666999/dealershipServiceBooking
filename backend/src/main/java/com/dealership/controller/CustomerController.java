package com.dealership.controller;

import com.dealership.common.Result;
import com.dealership.entity.Customer;
import com.dealership.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public Result<List<Customer>> list() {
        return Result.success(customerService.list());
    }

    @GetMapping("/{id}")
    public Result<Customer> getById(@PathVariable Long id) {
        return Result.success(customerService.getById(id));
    }

    @PostMapping("/")
    public Result<Void> save(@RequestBody Customer customer) {
        customerService.save(customer);
        return Result.success();
    }

    @PutMapping("/")
    public Result<Void> update(@RequestBody Customer customer) {
        customerService.updateById(customer);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        customerService.removeById(id);
        return Result.success();
    }
}
