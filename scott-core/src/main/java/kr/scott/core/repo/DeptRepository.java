package kr.scott.core.repo;

import kr.scott.core.entities.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
public interface DeptRepository extends JpaRepository<Dept, Integer>, JpaSpecificationExecutor<Dept>
{
}
