package kr.scott.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */
@Slf4j
public class JsonValidation
{

    /*
    {
      "level": "error",
      "schema": {
        "loadingURI": "#",
        "pointer": ""
      },
      "instance": {
        "pointer": ""
      },
      "domain": "validation",
      "keyword": "required",
      "message": "object has missing required properties ([\"dname\",\"loc\"])",
      "required": [
        "deptno",
        "dname",
        "loc"
      ],
      "missing": [
        "dname",
        "loc"
      ]
    }
     */
    public static boolean validate(JsonNode data, String jsonSchemaFileName) throws IOException, ProcessingException
    {
        ClassLoader classLoader = JsonValidation.class.getClassLoader();
        File file = new File(classLoader.getResource(jsonSchemaFileName).getFile());
        JsonNode fstabSchema = JsonLoader.fromFile(file);
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema schema = factory.getJsonSchema(fstabSchema);
        ProcessingReport pr = schema.validate(data, true);
        log.debug("JSON validate isSuccess => {}", pr.isSuccess());

        Iterator itr = pr.iterator();

        List<String> messageList = Lists.newArrayList();
        while(itr.hasNext()) {
            ProcessingMessage processingMessage = (ProcessingMessage) itr.next();
            LogLevel logLevel = processingMessage.getLogLevel();
            String message = processingMessage.getMessage();
            log.debug("LogLevel = {}, Message = {}", logLevel, message);
            JsonNode mjn = processingMessage.asJson();
            if(logLevel == LogLevel.ERROR) {
                log.warn("JSON validate error: {}", message);
                messageList.add(message);
            }
        }

        if(messageList.size() > 0) {
            throw new JsonValidateException("JSON Validate error.", messageList);
        }

        return pr.isSuccess();
    }

}
