package cn.fjcpc.common.pojo;

import java.io.Serializable;

/**
 * KindEditor返回数据标准
 */
public class KindEditorResult implements Serializable {

    private int error;

    private String url;

    private String message;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
