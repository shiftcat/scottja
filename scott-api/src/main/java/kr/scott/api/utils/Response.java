package kr.scott.api.utils;

import kr.scott.api.interceptor.RequestIdIntercepter;
import kr.scott.vo.ResBody;
import kr.scott.vo.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


/**
 * 응답 메시지를 구조(형식) 를 공틍으로 관리 하기 위한 클래스 이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */
public class Response
{


    /**
     * Request 고유 ID
     */
    private static String getRequestId()
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return String.valueOf(request.getAttribute(RequestIdIntercepter.REQUEST_ID_KEY));
    }




    private static ResponseVO makeResponseVO(HttpStatus status, List<String> msgs)
    {
        String requestId = getRequestId();

        ResponseVO responseVO = new ResponseVO();
        responseVO.setRequestId(requestId);
        responseVO.setCode(status.value());
        responseVO.setMessages(msgs);
        return responseVO;
    }


    private static ResponseVO makeResponseVO(HttpStatus status, String...msgs)
    {
        return makeResponseVO(status, Arrays.asList(msgs));
    }



    public static <T> ResponseEntity<ResBody<T>> makeResponse(T result)
    {
        ResponseVO responseVO = makeResponseVO(HttpStatus.OK, "Success");
        ResBody<T> resBody = new ResBody<T>();
        resBody.setResponseVO(responseVO);
        resBody.setResult(result);
        return ResponseEntity.ok(resBody);
    }


    public static <T> ResponseEntity<ResBody<T>> makeResponse(T result, HttpStatus status)
    {
        ResponseVO responseVO = makeResponseVO(status, "Success");
        ResBody<T> resBody = new ResBody<T>();
        resBody.setResponseVO(responseVO);
        resBody.setResult(result);
        ResponseEntity<ResBody<T>> responseEntity = new ResponseEntity(resBody, status);
        return responseEntity;
    }




    public static ResponseEntity<ResBody> makeResponse(HttpStatus status, List<String> msgs)
    {
        ResponseVO responseVO = makeResponseVO(status, msgs);
        ResBody resBody = new ResBody();
        resBody.setResponseVO(responseVO);
        ResponseEntity<ResBody> responseEntity = new ResponseEntity(resBody, status);
        return responseEntity;
    }

}
