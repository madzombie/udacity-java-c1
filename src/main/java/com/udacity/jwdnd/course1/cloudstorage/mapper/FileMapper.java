package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES where fileName=#{fileName}")
    File getFileByName(String fileName);

    @Select ("SELECT * FROM FILES where fileId=#{fileId}")
    File getFileById(Integer fileId);

    @Select("SELECT * FROM FILES where userId=#{userId}")
    List<File> getAllFilesByUserId(Integer userId);

    @Insert("INSERT INTO FILES (fileName,contentType,fileSize,userId,fileData) VALUES (#{fileName},#{contentType},#{fileSize},#{userId},#{fileData})")
    @Options(useGeneratedKeys = true,keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES where fileId=#{fileId}")
    void deleteFile (Integer fileId);

    @Select("SELECT count(*) FROM FILES where userId=#{userId} and fileName=#{fileName}")
    int fileExistsCheck(Integer userId, String fileName);

}
