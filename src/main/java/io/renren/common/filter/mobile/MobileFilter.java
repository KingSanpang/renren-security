package io.renren.common.filter.mobile;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import io.renren.common.constant.CommonConstants;
import io.renren.common.constant.ErrorConstants;
import io.renren.common.utils.R;
import io.renren.common.utils.RedisUtils;
import io.renren.common.utils.SpringContextUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * <dl>
 * <dt>MobileFilter</dt>
 * <dd>Description:</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>CreateDate: 2017-10-25</dd>
 * </dl>
 *
 * @author Administrator
 */
@WebFilter(filterName="mobileFilter",urlPatterns={"/sms/signalling/*"})
public class MobileFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(MobileFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;
        R r;
        String url = req.getServletPath();
        String sign = req.getHeader(CommonConstants.MOBILE_AUTH_HEADER_SIGN);
        String userId = req.getHeader(CommonConstants.MOBILE_AUTH_HEADER_USERID);
        String timestamp = req.getHeader(CommonConstants.MOBILE_AUTH_HEADER_TIME);
        if(StringUtils.isBlank(sign) || StringUtils.isBlank(userId) || StringUtils.isBlank(timestamp)){
            logger.warn("非法请求，鉴权参数不足。sign={},userId={},timestamp={}", sign, userId, timestamp);
            r = R.error("非法请求，参数不足");
            returnError(resp, r);
            return ;
        }
        RedisUtils redisUtils = (RedisUtils) SpringContextUtils.getBean("redisUtils");
        String jsonStr = redisUtils.get(String.format(CommonConstants.REDIS_KEY_MOBILE_TOKEN, userId));
        if(StringUtils.isBlank(jsonStr)){
            r = R.error(ErrorConstants.MobileAuth.NOT_LOGIN, "请登录");
            returnError(resp, r);
            return ;
        }
        Map userTokenMap = new Gson().fromJson(jsonStr, HashMap.class);
        String token = (String) userTokenMap.get(CommonConstants.REDIS_KEY_MOBILE_TOKEN_TOKEN);
        if(StringUtils.isBlank(token)){
            r = R.error(ErrorConstants.MobileAuth.NOT_LOGIN, "请登录");
            returnError(resp, r);
            return ;
        }
        //TODO 登录过长，重新登录ErrorConstants.MobileAuth.NEED_REFRESH_TOKEN
        //url + userId + timestamp + token
        String a = url + userId + timestamp + token;
        String b = Hashing.md5().newHasher().putString(a, Charsets.UTF_8).hash().toString();
        //sign = `md5(B + B.substring(5,13))`
        String mySign = Hashing.md5().newHasher().putString(b + b.substring(5, 13), Charsets.UTF_8).hash().toString();
        if(!sign.equalsIgnoreCase(mySign)){
            r = R.error(ErrorConstants.MobileAuth.SIGN_NOT_EQUALS, "签名不匹配！");
            returnError(resp, r);
            return ;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
    private void returnError(HttpServletResponse resp, R r){
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        Writer writer = null;
        try {
            writer = resp.getWriter();
            writer.write(new Gson().toJson(r));
        } catch (IOException e) {
            logger.error("returnError error:", e);
        }finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.error("io close error:", e);
                }
            }
        }
    }
}
