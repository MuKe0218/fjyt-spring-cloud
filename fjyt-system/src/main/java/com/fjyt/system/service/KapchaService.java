package com.fjyt.system.service;

import com.fjyt.common.domain.R;
import com.fjyt.common.exception.CaptchaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author keQiLong
 * @date 2023年04月23日 11:05
 */
public interface KapchaService {
    /**
     * 生成验证码
     */
    public R createCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException, CaptchaException;

    /**
     * 校验验证码
     */
    public R checkCaptcha(String uuid, String value) throws CaptchaException;
}
