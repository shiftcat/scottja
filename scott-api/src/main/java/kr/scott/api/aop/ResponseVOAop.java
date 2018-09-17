package kr.scott.api.aop;

import kr.scott.api.interceptor.RequestIdIntercepter;
import kr.scott.vo.ResBody;
import kr.scott.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */
//@Aspect
//@Component
@Slf4j
public class ResponseVOAop
{



//    @Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointCut(){}

//    @Before("controllerPointCut()")
////    @Before("@annotation(org.springframework.web.bind.annotation.RestController)")
//    public void beforController(JoinPoint joinPoint)
//    {
//        System.out.println("#>> Controller call befor complete");
//    }

    private String getRequestId()
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return String.valueOf(request.getAttribute(RequestIdIntercepter.REQUEST_ID_KEY));
    }

    @Around("controllerPointCut()")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable
    {
        Object rslt = null;
        try {
            rslt = joinPoint.proceed();
        }
        catch (Exception ex) {
            log.error("{}" , ex);
        }

        String requestId = getRequestId();

        ResponseVO responseVO = new ResponseVO();
        responseVO.setRequestId(requestId);
        responseVO.setCode(HttpStatus.OK.value());
        responseVO.setMessages(Arrays.asList("Success"));

        ResBody resBody = new ResBody();
        resBody.setResponseVO(responseVO);
        resBody.setResult(rslt);

        log.debug("Response body => {}", resBody);
        return resBody;
    }



//    @AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.RestController)", returning = "retVal")
//    @AfterReturning(pointcut = "controllerPointCut()", returning = "retVal")
//    public void afterComplete(JoinPoint joinPoint, Object retVal)
//    {
//        System.out.println("#>> Controller call after complete");
//    }

}
