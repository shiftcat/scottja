package kr.scott.core.repo.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.scott.core.entities.Emp;
import kr.scott.core.entities.QDept;
import kr.scott.core.entities.QEmp;
import kr.scott.core.repo.EmpDAO;
import kr.scott.core.repo.EmpRepository;
import kr.scott.vo.emp.EmpSearchVO;
import org.apache.commons.lang3.StringUtils;
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
public class EmpDAOImpl implements EmpDAO
{

    @PersistenceContext
    private EntityManager em;


    @Autowired
    private EmpRepository empRepository;



    @Override
    public List<Emp> findByNameByDeptno(EmpSearchVO searchVO)
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QEmp qEmp = QEmp.emp;
        QDept qDept = QDept.dept;

        JPAQuery query = queryFactory.selectFrom(qEmp).leftJoin(qEmp.dept, qDept).fetchJoin().where();
        if(StringUtils.isNotEmpty(searchVO.getEname())) {
            query.where(qEmp.ename.contains(searchVO.getEname()));
        }
        if(searchVO.getDeptno() != null && searchVO.getDeptno() > 0) {
            query.where(qDept.deptno.eq(searchVO.getDeptno()));
        }
        if(searchVO.getSize() > 0) {
            if(searchVO.getSize() > 1000) {
                searchVO.setSize(1000);
            }
            query.limit(searchVO.getSize());
        }
        if(searchVO.getOffset() > -1) {
            query.offset(searchVO.getOffset());
        }
        return query.fetch();
    }


    @Override
    public Emp findByEmpno(int empno)
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QEmp qEmp = QEmp.emp;

        return queryFactory.selectFrom(qEmp).where(qEmp.empno.eq(empno)).fetchOne();
    }


    @Override
    public Emp findByEmpnoWithDept(int empno)
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QEmp qEmp = QEmp.emp;
        QDept qDept = QDept.dept;

        Emp emp =
                queryFactory.selectFrom(qEmp)
                        .leftJoin(qEmp.dept, qDept).fetchJoin()
                        .where(qEmp.empno.eq(empno)).fetchOne();

        return emp;
    }




    @Override
    public List<Emp> findByDeptno(int deptno)
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QDept qDept = QDept.dept;
        QEmp qEmp = QEmp.emp;

        return queryFactory.selectFrom(qEmp).where(qDept.deptno.eq(deptno)).fetch();
    }



    @Override
    public long countByDeptno(int deptno)
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QDept qDept = QDept.dept;
        QEmp qEmp = QEmp.emp;

        return queryFactory.selectFrom(qEmp).where(qDept.deptno.eq(deptno)).fetchCount();
    }



    @Override
    public void remove(int empno)
    {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QEmp qEmp = QEmp.emp;

        queryFactory.delete(qEmp).where(qEmp.empno.eq(empno)).execute();
    }


    @Override
    public Emp save(Emp emp)
    {
        return empRepository.save(emp);
    }
}
