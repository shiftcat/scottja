package kr.scott.api.controller;

import io.swagger.annotations.*;
import kr.scott.api.utils.Response;
import kr.scott.core.service.DeptService;
import kr.scott.vo.ResBody;
import kr.scott.vo.dept.DeptRequestVO;
import kr.scott.vo.dept.DeptResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//import kr.scott.core.service.department.vo.DeptSvcReqVO;
//import kr.scott.core.service.department.vo.DeptSvcResVO;


/**
 * 이 클래스는 부서를 관리하는 API를 제공하기 위한 클래스이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */
@SwaggerDefinition(
        securityDefinition = @SecurityDefinition(
                basicAuthDefinions = {
                        @BasicAuthDefinition(key = "authorization")
                }
        )
)
@Api(value="/api/dept", description="부서관리 API")
@RequestMapping("/api/dept")
@RestController("DeptController")
public class DeptController
{



    @Autowired
    private DeptService deptService;


    /**
     *
     * 이 API는 부서 정보 조회 하는 API이다.
     *
     * @return 조회결과
     *
     */
    @ApiOperation(value = "부서 조회",
            authorizations = {
//                @Authorization(value = "oauth2", scopes = {}),
//                @Authorization(value = "oauth2-cc", scopes = {}),
//                @Authorization(value = "oauth2-ac", scopes = {}),
//                @Authorization(value = "oauth2-rop", scopes = {}),
                @Authorization(value = "Bearer")
            }
//            extensions = {
//                @Extension(name = "roles",
//                        properties = {
//                            @ExtensionProperty(name = "USER1", value = "advisors are allowed getting every virtualaccount"),
//                            @ExtensionProperty(name = "USER2", value = "customer only allowed getting own locations")
//                        }
//                )
//            }
    )

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<ResBody<List<DeptResponseVO>>> seaerch()
    {
        List<DeptResponseVO> res = deptService.search();
        return Response.makeResponse(res);
    }




    /**
     *
     * 이 API는 부서 번호에 해당하는 부서를 조회한다.
     * @param deptno 부서번호
     * @return 조회결과
     *
     */
    @ApiOperation(value = "부서 번호에 해당하는 부서 조회", authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptno", value = "부서번호", required = true, dataType = "int", paramType = "path", defaultValue = ""),
    })
    @ApiResponses( value = {
            @ApiResponse(code = 404, message = "Dept not found", response = ResBody.class)
    })
    @RequestMapping(value = "/{deptno}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<ResBody<DeptResponseVO>> getDept(@PathVariable Integer deptno)
    {
        DeptResponseVO res = deptService.get(deptno);
        return Response.makeResponse(res);
    }




    /**
     *
     * 이 API는 부서 번호에 해당하는 부서를 삭제한다.
     *
     *
     * @param deptno 부서번호
     * @return 처리결과
     *
     */
    @ApiOperation(value = "부서 번호에 해당하는 부서 정보 삭제", authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptno", value = "부서번호", required = true, dataType = "int", paramType = "path", defaultValue = ""),
    })
    @ApiResponses( value = {
            @ApiResponse(code = 404, message = "Dept not found", response = ResBody.class)
    })
    @RequestMapping(value = "/{deptno}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer deptno)
    {
        deptService.remove(deptno);
    }




    /**
     *
     * 이 API는 신규 부서 정보를 추가 하거나 부서 번호에 해당하는 부서정보를 변경한다.
     *
     *
     * @param deptVO 부서정보
     * @return 처리결과
     *
     */
    @ApiOperation(value = "신규 부서 저장 또는 변경", authorizations = {@Authorization(value = "Bearer")})
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED) // swagger
    public ResponseEntity<ResBody<DeptResponseVO>> save(
                    @ApiParam(name = "dept", value = "부서정보", required = true)
                    @RequestBody
                    @Valid
                            DeptRequestVO deptVO)
    {
        DeptResponseVO res = deptService.save(deptVO);
        return Response.makeResponse(res, HttpStatus.CREATED);
    }

}
