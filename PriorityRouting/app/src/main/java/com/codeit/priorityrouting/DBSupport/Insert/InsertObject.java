package com.codeit.priorityrouting.DBSupport.Insert;

import java.util.List;
import java.util.Map;

/**
 * Created by Hao Weng on 3/17/2015.
 */
public class InsertObject {


    private String email;
    private String password;
    private String confirmPassword;

    private List<Map<String,String>> legalDocList;

    public List<Map<String,String>> getDocuments(){return legalDocList;}

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }



    public void setConfirmPassword(String confirmPass) {
        this.confirmPassword=confirmPass;
    }

    public void setEmail(String mail) {
        this.email=mail;
    }

    public void setPassword(String pass) {
        this.password=pass;
    }

}
