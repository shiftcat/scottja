package kr.scott.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.scott.api.filter.RequestWrapFilter;
import kr.scott.config.ApiConfig;
import kr.scott.config.CoreConfig;
import kr.scott.vo.ResBody;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


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
@WebAppConfiguration
@ContextConfiguration(classes = {CoreConfig.class, ApiConfig.class})
@ActiveProfiles("test")
@Slf4j
public abstract class TestBase
{

    @Autowired
    private RequestWrapFilter filter;

    @Autowired
    private WebApplicationContext webAppContext;

    protected MockMvc mockMvc;


    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).addFilters(filter).build();
    }


    protected ResBody getResBody(MvcResult mvcResult) throws Exception
    {
        String resultBody = mvcResult.getResponse().getContentAsString();
        /*
         @JsonProperty annotation에 지정된 property 이름으로 해석하지 않음.
        Gson gson = new Gson();
        ResBody resBody = gson.fromJson(resultBody, ResBody.class);
        */
        ObjectMapper mapper = new ObjectMapper();
        ResBody resBody = mapper.readValue(resultBody, ResBody.class);
        log.debug("TEST ResBody => {}", resBody);
        if(resBody.getResult() != null) {
            log.debug("TEST Result type => {}", resBody.getResult().getClass());
        }
        return resBody;
    }

}
