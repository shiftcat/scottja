package kr.scott.core.service;


import kr.scott.config.CoreConfig;
import kr.scott.core.errors.ContentNotFoundException;
import kr.scott.core.service.EmpService;
import kr.scott.vo.emp.EmpRequestVO;
import kr.scott.vo.emp.EmpResponseVO;
import kr.scott.vo.emp.EmpSearchVO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;



/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreConfig.class)
@ActiveProfiles("test")
public class TestEmpService
{

    @Autowired
    private EmpService empService;


    @Test
    public void testSearchEmp()
    {
        EmpSearchVO searchVO = new EmpSearchVO();
        List<EmpResponseVO> empList = empService.search(searchVO);
        System.out.println(empList);
        assertTrue(empList.size() > 0);
    }


    @Test
    public void testSearchEmpName()
    {
        EmpSearchVO searchVO = new EmpSearchVO();
        searchVO.setEname("KI");
        List<EmpResponseVO> empList = empService.search(searchVO);
        System.out.println(empList);
        assertTrue(empList.size() > 0);
    }

    @Test
    public void testSearchEmpDeptno()
    {
        EmpSearchVO searchVO = new EmpSearchVO();
        searchVO.setDeptno(10);
        List<EmpResponseVO> empList = empService.search(searchVO);
        System.out.println(empList);
        assertTrue(empList.size() > 0);
    }


    @Test
    public void testSearchEmpSizeOffset()
    {
        EmpSearchVO searchVO = new EmpSearchVO();
        searchVO.setSize(10);
        searchVO.setOffset(3);
        List<EmpResponseVO> empList = empService.search(searchVO);
        System.out.println(empList);
        assertTrue(empList.size() > 0);
    }


    @Test
    public void testGetEmp()
    {
        EmpResponseVO empSvcVO = empService.get(7839);
        System.out.println(empSvcVO);
        assertThat(empSvcVO, is(notNullValue()));
    }


    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();


    @Test
    public void testGetEmpNotFound()
    {
        expectedExcetption.expect(ContentNotFoundException.class);
        empService.get(10000);
    }



    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyy-MM-dd");


    @Test
    public void testSaveAndDelete() throws ParseException
    {
        int empno = 8500;
        EmpRequestVO empVo = new EmpRequestVO();
        empVo.setEmpno(empno);
        empVo.setEname("홍길동");
        empVo.setJob("개발자");
        empVo.setMgr(7839);
        empVo.setHiredate(dateFormat.parse("2017-03-02"));
        empVo.setSal(BigDecimal.valueOf(4500));
        empService.save(empVo);

        EmpResponseVO empSvcVO = empService.get(empno);
        assertThat(empSvcVO, is(notNullValue()));

        empService.remove(empno);

        expectedExcetption.expect(ContentNotFoundException.class);
        empService.get(empno);
    }


    @Test
    public void testSaveAndUpdate() throws ParseException
    {
        int empno = 8600;
        EmpRequestVO empVo = new EmpRequestVO();
        empVo.setEmpno(empno);
        empVo.setEname("홍길동");
        empVo.setJob("개발자");
        empVo.setMgr(7839);
        empVo.setHiredate(dateFormat.parse("2017-03-02"));
        empVo.setSal(BigDecimal.valueOf(4500));
        empService.save(empVo);

        EmpResponseVO empSvcVO = empService.get(empno);
        assertThat(empSvcVO, is(notNullValue()));

        empVo = new EmpRequestVO();
        empVo.setEmpno(empno);
        empVo.setEname("김제동");
        empVo.setJob("방송인");
        empVo.setMgr(7839);
        empVo.setHiredate(dateFormat.parse("2001-03-02"));
        empVo.setSal(BigDecimal.valueOf(7000));
        empVo.setDeptno(10);
        empService.save(empVo);

        empSvcVO = empService.get(empno);
        assertThat(empSvcVO, is(notNullValue()));
        assertThat(empVo.getEname(), is("김제동"));
        assertThat(empVo.getJob(), is("방송인"));
        assertThat(empVo.getHiredate(), is(dateFormat.parse("2001-03-02")));
        assertThat(empVo.getSal(), is(BigDecimal.valueOf(7000)));
        assertThat(empVo.getDeptno(), is(10));

        empService.remove(empno);

        expectedExcetption.expect(ContentNotFoundException.class);
        empService.get(empno);
    }


    @Test
    public void testSaveAndUpdateDept() throws ParseException
    {
        int empno = 8700;
        EmpRequestVO empVo = new EmpRequestVO();
        empVo.setEmpno(empno);
        empVo.setEname("홍길동");
        empVo.setJob("개발자");
        empVo.setMgr(7839);
        empVo.setHiredate(dateFormat.parse("2017-03-02"));
        empVo.setSal(BigDecimal.valueOf(4500));
        empVo.setDeptno(10);
        empService.save(empVo);

        EmpResponseVO empSvcVO = empService.get(empno);
        assertThat(empSvcVO, is(notNullValue()));

        empVo = new EmpRequestVO();
        empVo.setEmpno(empno);
        empVo.setEname("김제동");
        empVo.setJob("방송인");
        empVo.setMgr(7839);
        empVo.setHiredate(dateFormat.parse("2001-03-02"));
        empVo.setSal(BigDecimal.valueOf(7000));
        // empVo.setDeptno(10);
        empService.save(empVo);

        empSvcVO = empService.get(empno);
        assertThat(empSvcVO, is(notNullValue()));
        assertThat(empVo.getEname(), is("김제동"));
        assertThat(empVo.getJob(), is("방송인"));
        assertThat(empVo.getHiredate(), is(dateFormat.parse("2001-03-02")));
        assertThat(empVo.getSal(), is(BigDecimal.valueOf(7000)));
        assertThat(empVo.getDeptno(), is(nullValue()));

        empService.remove(empno);

        expectedExcetption.expect(ContentNotFoundException.class);
        empService.get(empno);
    }

}
