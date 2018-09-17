package kr.scott.vo.emp;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 이 클래스는 사원 정보를 처리를 위한 요청 클래스 이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonPropertyOrder({"empno", "ename", "hiredate", "job", "mgr", "sal", "comm", "dept"})
@lombok.Data
@EqualsAndHashCode(callSuper=true)
public class EmpRequestVO extends EmpVO
{


    /**
     * 부서 번호
     */
    @ApiModelProperty(required = false, value = "소속부서", example = "60")
    @JsonProperty("deptno")
    private Integer deptno;


    /**
     * 입사일자
     */
    @ApiModelProperty(required = true, value = "입사일자", example = "2012-03-03")
    @NotNull
    private Date hiredate;



    @JsonProperty("hiredate")
    public String getHiredateString()
    {
        if(hiredate == null) {
            return null;
        }
        return dateFormat.format(hiredate);
    }


    public Date getHiredate()
    {
        return hiredate;
    }



}
