package kr.scott;

import kr.scott.config.ApiConfig;
import kr.scott.config.CoreConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;


/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
public class ScottApiApplication
{
    public static void main(String[] args)
    {
        new SpringApplicationBuilder(CoreConfig.class)
                .child(ApiConfig.class)
                .run(args);
    }
}
