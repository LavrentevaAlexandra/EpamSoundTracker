package com.lavrente.soundtrack.filter;

import com.lavrente.soundtrack.entity.User;

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
@WebFilter(urlPatterns = {"/jsp/admin/*"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class AdminSecurityFilter implements Filter {
    private final String PATH_PARAMETER = "INDEX_PATH";
    private final String USER_ATTRIBUTE = "user";
    private String indexPath;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter(PATH_PARAMETER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        User user=(User)httpRequest.getSession().getAttribute(USER_ATTRIBUTE);
        if (user==null || user.getRole()!=1) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
