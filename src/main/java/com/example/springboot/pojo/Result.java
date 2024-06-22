package com.example.springboot.pojo;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;//状态码0-成功，1-失败
    private String message;//提示信息
    private T data;//响应数据

    public Result() {
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 快速返回响应结果，带数据
     *
     * @param data 响应数据
     * @param <E>  泛型参数，用于响应数据的解析
     * @return 带有数据的Result类实例
     */
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }

    /**
     * 快速返回响应结果，不带数据
     *
     * @return 不带数据的Result类实例
     */
    public static Result success() {
        return new Result(0, "操作成功", null);
    }

    /**
     * 快速返回响应错误
     *
     * @param message 错误提示信息
     * @return 带有错误提示信息的Result类实例
     */
    public static Result error(String message) {
        return new Result(1, message, null);
    }
}
