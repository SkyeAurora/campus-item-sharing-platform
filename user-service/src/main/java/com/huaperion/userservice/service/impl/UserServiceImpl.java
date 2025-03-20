package com.huaperion.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huaperion.userservice.mapper.UserMapper;
import com.huaperion.userservice.model.User;
import com.huaperion.userservice.model.UserLoginDTO;
import com.huaperion.userservice.model.UserLoginVO;
import com.huaperion.userservice.model.UserRegisterDTO;
import com.huaperion.userservice.service.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.entity.User2Item;
import org.huaperion.common.exception.BusinessException;
import org.huaperion.common.exception.ErrorCode;
import org.huaperion.common.result.Result;
import org.huaperion.common.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


/**
 * @Author: Huaperion
 * @Date: 2025/3/7
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    @Value("${default.male-avatar}")
    private String DEFAULT_MALE_AVATAR;

    @Value("${default.female-avatar}")
    private String DEFAULT_FEMALE_AVATAR;

    @Autowired
    public UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        // 数据库中查询 user
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getStudentId, userLoginDTO.getStudentId()));
        if (user == null) {
            throw new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        // 重复加密过程
        String password = encryptWithNameSalt(userLoginDTO.getPassword(), userLoginDTO.getStudentId());
        // 比对密码
        if (!password.equals(user.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }

        // 生成Json Web Token
        String token = jwtUtil.generateToken(userLoginDTO.getStudentId());

        // JWT 存入 Redis
        redisTemplate.opsForValue().set(token, token, jwtUtil.getExpiration(), TimeUnit.MINUTES);

        // 更新用户的最后登录时间为当前时间
        user.setLastLoginTime(LocalDateTime.now().toString());
        userMapper.updateById(user);

        return new UserLoginVO(token, user.getStudentId(), user.getUsername(), user.getAvatar(), user.getCreditScore());
    }

    @Override
    public Result register(UserRegisterDTO userRegisterDTO) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStudentId, userRegisterDTO.getStudentId());
        if (userMapper.exists(wrapper)) {
            throw new BusinessException(ErrorCode.ACCOUNT_EXISTS);
        }

        // 密码加密
        String password = encryptWithNameSalt(userRegisterDTO.getPassword(), userRegisterDTO.getStudentId());
        // 创建User对象
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        // 设置加密后的密码
        user.setPassword(password);
        // 设置默认头像
        user.setAvatar(user.getSex() ? DEFAULT_MALE_AVATAR : DEFAULT_FEMALE_AVATAR);

        userMapper.insert(user);
        return new Result().success("注册成功");
    }

    @Override
    public Result<User> getUserInfo(String studentId) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getStudentId, studentId));
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        return Result.success(user, "查询用户信息成功");
    }

    @Override
    public User2Item getUser2Item(Long id) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, id));
        User2Item user2Item = new User2Item();
        BeanUtils.copyProperties(user, user2Item);
        return user2Item;
    }

    /**
     * MD5(name + password) 加密工具方法
     */
    private String encryptWithNameSalt(String password, String salt) {
        String saltedPassword = password + salt; // 盐值拼接方式可自定义（如 salt + password）
        return DigestUtils.md5DigestAsHex(saltedPassword.getBytes(StandardCharsets.UTF_8));
    }
}
