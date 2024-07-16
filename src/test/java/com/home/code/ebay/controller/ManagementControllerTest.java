package com.home.code.ebay.controller;

import com.google.gson.Gson;
import com.home.code.ebay.EbayApplication;
import com.home.code.ebay.pojo.User;
import com.home.code.ebay.util.TokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EbayApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
//@TestPropertySource(locations = "classpath:application.yml")

public class ManagementControllerTest {

    @Autowired
    private MockMvc mockMvc ;


    @Test
    public void testAddUserWhenUserIsAdmin() throws Exception {
        User user=new User();
        List<String> resources = new ArrayList<>();
        resources.add("abc");
        resources.add("mnc");
        user.setUserId("test1");
        user.setRole("user");
        user.setResources(resources);
        Gson gson=new Gson();
        String token = TokenUtil.generateToken("11111","admin");

       mockMvc.perform(post("/admin/addUser").contentType(MediaType.APPLICATION_JSON).header("token",token)
              .content(gson.toJson(user))).andDo(print())
               .andExpect(content().contentType("application/json"))
               .andExpect(jsonPath("$.data").value("add user success"));
    }

    @Test
    public void testAddUserWhenUserIsNotAdmin() throws Exception {
        User user=new User();
        List<String> resources = new ArrayList<>();
        resources.add("abc1");
        resources.add("mncs");
        user.setUserId("test2");
        user.setRole("user");
        user.setResources(resources);
        Gson gson=new Gson();
        String token = TokenUtil.generateToken("11111","user");

        mockMvc.perform(post("/admin/addUser").contentType(MediaType.APPLICATION_JSON).header("token",token)
                .content(gson.toJson(user))).andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.data").value("no access to add user"));
    }


    @Test
    public void testJudgeAccessWhenUserHasAccess() throws Exception {

        String token = TokenUtil.generateToken("test2","user");

        mockMvc.perform(get("/user/{resource}","mnc2").header("token",token))
                .andDo(print())
                .andExpect(status().isOk()) .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.data").value("user has access of mnc2"));;
    }

    @Test
    public void testJudgeAccessWhenUserHasNoAccess() throws Exception {

        String token = TokenUtil.generateToken("test2","user");

        mockMvc.perform(get("/user/{resource}","mnc3").header("token",token))
                .andDo(print())
                .andExpect(status().isOk()) .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.data").value("user has no access of mnc3"));;
    }

    @Test
    public void testJudgeAccessWhenNoSuchUser() throws Exception {

        String token = TokenUtil.generateToken("test5","user");

        mockMvc.perform(get("/user/{resource}","mnc3").header("token",token))
                .andDo(print())
                .andExpect(status().isOk()) .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.data").value("no such user"));;
    }

}
