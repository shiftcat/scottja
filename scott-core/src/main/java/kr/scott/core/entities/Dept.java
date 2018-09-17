package kr.scott.core.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 이 클래스는 부서 정보에 대한 데이터 모델를 정의한 클래스이다.
 *
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dept")
public class Dept implements Serializable
{


    /**
     * 부서 번호
     */
    @Id
    @Column(name = "deptno", insertable = true, nullable = false)
    private int deptno;


    /**
     * 부서 이름
     */
    @Column(name = "dname", nullable = false, length = 14)
    private String dname;


    /**
     * 부서 위치
     */

    @Column(name = "loc", nullable = false, length = 13)
    private String loc;


}
