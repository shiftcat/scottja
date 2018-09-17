package kr.scott.api.interceptor;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import kr.scott.api.utils.JsonValidation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 *
 * 사용자가 입력한 JSON 데이터에 대한 유효성을 검사하기 위한 Intercepter 이다.
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */
@Slf4j
public class ValidationIterceptor extends HandlerInterceptorAdapter
{

    private static final String URL_PREFIX = "/api";

    private static Map<String, String> JSON_SCHEMA_MAP = Maps.newHashMap();


    /**
     * JSON Schema 파일 등록
     */
    static {
        JSON_SCHEMA_MAP.put(String.format("%s/emp", URL_PREFIX), "jsonschema/emp_schema.json");
        JSON_SCHEMA_MAP.put(String.format("%s/dept", URL_PREFIX), "jsonschema/dept_schema.json");
    }


    /**
     * Request Body 를 읽어 JSON으로 변환한다.
     */
    private JsonNode requestToJsonNode(HttpServletRequest request) throws IOException
    {
//        String bodyData = CharStreams.toString(request.getReader());
        String bodyData = ((RereadableRequestWrapper)request).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory factory = objectMapper.getFactory();
        JsonParser parser = factory.createParser(bodyData);
        return objectMapper.readTree(parser);
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        boolean rstlBool = true;

        if( StringUtils.equals(request.getMethod(), HttpMethod.POST.toString()) ||
                StringUtils.equals(request.getMethod(), HttpMethod.PUT.toString()) ) {

            String jsonSchemaFileName = JSON_SCHEMA_MAP.get(request.getRequestURI());
            log.debug("JSON Schema file => {}", jsonSchemaFileName);

            if(StringUtils.isNotEmpty(jsonSchemaFileName)) {
                JsonNode requestJsonNode = requestToJsonNode(request);
                rstlBool = JsonValidation.validate(requestJsonNode, jsonSchemaFileName);
                if(!rstlBool) {
                    throw new Exception("Validate error");
                }
            }

        }

        return rstlBool;
    }


}
