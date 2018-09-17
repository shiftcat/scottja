package kr.scott.core.errors;


/**
 *
 * 이 예외 클래스는 사용중인 데이터에 유효하지 않은 처리를 시도할 경우 발생하는 예외이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */

public class OccupiedException extends RuntimeException
{
    public OccupiedException(String msg)
    {
        super(msg);
    }
}
