package kr.scott.flux.controller;


import kr.scott.core.service.EmpAsyncService;
import kr.scott.vo.emp.EmpRequestVO;
import kr.scott.vo.emp.EmpResponseVO;
import kr.scott.vo.emp.EmpSearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/v1/flux/emp")
public class EmpController
{

    @Autowired
    EmpAsyncService empService;


    @GetMapping("/search")
    public Mono<List<EmpResponseVO>> search(@Valid @ModelAttribute EmpSearchVO searchVO)
    {
        log.debug("emp search begin");
        CompletableFuture<List<EmpResponseVO>> res = empService.search(searchVO);
        log.debug("emp search end");
        return Mono.fromCompletionStage(res);
    }


    @GetMapping("/{empno}")
    public Mono<EmpResponseVO> get(@PathVariable Integer empno)
    {
        CompletableFuture<EmpResponseVO> res = empService.get(empno);
        return Mono.fromCompletionStage(res);
    }


    @DeleteMapping("/{empno}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer empno)
    {
        empService.remove(empno);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EmpResponseVO> save(@RequestBody @Valid EmpRequestVO requestVO)
    {
        log.debug("emp save begin");
        CompletableFuture<EmpResponseVO> res = empService.save(requestVO);
        log.debug("emp save end");
        return Mono.fromCompletionStage(res);
    }

}
