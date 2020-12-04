package com.teligen.demo.controller;

import com.teligen.demo.dto.AddUserDTO;
import com.teligen.demo.dto.ResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/user")
@Api(description = "用户")
@Validated   //加上这个，就可以对非object（包括String）的参数进行校验
public class UserController {

    @ApiOperation("新增用户")
    @RequestMapping(value = "/add",method = {RequestMethod.GET,RequestMethod.POST})
    public ResultDTO addUser(@RequestBody @Validated AddUserDTO addUserDTO){
        return ResultDTO.success();
    }

    @PostMapping("/getCount1")
    @ApiOperation("获取数量")
    public ResultDTO getCount1(@Min(value = 1, message = "最小值为1") @RequestParam Integer count,
                               @Length(min = 3,max = 20,message = "长度最小值为3，最大值为20") @RequestParam String name,
                               HttpServletRequest request) {
        int i = count / 1;
        return ResultDTO.success();
    }
}
