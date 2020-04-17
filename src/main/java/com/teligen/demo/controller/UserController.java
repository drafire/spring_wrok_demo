package com.teligen.demo.controller;

import com.teligen.demo.dto.AddUserDTO;
import com.teligen.demo.dto.ResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(description = "用户")
public class UserController {

    @ApiOperation("新增用户")
    @RequestMapping(value = "/add",method = {RequestMethod.GET,RequestMethod.POST})
    public ResultDTO addUser(@RequestBody @Validated AddUserDTO addUserDTO){
        return ResultDTO.success();
    }
}
