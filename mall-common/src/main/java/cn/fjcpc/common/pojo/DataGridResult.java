package cn.fjcpc.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 *  easyui-datagrid数据标准
 */
public class DataGridResult implements Serializable {
    /* 总条数 */
    private Long total;
    /* 数据 */
    private List<?> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
