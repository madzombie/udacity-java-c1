package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Service
public class FileService {

    private UserMapper userMapper;
    private FileMapper fileMapper;


    public FileService(UserMapper userMapper,FileMapper fileMapper) {
        this.userMapper=userMapper;
        this.fileMapper=fileMapper;
    }

    public boolean isFileAvailable (Authentication authentication,String fileName) {
        Integer userId = userMapper.getUser(authentication.getName()).getUserId();
        System.out.println("FileService");
        System.out.println("FileService-isFileAvailable:userId:"+userId+":AuthName:"+authentication.getName()+":fileName:"+fileName);
        System.out.println(":status:"+fileMapper.fileExistsCheck(userId,fileName)+":returnValue:"+fileMapper.fileExistsCheck(userId,fileName)!=null);
        return fileMapper.fileExistsCheck(userId,fileName)>0;
    }

    public List<File> getAllFilesByUserId(Authentication authentication) {
        if (authentication==null) {
            return null;
        } else if (userMapper.getUser(authentication.getName())==null) {
            return null;
        }
        return fileMapper.getAllFilesByUserId(userMapper.getUser(authentication.getName()).getUserId());
    }

    public File getFileById (Integer fileId) {
        return fileMapper.getFileById(fileId);
    }

    public File getFileByName(String fileName) {
        return fileMapper.getFileByName(fileName);
    }

    public void deleteFile (Integer fileId) {
        fileMapper.deleteFile(fileId);
    }

    public int createFile (MultipartFile file, Authentication authentication) throws IOException {
        InputStream fis = file.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = fis.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] fileData = buffer.toByteArray();

        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        String fileSize = String.valueOf(file.getSize());
        Integer userId = userMapper.getUser(authentication.getName()).getUserId();
        return fileMapper.insert(new File(null,fileName,contentType,fileSize,userId,fileData));
    }


}
