package kr.scott.flux.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class Validation
{

    private static ApplicationContext context;


    @Autowired
    public void setApplicationContext(ApplicationContext context)
    {
        Validation.context = context;
    }


    public static void validate(Object bean)
    {
        log.debug("=== validate begin ===");
        Validator validator = context.getBean(Validator.class);
        DataBinder binder = new DataBinder(bean);
        binder.setValidator(validator);
        binder.validate();
        BindingResult bindingResult = binder.getBindingResult();
        bindingResult.getFieldErrors().stream().forEach(e -> log.debug("{} : {}", e.getField(), e.getDefaultMessage()));
        if(bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

    }
}
