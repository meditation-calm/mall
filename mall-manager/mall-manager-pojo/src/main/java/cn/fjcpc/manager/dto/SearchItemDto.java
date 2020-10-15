package cn.fjcpc.manager.dto;

import cn.fjcpc.manager.pojo.TbItem;

public class SearchItemDto extends TbItem {

    private String[] images;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
