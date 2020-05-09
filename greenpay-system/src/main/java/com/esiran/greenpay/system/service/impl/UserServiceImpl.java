package com.esiran.greenpay.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.system.entity.User;
import com.esiran.greenpay.system.entity.UserInputDTO;
import com.esiran.greenpay.system.entity.UserInputVo;
import com.esiran.greenpay.system.mapper.UserMapper;
import com.esiran.greenpay.system.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

}
