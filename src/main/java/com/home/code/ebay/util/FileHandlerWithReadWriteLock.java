package com.home.code.ebay.util;

import com.google.gson.Gson;
import com.home.code.ebay.pojo.User;

import java.io.*;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileHandlerWithReadWriteLock {

    private final static ReadWriteLock lock = new ReentrantReadWriteLock();

    private static Gson gson = new Gson();

    private final static String PATH = "src/main/resources/";

    public static Boolean addUserToFile(String file, User userInfo) throws IOException {

        lock.writeLock().lock();

        try (FileWriter fileWriter = new FileWriter(PATH+file,true)) {
            fileWriter.write(gson.toJson(userInfo)+System.lineSeparator());
        }  finally {
            lock.writeLock().unlock();
        }
        return true;
    }

    public static User getUserFromFile(String file,String userId) throws IOException {
        lock.readLock().lock();
        User user ;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH+file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                user = gson.fromJson(line,User.class);
                if(Objects.equals(userId,user.getUserId())){
                    return user;
                }

            }
        }  finally {
            lock.readLock().unlock();
        }
        return null;
    }
}