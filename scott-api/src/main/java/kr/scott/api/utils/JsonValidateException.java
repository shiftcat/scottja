package kr.scott.api.utils;

import java.util.List;

/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */
public class JsonValidateException extends RuntimeException
{

    List<String> validateMessage;


    public JsonValidateException(String message)
    {
        super(message);
    }


    public JsonValidateException(String message, List<String> messageList)
    {
        super(message);
        this.validateMessage = messageList;
    }


    public List<String> getValidateMessage()
    {
        return this.validateMessage;
    }

}
