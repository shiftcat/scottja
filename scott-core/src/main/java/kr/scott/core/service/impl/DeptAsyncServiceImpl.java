package kr.scott.core.service.impl;

import kr.scott.core.config.AsyncScott;
import kr.scott.core.entities.Dept;
import kr.scott.core.repo.DeptAsyncDAO;
import kr.scott.core.repo.DeptDAO;
import kr.scott.core.service.DeptAsyncService;
import kr.scott.core.service.DeptService;
import kr.scott.vo.dept.DeptRequestVO;
import kr.scott.vo.dept.DeptResponseVO;
import kr.scott.vo.dept.DeptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service("DeptAsyncService")
public class DeptAsyncServiceImpl implements DeptAsyncService
{

    @Autowired
    private DeptService service;


    @Override
    @AsyncScott
    public CompletableFuture<List<DeptResponseVO>> search()
    {
        return CompletableFuture.supplyAsync(() -> {
            return service.search();
        });
    }


    @AsyncScott
    @Override
    public CompletableFuture<DeptResponseVO> get(int deptno)
    {
        return CompletableFuture.supplyAsync(() -> {
                return service.get(deptno);
        });
    }


    @AsyncScott
    @Override
    public CompletableFuture<Void> remove(int deptno)
    {
        return CompletableFuture.runAsync(() -> service.remove(deptno));
    }


    @AsyncScott
    @Override
    public CompletableFuture<DeptResponseVO> save(DeptRequestVO deptVO)
    {
        return CompletableFuture.supplyAsync(() -> {
            return service.save(deptVO);
        });
    }

}
