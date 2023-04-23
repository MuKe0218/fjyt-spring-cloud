package com.fjyt.system.api;

import com.fjyt.system.factory.RemoteUserFallbackFactory;
import com.fjyt.common.domain.LoginUserBo;
import com.fjyt.common.constant.SecurityConstants;
import com.fjyt.common.constant.ServiceNameConstants;
import com.fjyt.common.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author keQiLong
 * @date 2023年04月23日 14:10
 * 用户服务
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/user/info/{username}")
    public R<LoginUserBo> getUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
