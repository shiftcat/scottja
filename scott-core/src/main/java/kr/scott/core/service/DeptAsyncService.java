package kr.scott.core.service;

import kr.scott.vo.dept.DeptRequestVO;
import kr.scott.vo.dept.DeptResponseVO;
import kr.scott.vo.dept.DeptVO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DeptAsyncService
{


    /**
     * 부서 정보를 조회 한다.
     *
     * @return 부서 정보 리스트
     */
    public CompletableFuture<List<DeptResponseVO>> search();

    /**
     * 부서 번호에 해당하는 부서를 조회 한다.
     * @param deptno 부서 번호
     * @return 부서 번호에 해당하는 부서 정보
     * @exception kr.scott.core.errors.ContentNotFoundException 부서 번호에 해당한는 정보가 존재하지 않을 경우 발생한다.
     *
     */
    public CompletableFuture<DeptResponseVO> get(int deptno);


    /**
     * 부서 번호에 해당하는 정보를 삭제 한다.
     *
     * @param deptno 부서 번호
     * @exception kr.scott.core.errors.ContentNotFoundException 부서 번호에 해당한는 정보가 존재하지 않을 경우 발생한다.
     * @exception kr.scott.core.errors.OccupiedException 사용주인 부서를 삭제할 경우에 발생한다.
     */
    public CompletableFuture<Void> remove(int deptno);


    /**
     * 부서 정보를 저장한다. 부서 번호에 해당하는 기존 부서 정보가 존재할 경우 변경한다.
     *
     * @param deptVO 부서 정보
     *
     * @return 처리결과 부서 정보
     *
     */
    public CompletableFuture<DeptResponseVO> save(DeptRequestVO deptVO);


}
