package kr.scott.flux.config;

import kr.scott.flux.handler.DeptHandler;
import kr.scott.flux.handler.EmpHandler;
import kr.scott.flux.handler.HelloHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouteConfig
{

    @Bean
    public HelloHandler helloHandler()
    {
        return new HelloHandler();
    }



    @Bean
    RouterFunction<ServerResponse> helloRouterFunction(HelloHandler helloHandler)
    {
        return route(RequestPredicates.path("/"), helloHandler::handleRequest);
    }


    @Bean
    RouterFunction<ServerResponse> deptRouterFuncion(@Qualifier("DeptHandler") DeptHandler handler)
    {
        return RouterFunctions.nest(
                RequestPredicates.path("/v1/handle/dept"),
                 route(GET("/search").and(accept(APPLICATION_JSON)), handler::search)
                        .andRoute(GET("/{deptno}").and(accept(APPLICATION_JSON)), handler::get)
                         .andRoute(DELETE("/{deptno}").and(accept(APPLICATION_JSON)), handler::remove)
                        .andRoute(POST("").and(accept(APPLICATION_JSON)), handler::save)
        );
    }


    @Bean
    RouterFunction<ServerResponse> empRouterFuncion(@Qualifier("EmpHandler")EmpHandler handler)
    {
        return RouterFunctions.nest(
                RequestPredicates.path("/v1/handle/emp"),
                route(GET("/search").and(accept(APPLICATION_JSON)), handler::search)
                        .andRoute(GET("/{empno}").and(accept(APPLICATION_JSON)), handler::get)
                        .andRoute(DELETE("/{empno}").and(accept(APPLICATION_JSON)), handler::remove)
                        .andRoute(POST("").and(accept(APPLICATION_JSON)), handler::save)
        );
    }


}
