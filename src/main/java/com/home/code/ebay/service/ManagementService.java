package com.home.code.ebay.service;

import com.home.code.ebay.pojo.User;
import com.home.code.ebay.util.FileHandlerWithReadWriteLock;
import com.home.code.ebay.util.TokenUtil;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {
    @Value("${access.info.file}")
    String accessFile;

    public boolean addUser(String token,User user) throws IOException {
        String[] roleInfo = TokenUtil.parseToken(token);
        // only admin can add user
        if("admin".equals(roleInfo[1])){
            FileHandlerWithReadWriteLock.addUserToFile(accessFile,user);
            return true;
        }
        // no access to add user
        return false;
    }

    public String judgeAccess(String token,String resource) throws IOException{
        String[] roleInfo = TokenUtil.parseToken(token);
        User user=FileHandlerWithReadWriteLock.getUserFromFile(accessFile,roleInfo[0]);
        // not find user
        if(user==null){
            return "no such user";
        }
        var resources=user.getResources();
        if(resources.contains(resource)){
            return "user has access of "+resource;
        }
        //not find resource
        return "user has no access of "+resource;
    }
}
