package kr.scott.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;
import java.util.Arrays;

/**
 *
 *  이 클래스는 필터를 등록하기 위한 클래스 이다.
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */

@Configuration
@Slf4j
public class FilterConfig
{

    /**
     * 인코딩 필터를 등록 한다.
     */
    @Bean
    public FilterRegistrationBean encodingFilter()
    {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setOrder(0);
        filterBean.setFilter(filter);
        filterBean.setUrlPatterns(Arrays.asList("*"));
        return filterBean;
    }



    @Bean
    public FilterRegistrationBean crosFilter(@Qualifier("RequestWrapFilter") Filter filter)
    {
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setOrder(1);
        filterBean.setFilter(filter);
        filterBean.setUrlPatterns(Arrays.asList("/api/*"));
        return filterBean;
    }


    /**
     * Request를  랩핑하는 필터를 등록 한다.
     * RequestBody 데이터를 한 번 읽으면 다시 읽기 불가능한 문제를 해결하기 위함이다.
     */
    @Bean
    public FilterRegistrationBean requestWrapFilter(@Qualifier("RequestWrapFilter") Filter filter)
    {
        log.debug("Register request warp filer");
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setOrder(2);
        filterBean.setFilter(filter);
        filterBean.setUrlPatterns(Arrays.asList("/api/*"));
        return filterBean;
    }

}
