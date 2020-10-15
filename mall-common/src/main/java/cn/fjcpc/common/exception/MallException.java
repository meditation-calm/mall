package cn.fjcpc.common.exception;

/**
 *
 */
public class MallException extends RuntimeException {

    private String msg;

    public MallException(String msg){
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
