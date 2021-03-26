package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId}")
    List<Credential> getCredential (Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    Credential getCredentialById (Integer credentialId);

    @Insert("INSERT INTO CREDENTIALS (url,username,key,password,userId) values(#{url},#{username},#{key},#{password},#{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "credentialId")
    int addCredential (Credential credential);

    @Update("UPDATE CREDENTIALS set url=#{url},username=#{username},password=#{password} WHERE credentialId=#{credentialId}")
    public  void updateCredential(String url,String username,String password,Integer credentialId);

    @Delete("DELETE CREDENTIALS WHERE credentialId=#{credentialId}")
    public void deleteCredential(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId} order by credentialId desc limit 1")
    Credential getLastRecordByUserId(Integer userId);
}
