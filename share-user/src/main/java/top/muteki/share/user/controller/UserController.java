package top.muteki.share.user.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.muteki.share.user.domain.dto.LoginDTO;
import top.muteki.share.user.domain.entity.User;
import top.muteki.share.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/count")
    public Long count(){
        return userService.count();
    }
    @PostMapping("/login")
    public User login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }
}
