package kr.scott.api.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.common.base.Throwables;
import kr.scott.api.utils.JsonValidateException;
import kr.scott.api.utils.Response;
import kr.scott.core.errors.ContentNotFoundException;
import kr.scott.core.errors.OccupiedException;
import kr.scott.vo.ResBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 이 클래스는 예외처리 핸들러를 설정 하는 클래스 이다.
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */

@ControllerAdvice
@Slf4j
public class RestExceptionHandler
{


    /*
    ===================================================================
        JSON Schema 를 이용하여 입력값을 검사는 하는 경우에 발생한 예외처리를 하는 핸들러
    ==================================================================
     */
    /**
     * 사용자가 입력한 JSON이 유효하지 않을 경우 발생한 예외를 처리 한다.
     * @deprecated JSON scheam 를 이용하여 입력값을 검사하는 경우에 사용한다.
     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonParseException.class)
    @ResponseBody
    public ResponseEntity<ResBody> handleJsonParseError(HttpServletRequest req, JsonParseException exception) throws Exception
    {
        log.warn(exception.getMessage());
        return Response.makeResponse(HttpStatus.BAD_REQUEST, Arrays.asList(exception.getMessage()));
    }




    /**
     * 사용자가 입력한 값이 유효하지 않을 경우 발생한 예외를 처리 한다.
     * @deprecated JSON scheam 를 이용하여 입력값을 검사하는 경우에 사용한다.
     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonValidateException.class)
    @ResponseBody
    public ResponseEntity<ResBody> handleInvalidError(HttpServletRequest req, JsonValidateException exception) throws Exception
    {
        log.warn(exception.getMessage());
        return Response.makeResponse(HttpStatus.BAD_REQUEST, exception.getValidateMessage());
    }



    /*
    ===================================================================
        Bean Validation를 이용하여 입력값을 검사하는 경우에 발생한 예외처리 핸들러
    ==================================================================
     */

    /**
     *
     *
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<ResBody> handleBindError(HttpServletRequest req, BindException exception) throws Exception
    {
        log.warn(exception.getMessage());
        List<FieldError> filedErrors = exception.getBindingResult().getFieldErrors();
        return Response.makeResponse(HttpStatus.BAD_REQUEST, filedErrors.stream().map(fe -> fe.getField() + ": " + fe.getDefaultMessage()).collect(Collectors.toList()));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ResBody> handleNotNullError(HttpServletRequest req, MethodArgumentNotValidException exception) throws Exception
    {
        log.warn(exception.getMessage());
        List<FieldError> filedErrors = exception.getBindingResult().getFieldErrors();
        return Response.makeResponse(HttpStatus.BAD_REQUEST, filedErrors.stream().map(fe -> fe.getField() + ": " + fe.getDefaultMessage()).collect(Collectors.toList()));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ResBody> handleMessageNotReadableError(HttpServletRequest req, HttpMessageNotReadableException exception) throws Exception
    {
        log.warn(exception.getMessage());
        return Response.makeResponse(HttpStatus.BAD_REQUEST, Arrays.asList(exception.getMessage()));
    }



    /**
     * 사용중인 정보에 대한 유효하지 않은 처리를 시도할 경우 발생한 예외를 처리 한다.
     */
    @ExceptionHandler(OccupiedException.class)
    @ResponseBody
    public ResponseEntity<ResBody> handleOccupiedError(HttpServletRequest req, Exception exception) throws Exception
    {
        log.warn(exception.getMessage());
        return Response.makeResponse(HttpStatus.BAD_REQUEST, Arrays.asList(exception.getMessage()));
    }


    /**
     * 존재하지 않은 정보에 접근할 경우 발생한 예외를 처리 한다.
     */
    @ExceptionHandler(ContentNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ResBody> handleContentNotFound(HttpServletRequest req, Exception exception) throws Exception
    {
        log.warn(exception.getMessage());
        return Response.makeResponse(HttpStatus.NOT_FOUND, Arrays.asList(exception.getMessage()));
    }


    /**
     * 서버 내부에 발생한 예외를 처리 한다.
     *
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResBody> handleInternalError(HttpServletRequest req, Exception exception) throws Exception
    {
        log.error(Throwables.getStackTraceAsString(exception));
        return Response.makeResponse(HttpStatus.INTERNAL_SERVER_ERROR, Arrays.asList(exception.getMessage()));
    }



}
