package kr.scott.vo.dept;


import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;

/**
 * 이 클래스는 부서 정보처리를 위한 요청 클래스이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
@ApiModel
@lombok.Data
@EqualsAndHashCode(callSuper=true)
public class DeptRequestVO extends DeptVO
{

}
