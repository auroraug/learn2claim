package com.project1.servlet;


import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//过滤器对网页字符编码格式转换为UTF8
public class EncodeFilter implements Filter {

    private String encode = "utf-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if(filterConfig.getInitParameter("encode") != null){
            encode = filterConfig.getInitParameter("encode");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding(encode);
        response.setCharacterEncoding(encode);
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
