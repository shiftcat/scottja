package kr.scott.vo.dept;


/**
 * 이 클래스는 부서 정보 처리 후 결과를 반환하기 위한 응답 클래스이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;

@ApiModel
@lombok.Data
@EqualsAndHashCode(callSuper=true)
public class DeptResponseVO extends DeptVO
{


}
