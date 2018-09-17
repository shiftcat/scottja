package kr.scott.flux.handler;

import kr.scott.core.service.DeptAsyncService;
import kr.scott.flux.utils.Validation;
import kr.scott.vo.dept.DeptRequestVO;
import kr.scott.vo.dept.DeptResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Component("DeptHandler")
public class DeptHandler
{


    @Autowired
    DeptAsyncService service;



    public Mono<ServerResponse> search(ServerRequest serverRequest)
    {
        CompletableFuture<List<DeptResponseVO>> res = service.search();
        return ServerResponse.ok().body(Mono.fromCompletionStage(res), List.class);
    }


    public Mono<ServerResponse> get(ServerRequest serverRequest)
    {
        String sdpno = serverRequest.pathVariable("deptno");
        CompletableFuture<DeptResponseVO> res = service.get(Integer.valueOf(sdpno));

        return ServerResponse.ok()
                .body(Mono.fromCompletionStage(res), DeptResponseVO.class);
    }


    public Mono<ServerResponse> remove(ServerRequest serverRequest)
    {
        String sdpno = serverRequest.pathVariable("deptno");
        service.remove(Integer.valueOf(sdpno));
        return ServerResponse.noContent().build();
    }


    public Mono<ServerResponse> save(ServerRequest serverRequest)
    {
        URI uri = serverRequest.uriBuilder().path("/newdept").build();

        return ServerResponse.created(uri)
                .body(
                    serverRequest
                            .bodyToMono(DeptRequestVO.class)
                            .map(dept -> {
                                Validation.validate(dept);
                                return service.save(dept);
                            })
                            .flatMap(c -> Mono.fromCompletionStage(c))
                , DeptResponseVO.class);
    }



}
