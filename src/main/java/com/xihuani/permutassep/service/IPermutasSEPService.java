package com.xihuani.permutassep.service;

import com.xihuani.permutassep.model.Post;
import com.xihuani.permutassep.model.User;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface IPermutasSEPService {

    @POST("/users/")
    void newUser(@Body User user, Callback<User> callback);

    @GET("/users/{id}/posts")
    void myPosts(@Path("id") int id, Callback<List<Post>> callback);

    @POST("/posts/")
    Post newPost(@Body Post post, Callback<Post> Callback);

    @GET("/posts/")
    Callback<List<Post>> getPosts();
    
    @GET("/posts/")
    List<Post> getListPost();    

    @GET("/posts/")
    void searchPosts(@QueryMap Map<String, String> parameters, Callback<List<Post>> callback);

}