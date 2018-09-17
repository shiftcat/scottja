package kr.scott.core.repo;

import kr.scott.core.entities.Dept;

import java.util.List;


/**
 *
 * 이 클래스는 부서 정보 처리를 위한 데이터 접근 클래스이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
public interface DeptDAO
{

    public List<Dept> findAll();


    public Dept findByDeptno(int deptno);


    public void remove(int deptno);


    public Dept save(Dept dept);

}
