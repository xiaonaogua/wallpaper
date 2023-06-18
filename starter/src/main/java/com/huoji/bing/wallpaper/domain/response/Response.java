package com.huoji.bing.wallpaper.domain.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * response
 * @author No.6
 * @since 2023.5.7
 */
@Data
@Accessors(chain = true)
public class Response<T> implements Serializable {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 状态
     */
    private int code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 成功操作
     */
    public static <T> Response<T> success() {
        return success(null);
    }

    /**
     * 成功操作,携带数据
     */
    public static <T> Response<T> success(T data) {
        return success(ResponseCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功操作,携带消息
     */
    public static <T> Response<T> success(String message) {
        return success(message, null);
    }

    /**
     * 成功操作,携带消息和携带数据
     */
    public static <T> Response<T> success(String message, T data) {
        return success(ResponseCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 成功操作
     */
    public static <T> Response<T> success(int code, String message) {
        return success(code, message, null);
    }

    /**
     * 成功操作,携带自定义状态码,消息和数据
     */
    public static <T> Response<T> success(int code, String message, T data) {
        Response<T> result = new Response<T>();
        result.setCode(code);
        result.setMsg(message);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * 失败操作,默认数据
     */
    public static <T> Response<T> failure() {
        return failure(ResponseCode.SUCCESS.getMessage());
    }

    /**
     * 失败操作,携带自定义消息
     */
    public static <T> Response<T> failure(String message) {
        return failure(message, null);
    }

    /**
     * 失败操作,携带自定义消息和数据
     */
    public static <T> Response<T> failure(String message, T data) {
        return failure(ResponseCode.FAILURE.getCode(), message, data);
    }

    /**
     * 失败操作
     */
    public static <T> Response<T> failure(int code, String message) {
        return failure(ResponseCode.FAILURE.getCode(), message, null);
    }

    /**
     * 失败操作
     */
    public static <T> Response<T> failure(int code, String message, T data) {
        Response<T> result = new Response<T>();
        result.setCode(code);
        result.setMsg(message);
        result.setSuccess(false);
        result.setData(data);
        return result;
    }

    /**
     * Boolean 返回操作,携带默认返回值
     */
    public static <T> Response<T> decide(boolean b) {
        return decide(b, ResponseCode.SUCCESS.getMessage(), ResponseCode.FAILURE.getMessage());
    }

    /**
     * Boolean 返回操作,携带自定义消息
     */
    public static <T> Response<T> decide(boolean b, String success, String failure) {
        if (b) {
            return success(success);
        } else {
            return failure(failure);
        }
    }

}
