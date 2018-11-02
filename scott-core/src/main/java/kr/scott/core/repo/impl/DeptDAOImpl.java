package kr.scott.core.repo.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.scott.core.entities.Dept;
import kr.scott.core.entities.QDept;
import kr.scott.core.repo.DeptDAO;
import kr.scott.core.repo.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
@Repository
public class DeptDAOImpl implements DeptDAO
{
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DeptRepository deptRepository;


    @Override
    public List<Dept> findAll()
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QDept qDept = QDept.dept;
        return queryFactory.selectFrom(qDept).fetch();
    }

    @Override
    public Dept findByDeptno(int deptno)
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QDept qDept = QDept.dept;
        return queryFactory.selectFrom(qDept)
                .where(qDept.deptno.eq(deptno)).fetchOne();
    }

    @Override
    public void remove(int deptno)
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QDept qDept = QDept.dept;
        queryFactory.delete(qDept)
                .where(QDept.dept.deptno.eq(deptno)).execute();
    }

    @Override
    public Dept save(Dept dept)
    {
        return deptRepository.save(dept);
    }
}
