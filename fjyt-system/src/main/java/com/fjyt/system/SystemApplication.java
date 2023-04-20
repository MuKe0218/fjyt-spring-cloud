package com.fjyt.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author keQiLong
 * @date 2023年04月19日 16:28
 */
@SpringBootApplication
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
