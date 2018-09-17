package kr.scott.api.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;


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
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kr.scott.api.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(
                        RequestMethod.GET, responseMessageListForGet()
                )
                .globalResponseMessage(
                        RequestMethod.POST, responseMessageListForPost()
                )
                .globalResponseMessage(
                        RequestMethod.PUT, responseMessageListForPost()
                )
                .globalResponseMessage(
                        RequestMethod.DELETE, responseMessageListForDelete()
                );
    }


    private List<ResponseMessage> responseMessageList()
    {
        List<ResponseMessage> responseMessages = Lists.newArrayList();
        responseMessages.add(new ResponseMessageBuilder().code(500).message("Internal server error").build());
        return responseMessages;
    }


    private List<ResponseMessage> responseMessageListForGet()
    {
        List<ResponseMessage> responseMessages = responseMessageList();
        responseMessages.add(new ResponseMessageBuilder().code(200).message("Success").build());
        responseMessages.add(new ResponseMessageBuilder().code(404).message("Content not found").build());
        return responseMessages;
    }


    private List<ResponseMessage> responseMessageListForDelete()
    {
        List<ResponseMessage> responseMessages = responseMessageList();
        responseMessages.add(new ResponseMessageBuilder().code(204).message("Not content").build());
        responseMessages.add(new ResponseMessageBuilder().code(404).message("Content not found").build());
        return responseMessages;
    }

    private List<ResponseMessage> responseMessageListForPost()
    {
        List<ResponseMessage> responseMessages = responseMessageList();
        responseMessages.add(new ResponseMessageBuilder().code(201).message("Success").build());
        responseMessages.add(new ResponseMessageBuilder().code(400).message("Invalid value.").build());
        return responseMessages;
    }



    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Scott API v1.0", "Api Documentation", "v1.0", "Scott service", "admin@scott.com", "", "/"
        );
        return apiInfo;
    }
}