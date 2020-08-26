package com.luoqiu.base.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 自定义响应结构
 */
@Data
public class LuoqiuResult {

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public LuoqiuResult() {
    }
    public LuoqiuResult(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }
    public LuoqiuResult(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public LuoqiuResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static LuoqiuResult ok() {
        return new LuoqiuResult(null);
    }
    public static LuoqiuResult ok(String message) {
        return new LuoqiuResult(message, null);
    }
    public static LuoqiuResult ok(Object data) {
        return new LuoqiuResult(data);
    }
    public static LuoqiuResult ok(String message, Object data) {
        return new LuoqiuResult(message, data);
    }

    public static LuoqiuResult build(Integer code, String message) {
        return new LuoqiuResult(code, message, null);
    }

    public static LuoqiuResult build(Integer code, String message, Object data) {
        return new LuoqiuResult(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }


    /**
     * JSON字符串转成 MengxueguResult 对象
     * @param json
     * @return
     */
    public static LuoqiuResult format(String json) {
        try {
            return JSON.parseObject(json, LuoqiuResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
