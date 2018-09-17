package kr.scott.core.service;

import kr.scott.config.CoreConfig;
import kr.scott.core.errors.ContentNotFoundException;
import kr.scott.core.errors.OccupiedException;
import kr.scott.core.service.DeptService;
import kr.scott.vo.dept.DeptRequestVO;
import kr.scott.vo.dept.DeptResponseVO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
public class TestDeptService
{


    @Autowired
    @Qualifier("DeptService")
    private DeptService deptService;

    @Test
    public void testDeptList()
    {
        List<DeptResponseVO> deptList = deptService.search();
        assertTrue(deptList.size() > 0);
    }


    @Test
    public void testGetDept()
    {
        DeptResponseVO deptSvcVo = deptService.get(10);
        assertThat(deptSvcVo, is(notNullValue()));
    }


    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();

    @Test
    public void testGetDepNotFound()
    {
        expectedExcetption.expect(ContentNotFoundException.class);
//        expectedExcetption.expectMessage("customized exception");
        deptService.get(1000);
    }


    @Test
    public void testDeptDelOccupied()
    {
        expectedExcetption.expect(OccupiedException.class);
        deptService.remove(10);
    }

    @Test
    public void testDeptSaveAndDelete()
    {
        int deptno = 50;
        DeptRequestVO newDept = new DeptRequestVO();
        newDept.setDeptno(deptno);
        newDept.setDname("연구소");
        newDept.setLoc("서울 중구");
        deptService.save(newDept);
        DeptResponseVO deptSvcVo = deptService.get(deptno);
        assertThat(deptSvcVo, is(notNullValue()));
        assertThat(deptSvcVo.getDname(), is("연구소"));

        deptService.remove(deptno);

        expectedExcetption.expect(ContentNotFoundException.class);
        deptService.get(deptno);
    }



    @Test
    public void testDeptSaveAndUpdate()
    {
        int deptno = 60;
        DeptRequestVO newDept = new DeptRequestVO();
        newDept.setDeptno(deptno);
        newDept.setDname("연구소");
        newDept.setLoc("서울 중구");
        deptService.save(newDept);
        DeptResponseVO deptSvcVo = deptService.get(deptno);
        assertThat(deptSvcVo, is(notNullValue()));
        assertThat(deptSvcVo.getDname(), is("연구소"));

        newDept = new DeptRequestVO();
        newDept.setDeptno(deptno);
        newDept.setDname("영업부");
        newDept.setLoc("서울 강동");
        deptService.save(newDept);
        deptSvcVo = deptService.get(deptno);
        assertThat(deptSvcVo, is(notNullValue()));
        assertThat(deptSvcVo.getDname(), is("영업부"));

        deptService.remove(deptno);
        expectedExcetption.expect(ContentNotFoundException.class);
        deptService.get(deptno);
    }
}
