package top.muteki.share.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.muteki.share.user.domain.dto.LoginDTO;
import top.muteki.share.user.domain.entity.User;
import top.muteki.share.user.exception.BusinessException;
import top.muteki.share.user.exception.BusinessExceptionEnum;
import top.muteki.share.user.mapper.UserMapper;

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
}
