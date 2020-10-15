package cn.fjcpc.common.pojo;

import java.io.Serializable;

/**
 * 前后端交互数据标准
 */
public class Result<T> implements Serializable {
    /**
     * 消息
     */
    private String message;
    /**
     * 状态码
     */
    private Integer status;
    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 返回数据
     */
    private T data;

    public Result() {}

    public Result(Integer status) {
        this.status = status;
    }

    public Result(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
