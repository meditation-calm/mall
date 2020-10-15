package cn.fjcpc.manager.dto.front;

import cn.fjcpc.manager.dto.PortalContentDto;
import cn.fjcpc.manager.pojo.TbContent;

public class PortalContentDtoUtils {
    /**
     * TbContent --> PortalContentDto
     * @param content
     * @return
     */
    public static PortalContentDto Tbcontent2PortalContentDto(TbContent content) {
        PortalContentDto contentDto = new PortalContentDto();
        contentDto.setId(content.getId());
        contentDto.setSrcB(content.getPic2());
        contentDto.setHeight(240);
        contentDto.setAlt("图片加载失败！");
        contentDto.setWidth(670);
        contentDto.setSrc(content.getPic());
        contentDto.setWidthB(550);
        contentDto.setHref(content.getUrl());
        contentDto.setHeightB(240);
        return contentDto;
    }

}
