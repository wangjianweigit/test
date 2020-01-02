package com.tk.oms.oss.server;

import com.tk.oms.oss.entity.OssFile;
import com.tk.sys.security.Base64;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
/**
 * Copyright (c), 2018,  TongKu
 * FileName : GeneralExportService
 * 上传至oss相关类
 * @author: zhengfy
 * @version: 1.00
 * @date: 2018/6/21
 */
@Service
public class GeneralExportService {
    private Log logger = LogFactory.getLog(this.getClass());

    @Value("${oss_service_url}")
    private String oss_service_url;

    @Value("${file_upload}")
    private String file_upload;

    /**
     * 上传文件
     *
     * @return
     */
    public String uploadFile(String user_name, File file, int type) throws Exception{
        byte[] b = FileUtils.getBytes(file);
        OssFile ossFile = new OssFile();
        ossFile.setType(type);
        ossFile.setUser_name(user_name);
        ossFile.setContent(Base64.encode(b));

        Packet packet = Transform.GetResultJzb(ossFile);//加密数据
        String content = Jackson.writeObject2Json(packet);
        String json = HttpClientUtil.post(oss_service_url + "" + file_upload, content);
        ProcessResult pr = (ProcessResult)Transform.GetPacketJzb(json,ProcessResult.class);

        //删除临时文件
        FileUtils.deleteFile(file.getPath());
        return String.valueOf(pr.getObj());
    }


}

    
    
