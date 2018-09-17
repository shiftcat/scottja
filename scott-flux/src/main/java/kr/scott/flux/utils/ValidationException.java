package kr.scott.flux.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ValidationException extends RuntimeException
{

    private final BindingResult bindingResult;

    public ValidationException(BindingResult bindingResult)
    {
        this.bindingResult = bindingResult;
    }


    public String getMessage() {
        return this.bindingResult.toString();
    }

    public List<FieldError> getFieldErrors() {
        return this.bindingResult.getFieldErrors();
    }

    public final BindingResult getBindingResult() {
        return this.bindingResult;
    }
}
