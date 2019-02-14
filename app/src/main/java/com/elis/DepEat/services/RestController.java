package com.elis.DepEat.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RestController {

    private final static String BASE_URL = "http://0544f73c.ngrok.io/api/";
    private final static String VERSION = "v1/";

    private RequestQueue queue;

    public RestController(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public void getRequest(String endPoint, Response.Listener<String> success, Response.ErrorListener error){

        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL.concat(VERSION).concat(endPoint),
                success,
                error
                );
        queue.add(request);
    }

}
