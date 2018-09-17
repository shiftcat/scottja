package kr.scott.core.repo;

import kr.scott.core.entities.Dept;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DeptAsyncDAO
{

//    public CompletableFuture<List<Dept>> findAll();


    public CompletableFuture<Dept> findByDeptno(int deptno);


}
