package kr.scott.api.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Enumeration;


/**
 * 사용자 호출 시애 대한 정보를 로깅하기 위한 Intercepter 이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */
@Slf4j
public class RequestLoggerIntercepter extends HandlerInterceptorAdapter
{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        log.debug("=== Request Begin ===");
        log.debug("RequestURI => {}", request.getRequestURI());

        HttpMethod method = HttpMethod.resolve(request.getMethod());
        log.debug("Request Method => {}", method);

        Enumeration<String> headerKeysNum = request.getHeaderNames();
        Collections.list(headerKeysNum).forEach(k -> log.debug("Header {} => {}", k, request.getHeader(k)));

        if(method == HttpMethod.POST || method == HttpMethod.PUT) {
            String bodyData = ((RereadableRequestWrapper)request).getBody();
            log.debug("Request Body => {}", bodyData);
        }
        else {
            log.debug("Request QueryString => {}", request.getQueryString());
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        log.debug("Request postHandle ====");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
        log.debug("Request afterCompletion ====");
    }

}
