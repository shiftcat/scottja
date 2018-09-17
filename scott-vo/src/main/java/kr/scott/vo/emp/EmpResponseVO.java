package kr.scott.vo.emp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.scott.vo.dept.DeptVO;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 이 클래스는 사원 정보 처리 후 결과를 반환하기 위한 응답 클래스이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */

@ApiModel
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonPropertyOrder({"empno", "ename", "hiredate", "job", "mgr", "sal", "comm", "dept"})
@lombok.Data
@EqualsAndHashCode(callSuper=true)
public class EmpResponseVO extends EmpVO
{

    /**
     * 사원의 소속 부서 정보
     */
    @JsonProperty("dept")
    private DeptVO dept;


    /**
     * 입사일자
     */
    @ApiModelProperty(required = true, value = "입사일자", example = "2012-03-03")
    private Date hiredate;



    @JsonProperty("hiredate")
    public String getHiredate()
    {
        return dateFormat.format(hiredate);
    }


}
