package com.xihuani.permutassep.service;

import org.apache.commons.codec.binary.Base64;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;

public class PermutasSEPRestClient {

    private static IPermutasSEPService restClient;
    private RestAdapter restAdapter;

    public PermutasSEPRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint("http://permuta-sep-dev.herokuapp.com/api/");

        if(true){
            builder.setRequestInterceptor(new RequestInterceptor() {
                public void intercept(RequestFacade request) {
                    String credentials = "permuta" + ":" + "kc.ll#15";
                    request.addHeader("Accept", "application/json");
                    String base64 = "cGVybXV0YTprYy5sbCMxNQ==";
                    request.addHeader("Authorization", "Basic " + base64);
                }
            });
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        restAdapter = builder.build();
    }

    public PermutasSEPRestClient(Converter converter) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint("http://permuta-sep-dev.herokuapp.com/api/");
        builder.setConverter(converter);

        if(true){
            builder.setRequestInterceptor(new RequestInterceptor() {
                public void intercept(RequestFacade request) {
                	String credentials = "permuta" + ":" + "kc.ll#15";
                    request.addHeader("Accept", "application/json");                    
                    request.addHeader("Authorization", "Basic " + 
                    		new String(Base64.encodeBase64(credentials.getBytes())));
                }
            });
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        
        restAdapter = builder.build(); 
    }

    public IPermutasSEPService get() {
        return restAdapter.create(IPermutasSEPService.class);
    }
}