package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserMapper userMapper;
    private  CredentialEncryption credentialEncryption;

    public CredentialService(CredentialMapper credentialMapper,UserMapper userMapper,CredentialEncryption credentialEncryption) {
        this.credentialMapper=credentialMapper;
        this.userMapper=userMapper;
        this.credentialEncryption=credentialEncryption;
    }

    // list not by user +
    // create a note    +
    // delete a note
    // edit a note

    public Integer getUserId (Authentication authentication) {
        return userMapper.getUser(authentication.getName()).getUserId();
    }

    public List<Credential> getAllCredential(Authentication authentication) {
        Integer userId=getUserId(authentication);

        return credentialMapper.getCredential(userId);
    }

    public Credential getCredentialById (Integer credentialId) {
        return  credentialMapper.getCredentialById(credentialId);
    }
    public void deleteCredential (Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    public int createCredential(Authentication authentication,String url,String username,String key,String password) {
        Integer userId=getUserId(authentication);
        String encodedKey=credentialEncryption.getEncodedKey();
        String encodedPassword=credentialEncryption.encrypt(password);
        System.out.println("createCredential:key:"+encodedKey);
        return credentialMapper.addCredential(new Credential(null,url,username,encodedKey,encodedPassword,userId));
    }

    public void editCredential (String url,String username,String key,String password,Integer credentialId) {
        String encodedPassword=credentialEncryption.encrypt(password);
        credentialMapper.updateCredential(url,username,encodedPassword,credentialId);
    }
    public Credential getLastRecordByUserId (String username) {
        Integer userId = userMapper.getUser(username).getUserId();
        return credentialMapper.getLastRecordByUserId(userId);
    }


}
