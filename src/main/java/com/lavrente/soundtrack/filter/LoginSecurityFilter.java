package com.lavrente.soundtrack.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Created by 123 on 26.01.2017.
 */
@WebFilter(urlPatterns = {"/jsp/user/*"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class LoginSecurityFilter implements Filter {

    /** The path parameter. */
    private final String PATH_PARAMETER = "INDEX_PATH";

    /** The true. */
    private final String TRUE = "true";

    /** The is login attribute. */
    private final String IS_LOGIN_ATTRIBUTE = "is_login";

    /** The index path. */
    private String indexPath;

    /**
     * Inits the.
     *
     * @param fConfig the f config
     * @throws ServletException the servlet exception
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter(PATH_PARAMETER);
    }

    /**
     * Do filter.
     *
     * @param request the request
     * @param response the response
     * @param chain the chain
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Object isLogin=httpRequest.getSession().getAttribute(IS_LOGIN_ATTRIBUTE);
        if (isLogin==null || !TRUE.equals(isLogin.toString())) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }
        chain.doFilter(request, response);
    }

    /**
     * Destroy.
     */
    @Override
    public void destroy() {
    }
}
