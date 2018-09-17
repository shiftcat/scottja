package kr.scott.controller;

import com.google.gson.Gson;
import kr.scott.vo.ResBody;
import kr.scott.vo.dept.DeptRequestVO;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
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
public class TestDeptController extends TestBase
{


    @Test
    public void testGetDepSearch() throws Exception
    {
        MvcResult result =
                mockMvc.perform(get("/api/dept/search"))
                    .andDo(print())
                    .andExpect(status().isOk()).andReturn();

        ResBody resBody = getResBody(result);
        if(resBody.getResponseVO() == null) {
            System.out.println("Response VO is null");
        }
        assertTrue(((List)resBody.getResult()).size() > 0);
    }


    @Test
    public void testValidateRequire() throws Exception
    {
        int deptno = 150;
        DeptRequestVO requestVO = new DeptRequestVO();
        requestVO.setDeptno(deptno);
//        requestVO.setDname("연구소");
//        requestVO.setLoc("서울 중구");

        Gson gson = new Gson();
        String json = gson.toJson(requestVO);

        MvcResult result =
                mockMvc.perform(post("/api/dept").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isBadRequest()).andReturn();

        ResBody resBody = getResBody(result);
        List<String> messages = resBody.getResponseVO().getMessages();

        assertTrue(messages.size() == 2);
    }



    @Test
    public void testValidateMaxLength() throws Exception
    {
        int deptno = 150;
        DeptRequestVO requestVO = new DeptRequestVO();
        requestVO.setDeptno(deptno);
        requestVO.setDname("123456789012345");
        requestVO.setLoc("12345678901234");

        Gson gson = new Gson();
        String json = gson.toJson(requestVO);

        MvcResult result =
                mockMvc.perform(post("/api/dept").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isBadRequest()).andReturn();

        ResBody resBody = getResBody(result);
        List<String> messages = resBody.getResponseVO().getMessages();

        assertTrue(messages.size() == 2);
    }


    @Test
    public void testValidateMinLength() throws Exception
    {
        int deptno = 150;
        DeptRequestVO requestVO = new DeptRequestVO();
        requestVO.setDeptno(deptno);
        requestVO.setDname("1");
        requestVO.setLoc("1");

        Gson gson = new Gson();
        String json = gson.toJson(requestVO);

        MvcResult result =
                mockMvc.perform(post("/api/dept").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isBadRequest()).andReturn();

        ResBody resBody = getResBody(result);
        List<String> messages = resBody.getResponseVO().getMessages();

        assertTrue(messages.size() == 2);
    }


    @Test
    public void testValidateMaxValue() throws Exception
    {
        int deptno = 1001;
        DeptRequestVO requestVO = new DeptRequestVO();
        requestVO.setDeptno(deptno);
        requestVO.setDname("연구소");
        requestVO.setLoc("서울 중구");

        Gson gson = new Gson();
        String json = gson.toJson(requestVO);

        MvcResult result =
                mockMvc.perform(post("/api/dept").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andDo(print())
                        .andExpect(status().isBadRequest()).andReturn();

        ResBody resBody = getResBody(result);
        List<String> messages = resBody.getResponseVO().getMessages();

        assertTrue(messages.size() == 1);
    }


    @Test
    public void testDeptSaveAndDelete() throws Exception
    {
        int deptno = 150;
        DeptRequestVO requestVO = new DeptRequestVO();
        requestVO.setDeptno(deptno);
        requestVO.setDname("연구소");
        requestVO.setLoc("서울 중구");

        Gson gson = new Gson();
        String json = gson.toJson(requestVO);


        mockMvc.perform(post("/api/dept").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/dept/"+deptno))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/dept/"+deptno))
                .andDo(print())
                .andExpect(status().isNotFound()).andReturn();
    }



    @Test
    public void testDeptSaveAndUpdate() throws Exception
    {
        int deptno = 160;
        DeptRequestVO requestVO = new DeptRequestVO();
        requestVO.setDeptno(deptno);
        requestVO.setDname("연구소");
        requestVO.setLoc("서울 중구");

        Gson gson = new Gson();
        String json = gson.toJson(requestVO);


        mockMvc.perform(post("/api/dept").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isCreated());


        requestVO = new DeptRequestVO();
        requestVO.setDeptno(deptno);
        requestVO.setDname("영업부");
        requestVO.setLoc("서울 강남");

        gson = new Gson();
        json = gson.toJson(requestVO);

        mockMvc.perform(post("/api/dept").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isCreated());

        MvcResult result =
                mockMvc.perform(get("/api/dept/"+deptno))
                        .andDo(print())
                        .andExpect(status().isOk()).andReturn();
        ResBody resBody = getResBody(result);
        assertTrue(resBody.getResult() instanceof Map);
        Map deptMap = (Map)resBody.getResult();
        assertThat(deptMap.get("dname"), is("영업부"));
        assertThat(deptMap.get("loc"), is("서울 강남"));

        mockMvc.perform(delete("/api/dept/"+deptno))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


}
