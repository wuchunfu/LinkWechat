package com.linkwechat.web.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.linkwechat.common.constant.SecurityConstants;
import com.linkwechat.common.context.SecurityContextHolder;
import com.linkwechat.common.core.domain.model.LoginUser;
import com.linkwechat.common.core.domain.model.WxLoginUser;
import com.linkwechat.common.utils.ServletUtils;
import com.linkwechat.common.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 *
 * @author xueyi
 */
@Component
public class RequestContextInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        SecurityContextHolder.setCorpId(ServletUtils.getHeader(request, SecurityConstants.CORP_ID));
        SecurityContextHolder.setCorpName(ServletUtils.getHeader(request, SecurityConstants.CORP_NAME));
        SecurityContextHolder.setUserId(ServletUtils.getHeader(request, SecurityConstants.USER_ID));
        SecurityContextHolder.setUserName(ServletUtils.getHeader(request, SecurityConstants.USER_NAME));
        SecurityContextHolder.setUserType(ServletUtils.getHeader(request, SecurityConstants.USER_TYPE));
        SecurityContextHolder.setUserKey(ServletUtils.getHeader(request, SecurityConstants.USER_KEY));

        String loginType = ServletUtils.getHeader(request, SecurityConstants.LOGIN_TYPE);

        String loginUserStr = ServletUtils.getHeader(request, SecurityConstants.LOGIN_USER);
        if(StringUtils.isNotEmpty(loginUserStr)){
            if(StringUtils.isNotEmpty(loginType) && loginType.equals("LinkWeChatWXAPI")){
                SecurityContextHolder.set(SecurityConstants.Details.LOGIN_USER.getCode(), JSONObject.parseObject(loginUserStr, WxLoginUser.class));
            }else {
                SecurityContextHolder.set(SecurityConstants.Details.LOGIN_USER.getCode(), JSONObject.parseObject(loginUserStr, LoginUser.class));
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        SecurityContextHolder.remove();
    }
}