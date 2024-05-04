package com.shoe.Utils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileUtils {
    private static boolean isImage(MultipartFile file){
        String contentType = file.getContentType();
        return  contentType!= null && contentType.startsWith("image/");
    }
    public static String StoreFile(MultipartFile file) throws IOException {
        try{
            if(isImage(file)){
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());

                //Tạo ra tên file ngẫu nhiên
                String rFileName = UUID.randomUUID().toString() + "_" + fileName;
                Path path = Paths.get("src/main/resources/static/uploads");
                if(!Files.exists(path)){
                    Files.createDirectories(path);
                }
                //Lấy đường dẫn cụ thể dến file
                Path paths = Paths.get(path.toString(), rFileName);

                //Lưu file vào thư mục
                Files.copy(file.getInputStream(), paths, StandardCopyOption.REPLACE_EXISTING);
                return rFileName;
            }
        }
        catch (FileNotFoundException e){
            return null;
        }
        return null;

    }
}
