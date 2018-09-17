package kr.scott.core.service.impl;

import kr.scott.core.config.TxScott;
import kr.scott.core.entities.Dept;
import kr.scott.core.errors.ContentNotFoundException;
import kr.scott.core.errors.OccupiedException;
import kr.scott.core.service.DeptService;
import kr.scott.core.repo.DeptDAO;
import kr.scott.core.repo.EmpDAO;
import kr.scott.vo.dept.DeptRequestVO;
import kr.scott.vo.dept.DeptResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see DeptService
 */
@Service("DeptService")
public class DeptServiceImpl implements DeptService
{


    @Autowired
    private DeptDAO deptDAO;


    @Autowired
    private EmpDAO empDAO;



    private DeptResponseVO toVo(Dept dept)
    {
        DeptResponseVO deptVo = new DeptResponseVO();
        deptVo.setDeptno(dept.getDeptno());
        deptVo.setDname(dept.getDname());
        deptVo.setLoc(dept.getLoc());
        return deptVo;
    }


    @Override
    public List<DeptResponseVO> search()
    {
        List<Dept> depts = deptDAO.findAll();
        List deptList = depts.stream().map(d -> toVo(d)).collect(Collectors.toList());
        return deptList;
    }



    @Override
    public DeptResponseVO get(int deptno)
    {
        Dept dept = deptDAO.findByDeptno(deptno);
        if (dept != null) {
            DeptResponseVO deptVo = toVo(dept);
            return deptVo;
        }
        else {
            throw new ContentNotFoundException("Dept not found");
        }
    }


    @TxScott
    @Override
    public void remove(int deptno)
    {
        Dept dept = deptDAO.findByDeptno(deptno);
        if (dept == null) {
            throw new ContentNotFoundException("Dept not found");
        }
        long cnt = empDAO.countByDeptno(deptno);
        if(cnt > 0) {
            throw new OccupiedException("Dept are occupied");
        }
        deptDAO.remove(deptno);
    }


    @TxScott
    @Override
    public DeptResponseVO save(DeptRequestVO deptVO)
    {
        Integer deptno = deptVO.getDeptno();
        Dept dept = deptDAO.findByDeptno(deptno);
        if (dept == null) {
            dept = new Dept();
            dept.setDeptno(deptVO.getDeptno());
            dept.setDname(deptVO.getDname());
            dept.setLoc(deptVO.getLoc());
        }
        else {
            dept.setDname(deptVO.getDname());
            dept.setLoc(deptVO.getLoc());
        }
        deptDAO.save(dept);
        return toVo(dept);
    }

}
