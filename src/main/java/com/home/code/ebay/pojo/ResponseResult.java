package com.home.code.ebay.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {

    private String requestId;

    private Integer code;

    private String msg;

    private T data;

    public ResponseResult() {

    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResponseResult<T> success() {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setResultCode(ResponseResultCode.SUCCESS);
        return responseResult;
    }

    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setResultCode(ResponseResultCode.SUCCESS);
        responseResult.setData(data);
        return responseResult;
    }

    public static <T> ResponseResult<T> failure(ResponseResultCode responseResultCode) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setResultCode(responseResultCode);
        return responseResult;
    }

    public static <T> ResponseResult<T> failure(ResponseResultCode responseResultCode, String msg) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setMsg(msg);
        responseResult.setCode(responseResultCode.getCode());
        return responseResult;
    }

    public static <T> ResponseResult<T> failure(ResponseResultCode responseResultCode, String msg, T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setMsg(msg);
        responseResult.setCode(responseResultCode.getCode());
        responseResult.setData(data);
        return responseResult;
    }

    public void setResultCode(ResponseResultCode code) {
        this.code = code.getCode();
        this.msg = code.getMessage();
    }
}
