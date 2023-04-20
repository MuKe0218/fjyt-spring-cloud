package com.fjyt.system.controller;

import com.fjyt.common.domain.R;
import com.fjyt.common.utils.Base64;
import com.fjyt.common.utils.uuid.IdUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author keQiLong
 * @date 2023年04月20日 10:16
 * 验证码controller
 */
@RestController
@RequestMapping("/kaptcha")
public class KaptchaController {

    @Autowired
    private Producer producer;
    @GetMapping("/getkapcha")
    //获取验证码接口
    public R getKapcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = producer.createText();
        // 将验证码存于session中
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
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
        String uuid = IdUtils.simpleUUID();
        map.put("captchaEnabled",true);
        map.put("uuid",uuid);
        map.put("img", Base64.encode(os.toByteArray()));
        return R.ok(map,"获取验证码成功");
    }
}
