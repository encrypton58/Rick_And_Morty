package com.em_and_ei.company.rickandmorty.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static VolleySingleton singleton;
    private RequestQueue requestQueue;
    private static Context cxt;

    private VolleySingleton(Context context){
        cxt = context;
        requestQueue = getRequestQueque();
    }

    public static synchronized VolleySingleton getInstance(Context context){
        if (singleton == null){
            singleton = new VolleySingleton(context);
        }
        return singleton;
    }

    public RequestQueue getRequestQueque(){
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(cxt.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addRequestQueque(Request<T> req){
        getRequestQueque().add(req);
    }
}
