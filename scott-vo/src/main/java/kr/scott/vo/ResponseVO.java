package kr.scott.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version 1.0
 * @see
 */
@lombok.Data
@ApiModel
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonPropertyOrder({"requestId", "code", "messages"})
public class ResponseVO
{

    @ApiModelProperty(required = true, value = "RequestID", example = "c75f8fa2-717a-4fcd-b273-c092d3ff9070")
    @JsonProperty("requestId")
    private String requestId;

    @ApiModelProperty(required = true, value = "HttpStatusCode", example = "200")
    @JsonProperty("code")
    private int code;


    @ApiModelProperty(required = true, value = "messages", example = "Success")
    private List<String> messages;


    @JsonProperty("messages")
    public List<String> getMessages()
    {
        if(messages == null) {
            return Lists.newArrayList();
        }
        return messages;
    }


}
