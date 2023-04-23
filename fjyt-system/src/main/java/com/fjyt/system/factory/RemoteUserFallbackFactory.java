package com.fjyt.system.factory;

import com.fjyt.common.domain.LoginUserBo;
import com.fjyt.common.domain.R;
import com.fjyt.system.api.RemoteUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author keQiLong
 * @date 2023年04月23日 14:11
 * 用户服务降级处理
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteUserService create(final Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserService()
        {
            @Override
            public R<LoginUserBo> getUserInfo(String username, String source)
            {
                return R.fail("获取用户失败:" + throwable.getMessage());
            }

//            @Override
//            public R<Boolean> registerUserInfo(SysUser sysUser, String source)
//            {
//                return R.fail("注册用户失败:" + throwable.getMessage());
//            }
        };
    }
}
