package kr.scott.flux.handler;

import kr.scott.core.service.EmpAsyncService;
import kr.scott.flux.utils.Validation;
import kr.scott.vo.emp.EmpRequestVO;
import kr.scott.vo.emp.EmpResponseVO;
import kr.scott.vo.emp.EmpSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Objects;


@Component("EmpHandler")
public class EmpHandler
{


    @Autowired
    EmpAsyncService service;


    public Mono<ServerResponse> search(ServerRequest request)
    {
        MultiValueMap<String, String> param = request.queryParams();
        EmpSearchVO searchVO = new EmpSearchVO();
        searchVO.setEname( Objects.isNull(param.getFirst("ename")) ? null : String.valueOf(param.getFirst("ename")));
        searchVO.setDeptno( Objects.isNull(param.getFirst("deptno")) ? null : Integer.parseInt(param.getFirst("deptno")) );
        searchVO.setSize( Objects.isNull(param.getFirst("size")) ? null : Integer.parseInt(param.getFirst("size")) );
        searchVO.setOffset( Objects.isNull(param.getFirst("offset")) ? null : Integer.parseInt(param.getFirst("offset")) );
        Validation.validate(searchVO);

        return ServerResponse.ok()
                .body(
                    Mono.fromCompletionStage(service.search(searchVO))
                        , List.class);
    }



    public Mono<ServerResponse> get(ServerRequest request)
    {
        String seno = request.pathVariable("empno");

        return ServerResponse.ok()
                .body(
                        Mono.fromCompletionStage(service.get(Integer.valueOf(seno)))
                        , EmpResponseVO.class);
    }



    public Mono<ServerResponse> remove(ServerRequest request)
    {
        String seno = request.pathVariable("empno");
        service.remove(Integer.valueOf(seno));
        return ServerResponse.noContent().build();
    }



    public Mono<ServerResponse> save(ServerRequest request)
    {
        URI uri = request.uriBuilder().path("/newemp").build();

        return ServerResponse.created(uri)
                .body(
                    request
                            .bodyToMono(EmpRequestVO.class)
                            .map(ne -> {
                                Validation.validate(ne);
                                return service.save(ne);
                            })
                            .flatMap(res -> Mono.fromCompletionStage(res))
                , EmpResponseVO.class);
    }




}