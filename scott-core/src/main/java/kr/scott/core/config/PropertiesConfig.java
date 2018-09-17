package kr.scott.core.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class PropertiesConfig
{

    @Profile("test")
    @Bean("properties")
    public static PropertiesFactoryBean propertiesForTest()
    {
        Resource[] resources = new ClassPathResource[]{ new ClassPathResource("config/test/application-test.properties") };
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocations(resources);
        return propertiesFactoryBean;
    }


//    @Profile("real")
//    @Bean("properties")
//    public static PropertiesFactoryBean propertiesReal()
//    {
//        Resource[] resources = new ClassPathResource[]{ new ClassPathResource("config/real/application-real.properties") };
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocations(resources);
//        return propertiesFactoryBean;
//    }

}
