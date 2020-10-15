package cn.fjcpc.common.pojo;

import java.io.Serializable;
import java.util.List;

public class EasyUITree implements Serializable {

    private Long id;

    private String text;

    private String state;

    private boolean checked;

    private List<EasyUITree> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<EasyUITree> getChildren() {
        return children;
    }

    public void setChildren(List<EasyUITree> children) {
        this.children = children;
    }
}
