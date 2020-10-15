package cn.fjcpc.manager.dto;

import java.io.Serializable;
import java.util.List;

/**
 * portal菜单节点
 */
public class ItemCatMenu implements Serializable {

    private String u;

    private String n;

    private List<Object> i;

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public List<Object> getI() {
        return i;
    }

    public void setI(List<Object> i) {
        this.i = i;
    }

}
