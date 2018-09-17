package kr.scott.core.config;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(value = "txManager")
public @interface TxScott {

    Propagation propagation() default Propagation.REQUIRED;

    boolean readOnly() default false;

}
