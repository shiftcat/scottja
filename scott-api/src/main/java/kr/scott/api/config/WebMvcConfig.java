package kr.scott.api.config;

import kr.scott.api.interceptor.RequestIdIntercepter;
import kr.scott.api.interceptor.RequestLoggerIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter
{

//    @Bean
//    public ViewResolver getViewResolver()
//    {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/jsp/");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }


    private static final String URL_PATTERN = "/api/**";

    /**
     * Intercept를 등록한다.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new RequestIdIntercepter()).addPathPatterns(URL_PATTERN);
        registry.addInterceptor(new RequestLoggerIntercepter()).addPathPatterns(URL_PATTERN);
//        registry.addInterceptor(new ValidationIterceptor()).addPathPatterns(URL_PATTERN);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        // swagger 관련 설정.
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
