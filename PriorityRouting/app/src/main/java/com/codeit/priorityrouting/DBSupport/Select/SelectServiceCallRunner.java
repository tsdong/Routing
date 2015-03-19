package com.codeit.priorityrouting.DBSupport.Select;


import android.os.AsyncTask;

import com.codeit.priorityrouting.RegistrationActivity;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by FZDDFL on 3/8/2015.
 */

public class SelectServiceCallRunner extends AsyncTask<String,String,String>{

    private RestTemplate restTemplate= new RestTemplate();

    public RegistrationActivity registrationActivity ;


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

        registrationActivity.selectObjectReturn(result);

    }


}
