package com.codeit.priorityrouting.DBSupport.Insert;

import android.os.AsyncTask;

import com.codeit.priorityrouting.RegistrationActivity;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by FZDDFL on 3/17/2015.
 */

/**
 * Created by Hao Weng on 3/8/2015.
 */

public class InsertServiceCallRunner extends AsyncTask<String,String,String> {

    private RestTemplate restTemplate= new RestTemplate();

    public RegistrationActivity registrationActivity ;


    @Override
    protected String doInBackground(String ... params){

        String insertJson=params[0];
        System.out.println("I am going to insert: " + insertJson);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String result = restTemplate.postForObject("http://pigppo.com:6390/priorityRouting/insert", insertJson, String.class, "Android");

        return result;
    }

    @Override
    protected void onPostExecute(String result){

        registrationActivity.insertObjectReturn(result);

    }


}
