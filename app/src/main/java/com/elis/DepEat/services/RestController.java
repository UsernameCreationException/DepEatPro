package com.elis.DepEat.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class RestController {

    private final static String BASE_URL = "http://138.68.86.70/";

    private RequestQueue queue;



    public RestController(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public void getRequest(String endPoint, Response.Listener<String> success, Response.ErrorListener error){

        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL.concat(endPoint),
                success,
                error
                );

        queue.add(request);
    }

    public void postRequest(String endPoint, Response.Listener<String> success, Response.ErrorListener error, final Map<String, String> parameters){

        StringRequest postRequest = new StringRequest(Request.Method.POST,
                BASE_URL.concat(endPoint),
                success,
                error
        )   {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params;
                    params = parameters;

                    return params;
                }
            };

        queue.add(postRequest);
    }


}
