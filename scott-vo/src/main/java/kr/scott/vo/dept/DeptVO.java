package kr.scott.vo.dept;


import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * 이 클래스는 부서 정보를 담는 클래스이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
@lombok.Data
public class DeptVO
{

    /**
     * 부서 번호
     */
    @ApiModelProperty(required = true, value = "부서번호", example = "60")
    @Min(1)
    @Max(1000)
    @NotNull
    private Integer deptno;


    /**
     * 부서 이름
     */
    @ApiModelProperty(required = true, value = "부서이름", example = "연구소")
    @Length(min = 2, max = 14)
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$")
    private String dname;


    /**
     * 부서 위치
     */
    @ApiModelProperty(required = true, value = "부서위치", example = "서울 중구")
    @NotNull
    @Length(min = 2, max = 13)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]*$")
    private String loc;

}
