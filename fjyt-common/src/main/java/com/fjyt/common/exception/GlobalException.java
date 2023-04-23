package com.fjyt.common.exception;

import com.fjyt.common.domain.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keQiLong
 * @date 2023年04月21日 16:02
 * 将异常信息发给前端
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalException {
    @ExceptionHandler(ServiceException.class)
    public R exceptionHandler(ServiceException exception){
        return R.fail(exception.getCode(),exception.getMessage());
    }
    @ExceptionHandler(CaptchaException.class)
    public R exceptionHandler(CaptchaException exception){
        return R.fail(exception.getMessage());
    }
}
