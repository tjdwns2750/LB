package com.team2.lb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int number;
    private String id;
    private String pw;
    private String address;
    private String email;
    private String originfile;
    private String savedfile;
    private boolean enabled;
    private String rolename;
}
