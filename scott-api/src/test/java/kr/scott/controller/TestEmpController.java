package kr.scott.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import kr.scott.vo.ResBody;
import kr.scott.vo.emp.EmpRequestVO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
public class TestEmpController extends TestBase
{


    @Test
    public void testGetDepSearch() throws Exception
    {
        MvcResult result =
                mockMvc.perform(get("/api/emp/search"))
                        .andDo(print())
                        .andExpect(status().isOk()).andReturn();

        ResBody resBody = getResBody(result);
        if(resBody.getResponseVO() == null) {
            System.out.println("Response VO is null");
        }
        assertTrue(((List)resBody.getResult()).size() > 0);
    }


    @Test
    public void testGetDepSearchByEname() throws Exception
    {
        MvcResult result =
                mockMvc.perform(get("/api/emp/search?ename=KI"))
                        .andDo(print())
                        .andExpect(status().isOk()).andReturn();

        ResBody resBody = getResBody(result);
        if(resBody.getResponseVO() == null) {
            System.out.println("Response VO is null");
        }
        assertTrue(((List)resBody.getResult()).size() > 0);

        List<Map> empList = (List<Map>)resBody.getResult();
        for(Map e : empList) {
            String ename = (String)e.get("ename");
            assertTrue(StringUtils.indexOf(ename, "KI") > -1);
        }

    }


    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testValidateRequire() throws Exception
    {
        int empno = 9000;
        EmpRequestVO requestVO = new EmpRequestVO();
//        requestVO.setEmpno(empno);
//        requestVO.setEname("홍길동");
//        requestVO.setJob("개발자");
//        requestVO.setMgr(null);
//        requestVO.setSal(BigDecimal.valueOf(4500));
//        requestVO.setComm(null);
//        requestVO.setHiredate(dateFormat.parse("2010-02-09"));
//        requestVO.setDeptno(null);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(requestVO);

        MvcResult result =
                mockMvc.perform(post("/api/emp").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isBadRequest()).andReturn();

        ResBody resBody = getResBody(result);
        List<String> messages = resBody.getResponseVO().getMessages();

        assertTrue(messages.size() == 5);
    }


    @Test
    public void testValidateMaxLength() throws Exception
    {
        int empno = 9000;
        EmpRequestVO requestVO = new EmpRequestVO();
        requestVO.setEmpno(empno);
        requestVO.setEname("12345678901");
        requestVO.setJob("1234567890");
//        requestVO.setMgr(null);
        requestVO.setSal(BigDecimal.valueOf(4500));
//        requestVO.setComm(null);
        requestVO.setHiredate(dateFormat.parse("2010-02-09"));
//        requestVO.setDeptno(null);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(requestVO);

        MvcResult result =
                mockMvc.perform(post("/api/emp").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isBadRequest()).andReturn();

        ResBody resBody = getResBody(result);
        List<String> messages = resBody.getResponseVO().getMessages();

        assertTrue(messages.size() == 2);
    }


    @Test
    public void testValidateMinLength() throws Exception
    {
        int empno = 9000;
        EmpRequestVO requestVO = new EmpRequestVO();
        requestVO.setEmpno(empno);
        requestVO.setEname("1");
        requestVO.setJob("1");
//        requestVO.setMgr(null);
        requestVO.setSal(BigDecimal.valueOf(4500));
//        requestVO.setComm(null);
        requestVO.setHiredate(dateFormat.parse("2010-02-09"));
//        requestVO.setDeptno(null);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(requestVO);

        MvcResult result =
                mockMvc.perform(post("/api/emp").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isBadRequest()).andReturn();

        ResBody resBody = getResBody(result);
        List<String> messages = resBody.getResponseVO().getMessages();

        assertTrue(messages.size() == 2);
    }


    @Test
    public void testValidateMaxValue() throws Exception
    {
        int empno = 1000001;
        EmpRequestVO requestVO = new EmpRequestVO();
        requestVO.setEmpno(empno);
        requestVO.setEname("홍길동");
        requestVO.setJob("개발자");
//        requestVO.setMgr(null);
        requestVO.setSal(BigDecimal.valueOf(1000000));
        requestVO.setComm(BigDecimal.valueOf(1000000));
        requestVO.setHiredate(dateFormat.parse("2010-02-09"));
//        requestVO.setDeptno(null);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(requestVO);

        MvcResult result =
                mockMvc.perform(post("/api/emp").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isBadRequest()).andReturn();

        ResBody resBody = getResBody(result);
        List<String> messages = resBody.getResponseVO().getMessages();

        assertTrue(messages.size() == 3);
    }



    @Test
    public void testSaveAndDelete() throws Exception
    {
        int empno = 9800;
        EmpRequestVO requestVO = new EmpRequestVO();
        requestVO.setEmpno(empno);
        requestVO.setEname("홍길동");
        requestVO.setJob("개발자");
//        requestVO.setMgr(null);
        requestVO.setSal(BigDecimal.valueOf(4500));
        //requestVO.setComm(BigDecimal.valueOf(1000000));
        requestVO.setHiredate(dateFormat.parse("2010-02-09"));
//        requestVO.setDeptno(null);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(requestVO);

        mockMvc.perform(post("/api/emp").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn();

        mockMvc.perform(get("/api/emp/"+empno))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(delete("/api/emp/"+empno))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/emp/"+empno))
                .andDo(print())
                .andExpect(status().isNotFound()).andReturn();
    }




    @Test
    public void testSaveAndUpdate() throws Exception
    {
        int empno = 9810;
        EmpRequestVO requestVO = new EmpRequestVO();
        requestVO.setEmpno(empno);
        requestVO.setEname("홍길동");
        requestVO.setJob("개발자");
        requestVO.setMgr(null);
        requestVO.setSal(BigDecimal.valueOf(4500));
        requestVO.setComm(null);
        requestVO.setHiredate(dateFormat.parse("2010-02-09"));
        requestVO.setDeptno(null);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(requestVO);

        mockMvc.perform(post("/api/emp").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn();


        requestVO = new EmpRequestVO();
        requestVO.setEmpno(empno);
        requestVO.setEname("김제동");
        requestVO.setJob("방송인");
        requestVO.setMgr(7839);
        requestVO.setSal(BigDecimal.valueOf(4500));
        requestVO.setComm(BigDecimal.valueOf(6500));
        requestVO.setHiredate(dateFormat.parse("2001-03-02"));
        requestVO.setDeptno(10);

        mapper = new ObjectMapper();
        json = mapper.writeValueAsString(requestVO);


        mockMvc.perform(post("/api/emp").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn();


        MvcResult result =
            mockMvc.perform(get("/api/emp/"+empno))
                    .andDo(print())
                    .andExpect(status().isOk()).andReturn();

        ResBody resBody = getResBody(result);
        assertThat(resBody.getResult(), is(notNullValue()));

        Map empMap = (Map) resBody.getResult();
        assertThat(empMap.get("ename"), is("김제동"));
        assertThat(empMap.get("job"), is("방송인"));
        assertThat(empMap.get("hiredate"), is("2001-03-02"));
        assertThat(empMap.get("comm"), is(6500.0));
        assertThat(empMap.get("dept"), is(notNullValue()));

        Map deptMap = (Map) empMap.get("dept");
        assertThat(deptMap.get("deptno"), is(10));

        mockMvc.perform(delete("/api/emp/"+empno))
                .andDo(print())
                .andExpect(status().isNoContent());
    }





}
