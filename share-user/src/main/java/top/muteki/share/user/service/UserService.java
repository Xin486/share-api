package top.muteki.share.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.muteki.share.user.domain.dto.LoginDTO;
import top.muteki.share.user.domain.entity.User;
import top.muteki.share.user.domain.resp.UserLoginResp;
import top.muteki.share.user.exception.BusinessException;
import top.muteki.share.user.exception.BusinessExceptionEnum;
import top.muteki.share.user.mapper.UserMapper;
import top.muteki.share.user.util.JwtUtil;
import top.muteki.share.user.util.SnowUtil;

import java.util.Date;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    public Long count(){
        return userMapper.selectCount(null);
    }
    public UserLoginResp login(LoginDTO loginDTO){
        User userDB= userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getPhone,loginDTO.getPhone()));
        if (userDB == null){
            throw new BusinessException(BusinessExceptionEnum.PHONE_NOT_EXIST);
        }
        if (!userDB.getPassword().equals(loginDTO.getPassword())){
            throw new BusinessException(BusinessExceptionEnum.PASSWORD_ERROR);
        }
        UserLoginResp userLoginResp=UserLoginResp.builder()
                .user(userDB)
                 .build();
//        String key="Mutek1";
//        Map<String ,Object> map = BeanUtil.beanToMap(userLoginResp);
//        String token= JWTUtil.createToken(map,key.getBytes());
        String token= JwtUtil.createToken(userLoginResp.getUser().getId(),userLoginResp.getUser().getPhone());
        userLoginResp.setToken(token);
        return userLoginResp;
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
    public User findById(Long userId){
        return userMapper.selectById(userId);
    }
}
