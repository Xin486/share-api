package top.muteki.share.user.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.muteki.share.user.domain.dto.LoginDTO;
import top.muteki.share.user.domain.entity.User;
import top.muteki.share.user.resp.CommonResp;
import top.muteki.share.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/count")
    public CommonResp<Long> count(){
        Long count = userService.count();
        CommonResp<Long> commonResp=new CommonResp<>();
        commonResp.setData(count);
        return commonResp;
    }
    @PostMapping("/login")
    public CommonResp<User> login(@RequestBody LoginDTO loginDTO){
        User user= userService.login(loginDTO);
        CommonResp<User> commonResp =new CommonResp<>();
        commonResp.setData(user);
        return commonResp;
    }
}
