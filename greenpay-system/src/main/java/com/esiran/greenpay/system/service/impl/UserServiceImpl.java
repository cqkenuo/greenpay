package com.esiran.greenpay.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.common.util.TOTPUtil;
import com.esiran.greenpay.system.entity.User;
import com.esiran.greenpay.system.entity.UserInputDTO;
import com.esiran.greenpay.system.entity.UserInputVo;
import com.esiran.greenpay.system.mapper.UserMapper;
import com.esiran.greenpay.system.service.IUserService;
import com.google.common.io.BaseEncoding;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final ModelMapper modelMapper = new ModelMapper();
    @Override
    public void updateUser(Integer userId, UserInputDTO userInputDTO) throws PostResourceException {
        User oldUser = this.getById(userId);
        if (oldUser == null) {
            throw new PostResourceException("用户不存在");
        }
        User user = modelMapper.map(userInputDTO, User.class);
        user.setId(oldUser.getId());
        user.setUpdatedAt(LocalDateTime.now());
        updateById(user);

    }

    @Override
    public void updateUserPWD(Integer userId, UserInputVo userInputVo) throws PostResourceException {
        User oldUser = this.getById(userId);
        if (oldUser == null) {
            throw new PostResourceException("未查询到用户");
        }
        if (!oldUser.getPassword().equals(userInputVo.getOldPassword())) {
            throw new PostResourceException("原密码不正确");
        }
        if (oldUser.getPassword().equals(userInputVo.getNewPassword())) {
            throw new PostResourceException("新密码与旧密码相同");
        }

        if (!userInputVo.getNewPassword().equals(userInputVo.getConfirmPassword())) {
            throw new PostResourceException("两次输入的密码不一致");
        }

        oldUser.setPassword(userInputVo.getNewPassword());
        oldUser.setUpdatedAt(LocalDateTime.now());
        updateById(oldUser);


    }

    @Override
    public boolean verifyTOTPPass(Integer userId, String pass) {
        User user = this.getById(userId);
        if (user == null)
            throw new IllegalArgumentException("用户不存在");
        String totpSecretKey = user.getTotpSecretKey();
        if (totpSecretKey == null || totpSecretKey.length() == 0)
            throw new IllegalArgumentException("当前用户未开启两步验证码");
        try {
            String target = TOTPUtil.nextCode(totpSecretKey,30,"SHA1");
            return target.equals(pass);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getTOTPSecretKey(Integer userId) {
        User user = getById(userId);
        if (user == null || user.getTotpSecretKey() == null
                || user.getTotpSecretKey().length() == 0) return null;
        return user.getTotpSecretKey();
    }

    @Override
    public void resetTOTPSecretKey(Integer userId) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        String totpSecretKey = UUID.randomUUID().toString().replaceAll("-","");
        updateWrapper.set(User::getTotpSecretKey,totpSecretKey);
        updateWrapper.eq(User::getId,userId);
        this.update(updateWrapper);
    }

}
