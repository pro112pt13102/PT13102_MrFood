package com.example.peter.project1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.peter.project1.Adapter.AdapterSlideShow;
import com.example.peter.project1.Adapter.adapter_rc_horizontalview;
import com.example.peter.project1.Model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class TrangChuActivity extends AppCompatActivity {
    ViewPager viewPager_slideshow;
    ImageButton imgb;
    DrawerLayout dw;
    NavigationView navigationView;
    TextView tv_xemtatca_mon_chinh,tv_xemtatca_mon_vat,tv_xemtatca_thuc_uong;
    RecyclerView rc_MonChinh,rc_Mon_Vat,rc_Thuc_uong;
    CircleIndicator circleIndicator;
    ArrayList<SanPham> arrayListSanPhamSlideShow;
    ArrayList<SanPham> arrayListMonChinh;
    ArrayList<SanPham> arrayListMonAnVat;
    ArrayList<SanPham> arrayListThucUong;
    ArrayList<SanPham> arrayListComVanPhong;
    ArrayList<SanPham> arrayListCafe;
    ArrayList<SanPham> arrayListTraSua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);
        AnhXa();
       //get DAta from loading Screen
        getData();
        setUpAdapter();
        imgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dw.openDrawer(GravityCompat.START);
            }
        });
        // Show all Món chính
        tv_xemtatca_mon_chinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangChuActivity.this,SanPhamActivity.class);
                i.putExtra("LOAI","Món chính");
                i.putExtra("DsMonChinh",arrayListMonChinh);
                startActivity(i);
            }
        });
        // Show all Món vặt
        tv_xemtatca_mon_vat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangChuActivity.this,SanPhamActivity.class);
                i.putExtra("LOAI","Món Ăn Vặt");
                i.putExtra("DsMonVat",arrayListMonAnVat);
                startActivity(i);
            }
        });
        // Show all Thức uống
        tv_xemtatca_thuc_uong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangChuActivity.this,SanPhamActivity.class);
                i.putExtra("LOAI","Thức uống");
                i.putExtra("DsThucUong",arrayListThucUong);
                startActivity(i);
            }
        });
        //Onclick Menu navigation
        onClicMenu();
    }
    public void AnhXa(){
        navigationView=findViewById(R.id.nav_view);
        arrayListThucUong=new ArrayList<>();
        arrayListMonAnVat=new ArrayList<>();
        arrayListMonChinh =new ArrayList<>();
        arrayListSanPhamSlideShow=new ArrayList<>();
        arrayListCafe=new ArrayList<>();
        arrayListComVanPhong=new ArrayList<>();
        arrayListTraSua =new ArrayList<>();
        rc_MonChinh=findViewById(R.id.rc_mon_chinh);
        rc_Mon_Vat=findViewById(R.id.rc_mon_van_vat);
        rc_Thuc_uong=findViewById(R.id.rc_thuc_uong);
        imgb = findViewById(R.id.img_btn_menu);
        dw = findViewById(R.id.drawer_layout);
        tv_xemtatca_mon_chinh = findViewById(R.id.tv_xemtatca_mon_chinh);
        tv_xemtatca_mon_vat = findViewById(R.id.tv_xemtatca_mon_vat);
        tv_xemtatca_thuc_uong = findViewById(R.id.tv_xemtatca_thuc_uong);
        viewPager_slideshow=findViewById(R.id.vp_slideshow);
        circleIndicator=findViewById(R.id.indicator_default);
    }
    public void getData(){
        arrayListMonChinh= (ArrayList<SanPham>) getIntent().getSerializableExtra("MonChinh");
        arrayListMonAnVat= (ArrayList<SanPham>) getIntent().getSerializableExtra("MonVat");
        arrayListThucUong= (ArrayList<SanPham>) getIntent().getSerializableExtra("ThucUong");
        arrayListSanPhamSlideShow= (ArrayList<SanPham>) getIntent().getSerializableExtra("SlideShow");
        arrayListComVanPhong= (ArrayList<SanPham>) getIntent().getSerializableExtra("ComVanPhong");
        arrayListCafe= (ArrayList<SanPham>) getIntent().getSerializableExtra("Cafe");
        arrayListTraSua= (ArrayList<SanPham>) getIntent().getSerializableExtra("TraSua");
    }
    public void setUpCircleIndicator(){
            FragmentPagerAdapter adapter= new AdapterSlideShow(getSupportFragmentManager(),arrayListSanPhamSlideShow);
            viewPager_slideshow.setAdapter(adapter);
            circleIndicator.setViewPager(viewPager_slideshow);

    }
    public void onClicMenu(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.monchinh:
                        Intent i1 = new Intent(TrangChuActivity.this,SanPhamActivity.class);
                        i1.putExtra("LOAI","Món chính");
                        i1.putExtra("DsMonChinh",arrayListMonChinh);
                        startActivity(i1);
                        break;
                    case R.id.monanvat:
                        Intent i2 = new Intent(TrangChuActivity.this,SanPhamActivity.class);
                        i2.putExtra("LOAI","Món Ăn Vặt");
                        i2.putExtra("DsMonVat",arrayListMonAnVat);
                        startActivity(i2);
                        break;
                    case R.id.trasua:
                        Intent i3 = new Intent(TrangChuActivity.this,SanPhamActivity.class);
                        i3.putExtra("LOAI","Trà Sữa");
                        i3.putExtra("TraSua",arrayListTraSua);
                        startActivity(i3);
                        break;
                    case R.id.cafe:
                        Intent i4 = new Intent(TrangChuActivity.this,SanPhamActivity.class);
                        i4.putExtra("LOAI","Cafe");
                        i4.putExtra("Cafe",arrayListCafe);
                        startActivity(i4);
                        break;
                    case R.id.khac:
                        Intent i5 = new Intent(TrangChuActivity.this,SanPhamActivity.class);
                        i5.putExtra("LOAI","Khác");
                        i5.putExtra("ThucUong",arrayListThucUong);
                        startActivity(i5);
                        break;
                    case R.id.comvanphong:
                        Intent i6 = new Intent(TrangChuActivity.this,SanPhamActivity.class);
                        i6.putExtra("LOAI","Cơm văn phòng");
                        i6.putExtra("ComVanPhong",arrayListComVanPhong);
                        startActivity(i6);
                        break;
                    case R.id.thoat:
                        System.exit(0);
                        break;
                    case R.id.lienhe:
                        Intent i7 = new Intent(TrangChuActivity.this,LienHeActivity.class);
                        startActivity(i7);
                        break;
                }
                return false;
            }
        });
    }
    public void setUpRcMonChinh(){
        adapter_rc_horizontalview adapterMonChinh = new adapter_rc_horizontalview(arrayListMonChinh,TrangChuActivity.this);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rc_MonChinh.setLayoutManager(layoutManager);
        rc_MonChinh.setAdapter(adapterMonChinh);
    }
    public void setUpRcMonVat(){
        adapter_rc_horizontalview adapterMonChinh = new adapter_rc_horizontalview(arrayListMonAnVat,TrangChuActivity.this);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rc_Mon_Vat.setLayoutManager(layoutManager);
        rc_Mon_Vat.setAdapter(adapterMonChinh);
    }
    public void setUpRcThucUong(){
        adapter_rc_horizontalview adapterMonChinh = new adapter_rc_horizontalview(arrayListThucUong,TrangChuActivity.this);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rc_Thuc_uong.setLayoutManager(layoutManager);
        rc_Thuc_uong.setAdapter(adapterMonChinh);
    }
    public void setUpAdapter(){
        setUpRcMonChinh();
        setUpRcMonVat();
        setUpRcThucUong();
        setUpCircleIndicator();
    }
}
