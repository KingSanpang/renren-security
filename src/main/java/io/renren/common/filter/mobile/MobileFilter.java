package io.renren.common.filter.mobile;

import com.google.gson.Gson;
import io.renren.common.constant.CommonConstants;
import io.renren.common.utils.R;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

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
        String url = req.getRequestURI();
        String sign = req.getHeader(CommonConstants.MOBILE_AUTH_HEADER_KEY);
        if(StringUtils.isBlank(sign)){
            logger.warn("非法请求，没有带sign。");
            r = R.error("非法请求");
            returnError(resp, r);
            return ;
        }

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
