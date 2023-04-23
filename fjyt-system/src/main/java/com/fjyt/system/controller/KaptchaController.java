package com.fjyt.system.controller;

import com.fjyt.common.domain.R;
import com.fjyt.system.pojo.BO.KapchaBo;
import com.fjyt.system.service.KapchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author keQiLong
 * @date 2023年04月20日 10:16
 * 验证码controller
 */
@RestController
@RequestMapping("/kaptcha")
public class KaptchaController {

    @Autowired
    private KapchaService getKapchaService;
    @GetMapping("/getKapcha")
    //获取验证码接口
    public R getKapcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return getKapchaService.createCaptcha(request,response);
    }

        @PostMapping("/checkKapcha")
    //yaznh
    public R checkKapcha(@RequestBody KapchaBo kapchaBo) throws IOException {
        return getKapchaService.checkCaptcha(kapchaBo.getUuid(), kapchaBo.getText());
    }
}
