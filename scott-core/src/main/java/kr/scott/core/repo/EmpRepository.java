package kr.scott.core.repo;


import kr.scott.core.entities.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by yhlee on 2017-08-25.
 *
 *
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
public interface EmpRepository extends JpaRepository<Emp, String>, JpaSpecificationExecutor<Emp>
{
}
