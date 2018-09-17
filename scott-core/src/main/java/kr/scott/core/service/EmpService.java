package kr.scott.core.service;

import kr.scott.vo.emp.EmpRequestVO;
import kr.scott.vo.emp.EmpResponseVO;
import kr.scott.vo.emp.EmpSearchVO;
import kr.scott.vo.emp.EmpVO;

import java.util.List;


/**
 * 이 클래스는 사원 정보를 관리하기 위한 서비스 클래스이다.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
public interface EmpService
{

    /**
     * 사원 정보를 조회 한다.
     * @param searchVO
     * @return 조회 결과 리스트
     *
     */
    public List<EmpResponseVO> search(EmpSearchVO searchVO);


    /**
     * 사원 번호에 해당하는 사원 정보를 조회 한다.
     *
     * @param empno 사원 번호
     * @return 사원 정보
     * @exception kr.scott.core.errors.ContentNotFoundException 사원 번호에 해당하는 사원 정보가 존재하지 않을 경우 발생한다.
     */
    public EmpResponseVO get(int empno);


    /**
     * 사원 번호에 해당하는 사원 정보를 삭제 한다.
     *
     * @param empno 사원 번호
     * @exception kr.scott.core.errors.ContentNotFoundException 사원 번호에 해당하는 사원 정보가 존재하지 않을 경우 발생한다.
     *
     */
    public void remove(int empno);


    /**
     * 사원 정보를 저장한다. 사원 번호에 해당하는 사원정보가 존재할 경우 변경한다.
     *
     * @param empVO 사원정보
     * @return 처리 결과 사원 정보
     * @exception kr.scott.core.errors.ContentNotFoundException 부서번호에 대한 부서 번호가 존재하지 않을 경우 발생한다.
     */
    public EmpResponseVO save(EmpRequestVO empVO);

}
