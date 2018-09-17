package kr.scott.core.repo.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.scott.core.config.AsyncScott;
import kr.scott.core.entities.Emp;
import kr.scott.core.entities.QEmp;
import kr.scott.core.repo.EmpAsyncDAO;
import kr.scott.core.repo.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.concurrent.CompletableFuture;

public class EmpAsyncDAOImpl implements EmpAsyncDAO
{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EmpRepository empRepository;


    @AsyncScott
    @Override
    public CompletableFuture<Emp> findByEmpno(int empno)
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QEmp qEmp = QEmp.emp;

        Emp emp = queryFactory.selectFrom(qEmp).where(qEmp.empno.eq(empno)).fetchOne();

        return CompletableFuture.completedFuture(emp);
    }



    @AsyncScott
    @Override
    public CompletableFuture<Emp> save(Emp emp)
    {
        Emp ne = empRepository.save(emp);
        return CompletableFuture.completedFuture(ne);
    }


}
