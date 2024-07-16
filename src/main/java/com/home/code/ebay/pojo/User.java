package com.home.code.ebay.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    String userId;
    String accountName;
    String role;
    List<String> resources;
}
