package com.devin.jsonparsing_retrofit.rest;

import com.devin.jsonparsing_retrofit.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("contacts")
    Call<Response> getAllContacts();
}
