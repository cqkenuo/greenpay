package com.esiran.greenpay.common.sign;

import com.esiran.greenpay.common.util.RSAUtil;

import java.nio.charset.StandardCharsets;

public class RSA2SignType extends SignTypeAbs {
    public RSA2SignType(String principal) {
        super(principal);
    }

    @Override
    public SignVerify sign(String credential) {
        return new RSASignVerify(getPrincipal(),credential);
    }

    @Override
    public String sign2(String credential) {
        if (getPrincipal() == null || getPrincipal().length() == 0) return null;
        byte[] principalBytes = getPrincipal().getBytes(StandardCharsets.UTF_8);
        return RSAUtil.sign(principalBytes,credential);
    }
}
