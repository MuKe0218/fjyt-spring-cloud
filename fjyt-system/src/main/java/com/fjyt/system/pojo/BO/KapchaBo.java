package com.fjyt.system.pojo.BO;

import lombok.Data;

/**
 * @author keQiLong
 * @date 2023年04月23日 11:40
 */
@Data
public class KapchaBo {
    // 验证码uuid
    private String uuid;
    // 验证码文本
    private String text;
}
