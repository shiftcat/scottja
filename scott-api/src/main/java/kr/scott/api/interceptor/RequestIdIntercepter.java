package kr.scott.api.interceptor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * API 호출 시 고유 ID를 부여하기 위한 Intercepter 이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */
@Slf4j
public class RequestIdIntercepter extends HandlerInterceptorAdapter
{


    public static final String REQUEST_ID_KEY = "_REQUEST_ID_";

    private String generateRequestID()
    {
        return UUID.randomUUID().toString();
    }


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String requestId = generateRequestID();
        log.debug("Generate request id => {}", requestId);
        request.setAttribute(REQUEST_ID_KEY, requestId);
        MDC.put("request_id", requestId);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
        MDC.clear();
        log.debug("Request id clear.");
    }
}
