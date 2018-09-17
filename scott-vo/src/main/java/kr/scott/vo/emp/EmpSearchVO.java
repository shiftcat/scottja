package kr.scott.vo.emp;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

/**
 *
 * 이 클스는 사원 정보를 조회하기 위한 검색 조건 정보를 담는 클래스이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */

@lombok.Data
public class EmpSearchVO
{

    /**
     * 사원 이름
     */
    @Length(max = 10)
    private String ename;

    /**
     * 부서 번호
     */
    @Min(0)
    private Integer deptno;


    /**
     * 조회 크기
     */
    @Min(0)
    private Integer size;

    /**
     *  조회 시작 위치
     */
    @Min(0)
    private Integer offset;



    public int getSize()
    {
        if(size == null) {
            return 0;
        }
        return size;
    }

    public int getOffset()
    {
        if(offset == null) {
            return 0;
        }
        return offset;
    }
}
