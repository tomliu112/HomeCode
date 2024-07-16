package com.home.code.ebay.controller;

import com.home.code.ebay.pojo.ResponseResult;
import com.home.code.ebay.pojo.ResponseResultCode;
import com.home.code.ebay.pojo.User;
import com.home.code.ebay.service.ManagementService;
import com.home.code.ebay.util.TokenUtil;
import jakarta.annotation.Resource;
import java.io.IOException;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ManagementController {

    @Resource
    ManagementService managementService;

    @PostMapping("/admin/addUser")
    public ResponseResult<String> addUser(@RequestBody User user, @RequestHeader("token") String token) {
        try {
           boolean re = managementService.addUser(token,user);
           if(re){
               return ResponseResult.success("add user success");

           }
            return ResponseResult.success("no access to add user");

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseResult.failure(ResponseResultCode.FAIL,e.getMessage());
        }
    }

    @GetMapping("/user/{resource}")
    public ResponseResult<String> judgeAccess(@PathVariable("resource") String resource,@RequestHeader("token") String token) {
        try {
            String re = managementService.judgeAccess(token,resource);
            return ResponseResult.success(re);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseResult.failure(ResponseResultCode.FAIL,e.getMessage());
        }

    }

}
