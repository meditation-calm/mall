package cn.fjcpc.manager.controller;

import cn.fjcpc.common.exception.MallException;
import cn.fjcpc.common.pojo.KindEditorResult;
import cn.fjcpc.common.utils.FtpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 图片上传统一接口
 */
@Controller
public class ImageController {

    /**
     * KindEditor控件上传文件-->vsftpd服务器
     * @param file
     * @param fileType
     * @return
     */
    @RequestMapping(value = "/pic/upload",method = RequestMethod.POST)
    @ResponseBody
    public KindEditorResult uploadFile(@RequestParam("uploadFile") MultipartFile file,
                                       @RequestParam("dir") String fileType){
        KindEditorResult result = new KindEditorResult();
        String filename = UUID.randomUUID().toString().replace("-","")+
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        try {
            if(!file.isEmpty()){
                if (!FtpUtil.uploadFile(fileType, filename, file.getInputStream())) {
                    result.setError(1);
                    result.setMessage("上传失败");
                    return result;
                }
                result.setError(0);
                result.setUrl("http://"+ FtpUtil.Ftp_Host+":80/"+fileType+"/"+filename);
            }
        } catch (IOException e) {
            throw new MallException("上传失败");
        }
        return result;
    }

}
