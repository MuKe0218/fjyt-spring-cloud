package com.fjyt.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author keQiLong
 * @date 2023年04月20日 14:53
 * 认证模块
 */
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
        System.out.println("(♥◠‿◠)ﾉﾞ  FJYT认证模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------   -------  --    --  ------- \n" +
                " |             |     ''    ''     |\n" +
                " |             |      '' ''       |\n" +
                " |------       |       '''        |\n" +
                " |             |        |         |\n" +
                " |             |        |         |\n" +
                " |        ''   |        |         |\n" +
                " |         ''  |        |         |\n" +
                " '          ''''        '         '");
    }
}
