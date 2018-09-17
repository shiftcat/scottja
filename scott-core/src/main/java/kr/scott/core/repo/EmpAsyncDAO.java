package kr.scott.core.repo;

import kr.scott.core.entities.Emp;
import kr.scott.vo.emp.EmpSearchVO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EmpAsyncDAO
{

//    public CompletableFuture<List<Emp>> findByNameByDeptno(EmpSearchVO searchVO);


    public CompletableFuture<Emp> findByEmpno(int empno);


    public CompletableFuture<Emp> save(Emp emp);


}
