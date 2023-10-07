package top.muteki.share.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.muteki.share.user.domain.dto.LoginDTO;
import top.muteki.share.user.domain.entity.User;
import top.muteki.share.user.exception.BusinessException;
import top.muteki.share.user.exception.BusinessExceptionEnum;
import top.muteki.share.user.mapper.UserMapper;
import top.muteki.share.user.util.SnowUtil;

import java.util.Date;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    public Long count(){
        return userMapper.selectCount(null);
    }
    public User login(LoginDTO loginDTO){
        User userDB= userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getPhone,loginDTO.getPhone()));
        if (userDB == null){
            throw new BusinessException(BusinessExceptionEnum.PHONE_NOT_EXIST);
        }
        if (!userDB.getPassword().equals(loginDTO.getPassword())){
            throw new BusinessException(BusinessExceptionEnum.PASSWORD_ERROR);
        }
        return userDB;
    }
    public Long register(LoginDTO loginDTO){
        User userDB = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getPhone,loginDTO.getPhone()));
        if (userDB !=null){
            throw new BusinessException(BusinessExceptionEnum.PHONE_EXIST);
        }
        User savedUser = User.builder()
                .id(SnowUtil.getSnowflakeNextId())
                .phone(loginDTO.getPhone())
                .password(loginDTO.getPassword())
                .nickname("新用户")
                .roles("user")
                .avatarUrl("https://t7.baidu.com/it/u=1819248061,230866778&fm=193&f=GIF")
                .bonus(100)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        userMapper.insert(savedUser);
        return savedUser.getId();
    }
}
