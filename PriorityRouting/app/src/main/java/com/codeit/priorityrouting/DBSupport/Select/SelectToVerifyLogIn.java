package com.codeit.priorityrouting.DBSupport;

import android.os.AsyncTask;

import com.codeit.priorityrouting.LoginActivity;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;



/*
*
 * Created by Hao on 3/8/2015.
 * */



public class SelectToVerifyLogIn extends AsyncTask<String,String,String> {

    private RestTemplate restTemplate= new RestTemplate();

    public LoginActivity loginActivity ;



    @Override
    protected String doInBackground(String ... params){
        String email=params[0];

        String query = "select email, password from localtest.priorityRouting where email = " + "'" + email + "'";
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String result = restTemplate.postForObject("http://10.0.2.2:9001/priorityRouting/select", query,String.class,"Android");

        return result;
    }

    @Override
    protected void onPostExecute(String result){

        loginActivity.selectObjectReturn(result);

    }


}
