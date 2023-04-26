package com.fjyt.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author keQiLong
 * @date 2023年04月19日 16:28
 */
@SpringBootApplication
@ComponentScan(value = "com.fjyt")
@EnableFeignClients
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
        System.out.println("(♥◠‿◠)ﾉﾞ  FJYT系统模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
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
