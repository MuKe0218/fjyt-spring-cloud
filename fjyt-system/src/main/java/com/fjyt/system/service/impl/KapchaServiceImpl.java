package com.fjyt.system.service.impl;

import com.fjyt.common.constant.CacheConstants;
import com.fjyt.common.constant.Constants;
import com.fjyt.common.domain.R;
import com.fjyt.common.exception.CaptchaException;
import com.fjyt.common.redis.service.RedisService;
import com.fjyt.common.utils.Base64;
import com.fjyt.common.utils.StringUtils;
import com.fjyt.common.utils.uuid.IdUtils;
import com.fjyt.system.service.KapchaService;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author keQiLong
 * @date 2023年04月23日 11:05
 * 验证码
 */
@Service
public class KapchaServiceImpl implements KapchaService {

    @Autowired
    private Producer producer;
    @Autowired
    private RedisService redisService;
    public R createCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException, CaptchaException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = producer.createText();
        // 将验证码存于redis中
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        redisService.setCacheObject(verifyKey, capText, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
//        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY+uuid, capText);
        BufferedImage bi = producer.createImage(capText);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        // 向页面输出验证码
        ImageIO.write(bi, "jpg", os);
        try {
            // 清空缓存区
            os.flush();
        } finally {
            // 关闭输出流
            os.close();
        }
        Map<String,Object> map = new Hashtable();
        map.put("captchaEnabled",true);
        map.put("uuid",uuid);
        map.put("img", Base64.encode(os.toByteArray()));
        return R.ok(map,"获取验证码成功");
    }

    public R checkCaptcha(String uuid, String value) throws CaptchaException {
        if (StringUtils.isEmpty(value))
        {
            throw new CaptchaException("验证码不能为空");
        }
        if (StringUtils.isEmpty(uuid))
        {
            throw new CaptchaException("验证码已失效");
        }
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisService.getCacheObject(verifyKey);
        redisService.deleteObject(verifyKey);
        if (!value.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException("验证码错误");
        }
        return R.ok("通过");
    }
}
