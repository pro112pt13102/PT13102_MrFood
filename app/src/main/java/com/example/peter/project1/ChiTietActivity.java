package com.example.peter.project1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.project1.Model.SanPham;

import es.dmoral.toasty.Toasty;

import static com.example.peter.project1.SanPhamActivity.Addgiohang;

public class ChiTietActivity extends AppCompatActivity {
    SanPham sp;
    TextView tv_xemthem;
    LinearLayout linearLayout_xemthem;
    TextView giaSp,tenSp;
    ImageView imgHinhSp;
    ImageButton img_back_chitiet;
    ImageView img_giohang_chi_tiet;
    int key=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        Anhxa();
        getData();
        onClickEvent();



    }
    public void getData(){
        key=getIntent().getIntExtra("key",0);
        sp= (SanPham) getIntent().getSerializableExtra("SanPham");
        giaSp.setText(sp.getDongia()+"");
        tenSp.setText(sp.getTenSanPha());
        imgHinhSp.setImageResource(sp.getHinh());
    }
    public  void Anhxa(){
        img_giohang_chi_tiet=findViewById(R.id.img_giohang_chi_tiet);
        img_back_chitiet=findViewById(R.id.img_btn_back_chitiet);
        linearLayout_xemthem = findViewById(R.id.xemthem);
        tv_xemthem = findViewById(R.id.tv_noidungxemthem);
        tv_xemthem.setVisibility(View.INVISIBLE);
        giaSp=findViewById(R.id.tv_giasp_chitiet);
        tenSp=findViewById(R.id.tv_tensp_chitiet);
        imgHinhSp=findViewById(R.id.img_hinhsp_chitiet);
    }
    public void onClickEvent(){
        linearLayout_xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_xemthem.getVisibility() == View.VISIBLE){
                    tv_xemthem.setVisibility(View.GONE);
                } else tv_xemthem.setVisibility(View.VISIBLE);
            }
        });

        //onclick img_back_chitiet
        img_back_chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // onclick img_giohang_chi_tiet
        img_giohang_chi_tiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gửi và thêm sản phẩm trên sever
                // đồng thời update giỏ hàng ở Sanpham Activity
                if(key==0){
                    Addgiohang(sp);
                }
                showToastTy();
                Toast.makeText(ChiTietActivity.this, "Đã thêm vào giỏ hàng trên sever", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void showToastTy(){
        Toasty.Config.getInstance()
                .setTextColor(Color.parseColor("#FFF1DD2F"))
                .setSuccessColor(Color.parseColor("#FFF11616"))
                .apply();
        Toasty.success(ChiTietActivity.this,"Đã thêm vào giỏ hàng").show();
    }
}
