package kr.scott.core.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 이 클래스는 사원정보에 대한 데이터 모델을 정의한 클래스이다.
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
@Table(name="emp")
public class Emp implements Serializable
{

    /**
     * 사원 번호 (Primary key)
     */
    @Id
    @Column(name = "empno", nullable = false)
    private int empno;


    /**
     * 사원 이름
     */
    @Column(name="ename", nullable = false, length = 10)
    private String ename;


    /**
     * 사원 업무(역할)
     */
    @Column(name = "job", nullable = false, length = 9)
    private String job;


    /**
     * 관리자 사원 번호
     */
    @Column(name = "mgr", nullable = true)
    private Integer mgr;


    /**
     * 입사일자
     */
    @Column(name = "hiredate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date hiredate;


    /**
     * 사원 급여
     */
    @Column(name = "sal", precision = 7, scale = 2, nullable = false)
    private BigDecimal sal;


    /**
     *  사원 커미션
     */
    @Column(name="comm", precision = 7, scale = 2, nullable = true)
    private BigDecimal comm;


    /**
     * 소속 부서
     */
    @ManyToOne
    @JoinColumn(name = "deptno")
    private Dept dept;


}
