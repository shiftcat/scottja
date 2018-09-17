package kr.scott.core.service.impl;

import kr.scott.core.config.TxScott;
import kr.scott.core.entities.Dept;
import kr.scott.core.entities.Emp;
import kr.scott.core.errors.ContentNotFoundException;
import kr.scott.core.repo.DeptDAO;
import kr.scott.core.service.EmpService;
import kr.scott.core.repo.EmpDAO;
import kr.scott.vo.dept.DeptVO;
import kr.scott.vo.emp.EmpRequestVO;
import kr.scott.vo.emp.EmpResponseVO;
import kr.scott.vo.emp.EmpSearchVO;
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
 * @see EmpService
 */
@Service("EmpService")
public class EmpServiceImpl implements EmpService
{

    @Autowired
    private EmpDAO empDAO;


    @Autowired
    private DeptDAO deptDAO;



    private DeptVO toDeptVo(Dept dept)
    {
        DeptVO deptSvcVo = new DeptVO();
        deptSvcVo.setDeptno(dept.getDeptno());
        deptSvcVo.setDname(dept.getDname());
        deptSvcVo.setLoc(dept.getLoc());
        return deptSvcVo;
    }

    private EmpResponseVO toResponse(Emp emp)
    {
        EmpResponseVO empVO = new EmpResponseVO();
        empVO.setEmpno(emp.getEmpno());
        empVO.setEname(emp.getEname());
        empVO.setHiredate(emp.getHiredate());
        empVO.setJob(emp.getJob());
        empVO.setSal(emp.getSal());
        empVO.setComm(emp.getComm());
        empVO.setMgr(emp.getMgr());
        if(emp.getDept() != null) {
            empVO.setDept(toDeptVo(emp.getDept()));
        }
        return empVO;
    }



    @Override
    public List<EmpResponseVO> search(EmpSearchVO searchVO)
    {
        List<Emp> empList = empDAO.findByNameByDeptno(searchVO);

        return empList.stream().map(e -> toResponse(e)).collect(Collectors.toList());
    }


    @Override
    public EmpResponseVO get(int empno)
    {
        Emp emp = empDAO.findByEmpno(empno);
        if(emp == null) {
            throw new ContentNotFoundException("Emp not found");
        }
        return toResponse(emp);
    }


    @TxScott
    @Override
    public void remove(int empno)
    {
        Emp emp = empDAO.findByEmpno(empno);
        if(emp == null) {
            throw new ContentNotFoundException("Emp not found");
        }
        empDAO.remove(empno);
    }



    private void setEntity(Emp emp, EmpRequestVO empVO, Dept dept)
    {
        emp.setEmpno(empVO.getEmpno());
        emp.setEname(empVO.getEname());
        emp.setHiredate(empVO.getHiredate());
        emp.setJob(empVO.getJob());
        emp.setSal(empVO.getSal());
        emp.setComm(empVO.getComm());
        emp.setMgr(empVO.getMgr());
        emp.setDept(dept);
    }



    @TxScott
    @Override
    public EmpResponseVO save(EmpRequestVO empVO)
    {
        Dept dept = null;
        if(empVO.getDeptno() != null) {
            dept = deptDAO.findByDeptno(empVO.getDeptno());
            if(dept == null) {
                throw new ContentNotFoundException("Dept not found");
            }
        }

        Emp emp = empDAO.findByEmpno(empVO.getEmpno());

        if(emp == null) {
            emp = new Emp();
            setEntity(emp, empVO, dept);
        }
        else {
            setEntity(emp, empVO, dept);
        }
        empDAO.save(emp);
        return toResponse(emp);
    }


}
