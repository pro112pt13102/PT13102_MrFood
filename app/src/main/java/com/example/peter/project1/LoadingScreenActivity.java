package com.example.peter.project1;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.peter.project1.Model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoadingScreenActivity extends AppCompatActivity {
    ImageView img_logo;
    TextView txt_app_name;
    ArrayList<SanPham> arrayListSanPhamSlideShow;
    ArrayList<SanPham> arrayListMonChinh;
    ArrayList<SanPham> arrayListMonAnVat;
    ArrayList<SanPham> arrayListThucUong;
    mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        Anhxa();
        //Animation
        loadData();
        Animation();
        Thread trangchu = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(LoadingScreenActivity.this,
                            TrangChuActivity.class);
                    i.putExtra("MonChinh",arrayListMonChinh);
                    i.putExtra("MonVat",arrayListMonAnVat);
                    i.putExtra("ThucUong",arrayListThucUong);
                    i.putExtra("SlideShow",arrayListSanPhamSlideShow);
                    startActivity(i);
                    finish();
                    Log.d("AAA","mon chinh"+arrayListMonChinh.size() +"\n mon vat"+arrayListMonAnVat.size()+"\n thuc uong"+ arrayListThucUong.size()+"\n slideshow" +arrayListSanPhamSlideShow.size());
                }
            }
        };
        trangchu.start();

    }

    public void Anhxa(){
        img_logo=findViewById(R.id.img_logo);
        txt_app_name=findViewById(R.id.txt_app_name);
        arrayListThucUong=new ArrayList<>();
        arrayListMonAnVat=new ArrayList<>();
        arrayListMonChinh =new ArrayList<>();
        arrayListSanPhamSlideShow=new ArrayList<>();
    }
    public void changeMarginTopWithanimation1(final View v, int differentMargin) {
        final ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) v.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, params.topMargin+differentMargin);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                params.topMargin = (Integer) valueAnimator.getAnimatedValue();
                v.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }
    public  void ChangeAnphaAnimation(final View view, float value){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,value);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }
    public void ScaleView(final View view, float value){
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", value);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", value);
        scaleDownX.setDuration(500);
        scaleDownY.setDuration(500);

        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);

        scaleDownX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                View p = (View) view.getParent();
                p.invalidate();
            }
        });
        scaleDown.start();

    }
    // LoadData
    public class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    setDataSlideshow(arrayListMonChinh.get(arrayListMonChinh.size()-1),arrayListMonChinh.get(arrayListMonChinh.size()-2));
                case 1:
                    setDataSlideshow(arrayListMonAnVat.get(arrayListMonAnVat.size()-1),arrayListMonAnVat.get(arrayListMonAnVat.size()-2));

                case 2:
                    setDataSlideshow(arrayListThucUong.get(arrayListThucUong.size()-1),arrayListThucUong.get(arrayListThucUong.size()-2));

            }
        }
    }
    public class ThreadLoadMonChinh extends Thread{
        @Override
        public void run() {
            super.run();
            // Load dataMonChinh
            String url="https://immense-scrubland-98497.herokuapp.com/app.php?kihieu=danh-sach-mon-an-theo-ma-loai&maloai=1&soluong=10";
            loadDataDoAn(url,arrayListMonChinh);
            try {
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                mHandler.sendEmptyMessage(0);
            }
        }
    }
    public class ThreadLoadMonVat extends Thread{
        @Override
        public void run() {
            super.run();
            // Load dataMonChinh
            String url="https://immense-scrubland-98497.herokuapp.com/app.php?kihieu=danh-sach-mon-an-theo-ma-loai&maloai=2&soluong=10";
            loadDataDoAn(url,arrayListMonAnVat);
            try {
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                mHandler.sendEmptyMessage(1);
            }

        }
    }
    public class ThreadLoadThucUong extends Thread{
        @Override
        public void run() {
            super.run();
            // Load dataMonChinh
            String url="https://immense-scrubland-98497.herokuapp.com/app.php?kihieu=danh-sach-thuc-uong-theo-ma-loai&maloai=3&soluong=10";
            loadDataDoUong(url,arrayListThucUong);
            try {
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                mHandler.sendEmptyMessage(2);
            }

        }
    }
    public void loadDataDoAn(String url, final ArrayList<SanPham> arrayList){
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(LoadingScreenActivity.this);

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject MonAn = response.getJSONObject(i);
                                // Get the current student (json object) data
                                int MaMa = MonAn.getInt("MaMA");
                                String TenMA = MonAn.getString("TenMA");
                                String GioiThieu = MonAn.getString("GioiThieu");
                                int Dongia= MonAn.getInt("Dongia");
                                String Anh = MonAn.getString("Anh");
                                int maDM = MonAn.getInt("MaDM");
                                SanPham monAn = new SanPham(TenMA,Dongia,Anh,1,MaMa,maDM,GioiThieu);

                                arrayList.add(monAn);

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.d("BBB",error+"");
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }
    public void loadDataDoUong(String url, final ArrayList<SanPham> arrayList){
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(LoadingScreenActivity.this);

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject MonAn = response.getJSONObject(i);
                                // Get the current student (json object) data
                                int MaMa = MonAn.getInt("MaDU");
                                String TenMA = MonAn.getString("TenDU");
                                String GioiThieu = MonAn.getString("GioiThieu");
                                int Dongia= MonAn.getInt("Dongia");
                                String Anh = MonAn.getString("Anh");
                                int maDM = MonAn.getInt("MaDM");
                                SanPham monAn = new SanPham(TenMA,Dongia,Anh,1,MaMa,maDM,GioiThieu);

                                arrayList.add(monAn);

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.d("BBB",error+"");
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }
    public void setDataSlideshow(SanPham sp1 ,SanPham sp2){
        arrayListSanPhamSlideShow.add(sp1);
        arrayListSanPhamSlideShow.add(sp2);
    }
    public void loadData(){
        mHandler=new mHandler();
        ThreadLoadMonChinh threadDataMonChinh = new ThreadLoadMonChinh();
        threadDataMonChinh.start();
        Thread threadDataMonVat = new ThreadLoadMonVat();
        threadDataMonVat.start();
        Thread threadDataThucuong = new ThreadLoadThucUong();
        threadDataThucuong.start();
    }
    public void Animation(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                // Scale size logo
                ScaleView(img_logo, (float) 2.2);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Change margin top of logo
//                    changeMarginWithanimation();
                changeMarginTopWithanimation1(img_logo,-40);
            }
        },800);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                changeMarginWithanimation();
                ChangeAnphaAnimation(txt_app_name,1);
//                changeMarginTopWithanimation();
                changeMarginTopWithanimation1(txt_app_name, 95);
            }
        },1000);
    }
}
