package kr.scott.flux.controller;


import kr.scott.core.service.DeptAsyncService;
import kr.scott.vo.dept.DeptRequestVO;
import kr.scott.vo.dept.DeptResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/v1/flux/dept")
public class DeptController
{

    @Autowired
    DeptAsyncService deptService;


    @GetMapping("/search")
    public Mono<List<DeptResponseVO>> search()
    {
        CompletableFuture<List<DeptResponseVO>> futureDeptList = deptService.search();
        return Mono.fromCompletionStage(futureDeptList);
    }


    @GetMapping("/{deptno}")
    public Mono<DeptResponseVO> get(@PathVariable Integer deptno)
    {
        CompletableFuture<DeptResponseVO> res = deptService.get(deptno);
        return Mono.fromCompletionStage(res);
    }


    @DeleteMapping("/{deptno}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer deptno)
    {
        deptService.remove(deptno);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DeptResponseVO> save(@RequestBody @Valid DeptRequestVO requestVO)
    {
        CompletableFuture<DeptResponseVO> res = deptService.save(requestVO);
        return Mono.fromCompletionStage(res);
    }

}
