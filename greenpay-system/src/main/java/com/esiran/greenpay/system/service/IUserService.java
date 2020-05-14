package com.esiran.greenpay.system.service;

import com.esiran.greenpay.common.exception.PostResourceException;
import com.esiran.greenpay.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.esiran.greenpay.system.entity.UserInputDTO;
import com.esiran.greenpay.system.entity.UserInputVo;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
public interface IUserService extends IService<User> {
    void updateUser(Integer userId, UserInputDTO userInputDTO) throws PostResourceException;
    void updateUserPWD(Integer integer, UserInputVo userInputVo) throws PostResourceException;
    boolean verifyTOTPPass(Integer userId, String pass);
    String getTOTPSecretKey(Integer userId);
    void resetTOTPSecretKey(Integer userId);
}
