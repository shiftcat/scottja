package kr.scott.api.controller;


import io.swagger.annotations.*;
import kr.scott.api.utils.Response;
import kr.scott.core.service.EmpService;
import kr.scott.vo.ResBody;
import kr.scott.vo.emp.EmpRequestVO;
import kr.scott.vo.emp.EmpResponseVO;
import kr.scott.vo.emp.EmpSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 *  이 클래스는 사워정보를 관리하기 위한 클래스이다.
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 *
 */
@Api(value="/api/emp", description="사원괸리 API")
@RequestMapping("/api/emp")
@RestController("EmpController")
public class EmpController
{

    @Autowired
    private EmpService empService;



    /**
     *
     * 이 API는 사원정보를 조회하는 API이다.
     *
     *
     * @param searchVO 조회조건
     * @return 조회결과
     *
     */
    @ApiOperation(value = "사원 조회", authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ename", value = "사원이름", required = false, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "deptno", value = "사원번호", required = false, dataType = "int", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "size", value = "조회크기", required = false, dataType = "int", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "offset", value = "조회시작위치", required = false, dataType = "int", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<ResBody<List<EmpResponseVO>>> search(@Valid @ModelAttribute EmpSearchVO searchVO)
    {
        List<EmpResponseVO> res = empService.search(searchVO);
        return Response.makeResponse(res);
    }




    /**
     *
     * 이 API는 사원번호에 해당하는 사원정보를 조회 한다.
     *
     *
     * @param empno 사원번호
     * @return 사원정보
     *
     */
    @ApiOperation(value = "사원 번호에 해당하는 사원 정보 조회", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses( value = {
            @ApiResponse(code = 404, message = "Emp not found", response = ResBody.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empno", value = "사원번호", required = true, dataType = "int", paramType = "path", defaultValue = ""),
    })
    @RequestMapping(value = "/{empno}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<ResBody<EmpResponseVO>> getEmp(@PathVariable Integer empno)
    {
        EmpResponseVO res = empService.get(empno);
        return Response.makeResponse(res);
    }





    /**
     *
     * 이 API는 사원번호에 해당하는 사원 정보를 삭제 한다.
     *
     *
     * @param empno 사원번호
     * @return 처리결과
     *
     */
    @ApiOperation(value = "사원 번호에 해당하는 사원 삭제", authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses( value = {
            @ApiResponse(code = 404, message = "Emp not found", response = ResBody.class)
    })
    @RequestMapping(value = "/{empno}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer empno)
    {
        empService.remove(empno);
    }




    /**
     *
     * 이 API는 신규 사원 정보를 저장하거나 사원 번호에 해당하는 사원 정보를 변경한다.
     *
     *
     * @param requestVO 사원정보
     * @return 처리결과
     *
     *
     */
    @ApiOperation(value = "신규 사원 정보 저장 또는 변경", authorizations = {@Authorization(value = "Bearer")})
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED) // swagger
    public ResponseEntity<ResBody<EmpResponseVO>> save(
            @ApiParam(name = "emp", value = "사원정보", required = true)
            @RequestBody
            @Valid
                    EmpRequestVO requestVO)
    {

        EmpResponseVO res =  empService.save(requestVO);

        return Response.makeResponse(res, HttpStatus.CREATED);
    }


}
