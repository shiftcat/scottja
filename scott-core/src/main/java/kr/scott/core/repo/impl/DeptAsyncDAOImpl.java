package kr.scott.core.repo.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.scott.core.config.AsyncScott;
import kr.scott.core.entities.Dept;
import kr.scott.core.entities.QDept;
import kr.scott.core.repo.DeptAsyncDAO;
import kr.scott.core.repo.DeptDAO;
import kr.scott.core.repo.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.concurrent.CompletableFuture;


@Repository("DeptAsyncDAO")
public class DeptAsyncDAOImpl implements DeptAsyncDAO
{


    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DeptRepository deptRepository;


    @AsyncScott
    @Override
    public CompletableFuture<Dept> findByDeptno(int deptno)
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QDept qDept = QDept.dept;
        Dept dept = queryFactory.selectFrom(qDept).where(qDept.deptno.eq(deptno)).fetchOne();
        return CompletableFuture.completedFuture(dept);
    }


}
