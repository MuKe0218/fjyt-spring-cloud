package com.fjyt.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author keQiLong
 * @date 2023年04月20日 15:30
 * 接收login信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码uuid
     */
    private String uuid;
    /**
     * 验证码code
     */
    private String code;


}
