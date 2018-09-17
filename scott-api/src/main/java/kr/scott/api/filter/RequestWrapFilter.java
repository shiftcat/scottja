package kr.scott.api.filter;



import kr.scott.api.interceptor.RereadableRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



/**
 *
 * 사용자 용자로 부터 온 Request  랩핑하기 위한 필터이다.
 *
 * RequestBody 데이터를 한 번 읽으면 다시 읽기 불가한 문제를 해결하기 위한 필터이다.
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */

@Slf4j
@Component("RequestWrapFilter")
public class RequestWrapFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        log.debug("Request wrapper begin");
        RereadableRequestWrapper requestWrapper = new RereadableRequestWrapper((HttpServletRequest)request);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() { }
}
