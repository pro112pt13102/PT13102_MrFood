package com.example.peter.project1;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by daovip on 4/9/2018.
 */

public class MySingleton  {
    private static MySingleton mIntance;
    private RequestQueue requestQueue;
    private static Context mCTx;
    private MySingleton (Context context){
        mCTx=context;
        requestQueue=getRequestQueue();
    }
    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(mCTx.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized MySingleton getmIntance(Context context){
        if(mIntance==null){
            mIntance=new MySingleton(context);
        }
        return mIntance;
    }

    public<T> void addToRequestque(Request<T> request){
        requestQueue.add(request);
    }
}
