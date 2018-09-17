package kr.scott.vo.emp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;


/**
 * 이 클래스는 사원정보를 담는 클래스이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */

@Getter
@Setter
public class EmpVO
{

    /**
     *  사원번호
     */
    @ApiModelProperty(required = true, value = "사원번호", example = "1234")
    @JsonProperty("empno")
    @NotNull
    @Min(1)
    @Max(1000000)
    private Integer empno;



    /**
     * 사원 이름
     */
    @ApiModelProperty(required = true, value = "사원이름", example = "홍길동")
    @JsonProperty("ename")
    @NotNull
    @Length(min = 2, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$")
    private String ename;

    /**
     * 사원 업무
     */
    @ApiModelProperty(required = true, value = "업무", example = "개발자")
    @JsonProperty("job")
    @NotNull
    @Length(min = 2, max = 9)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$")
    private String job;


    /**
     * 관리자 사원 번호
     */
    @ApiModelProperty(required = false, value = "관라자 사번", example = "1000")
    @JsonProperty("mgr")
    @Min(0)
    @Max(1000000)
    private Integer mgr;


    /**
     * 급여
     */
    @ApiModelProperty(required = true, value = "급여", example = "7500")
    @JsonProperty("sal")
    @Min(0)
    @Max(99999)
    @NotNull
    private BigDecimal sal;


    /**
     * 커미션
     */
    @ApiModelProperty(required = false, value = "커미션", example = "1500")
    @JsonProperty("comm")
    @Min(0)
    @Max(99999)
    private BigDecimal comm;



    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
}
