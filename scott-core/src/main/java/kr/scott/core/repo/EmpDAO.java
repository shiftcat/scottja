package kr.scott.core.repo;

import kr.scott.core.entities.Emp;
import kr.scott.vo.emp.EmpSearchVO;

import java.util.List;


/**
 * 이 클래스는 사원 정보 처리를 위한 데이터 접근 클래스이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
public interface EmpDAO
{

    public List<Emp> findByNameByDeptno(EmpSearchVO searchVO);


    public Emp findByEmpno(int empno);


    public Emp findByEmpnoWithDept(int empno);


    public List<Emp> findByDeptno(int deptno);


    public long countByDeptno(int deptno);


    public void remove(int empno);


    public Emp save(Emp emp);

}
