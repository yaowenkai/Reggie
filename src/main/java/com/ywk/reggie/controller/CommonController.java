package com.ywk.reggie.controller;

import com.ywk.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@CrossOrigin//解决跨域问题
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;
    //文件上传
    @PostMapping("/upload")
    //MultipartFile file  中的file 要和前端
    // Content-Disposition: form-data; name="file"; filename="a309e1c7b8f04deda8cc5e98df3a7ada.png"
    //中的 name属性保持一致,且file是一个临时文件,请求完成后就删除了
    public R<String> upload(MultipartFile file) {
        //原始文件名,包含后缀,需要截取后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID重新给文件命名,防止重名覆盖
        String s = UUID.randomUUID().toString();

        //拼接名字
        String fileName = s+suffix;

        //判断目录是否存在
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        try {
            //将临时文件进行转存
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("上传");
        return R.success(fileName);
    }


    //文件下载
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //输入流,读取文件
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));
            //    输出流,将文件写回浏览器,浏览器可以直接展示
            ServletOutputStream outputStream = response.getOutputStream();
            //设置输出文件类型
            response.setContentType("image/jpeg");
            //传输数据,展示回页面(前端已经完成对应的流程,只需要把数据传递给浏览器即可)
            int len=0;
            byte[] bytes = new byte[1024];
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

        //    关闭资源
            outputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
