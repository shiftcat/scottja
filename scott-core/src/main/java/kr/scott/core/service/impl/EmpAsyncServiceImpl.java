package kr.scott.core.service.impl;

import kr.scott.core.config.AsyncScott;
import kr.scott.core.service.EmpAsyncService;
import kr.scott.core.service.EmpService;
import kr.scott.vo.emp.EmpRequestVO;
import kr.scott.vo.emp.EmpResponseVO;
import kr.scott.vo.emp.EmpSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service("EmpAsyncService")
public class EmpAsyncServiceImpl implements EmpAsyncService
{

    @Autowired
    EmpService service;


    @AsyncScott
    @Override
    public CompletableFuture<List<EmpResponseVO>> search(EmpSearchVO searchVO)
    {
        return CompletableFuture.supplyAsync(() -> {
            return service.search(searchVO);
        });
    }


    @AsyncScott
    @Override
    public CompletableFuture<EmpResponseVO> get(int empno)
    {
        return CompletableFuture.supplyAsync(() -> {
            return service.get(empno);
        });
    }


    @AsyncScott
    @Override
    public CompletableFuture<Void> remove(int empno)
    {
        return CompletableFuture.runAsync(() -> {
            service.remove(empno);
        });
    }


    @AsyncScott
    @Override
    public CompletableFuture<EmpResponseVO> save(EmpRequestVO empVO)
    {
        return CompletableFuture.supplyAsync(() -> {
            return service.save(empVO);
        });
    }
}
