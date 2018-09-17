package kr.scott.core.errors;


/**
 * 이 예외 클래스는 존재하지 않은 데이터에 접근할 경우에 발생하는 예외이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */

public class ContentNotFoundException extends RuntimeException
{
    public ContentNotFoundException(String msg)
    {
        super(msg);
    }
}
