package com.example.peter.project1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.project1.Adapter.adapter_rc_gio_hang;
import com.example.peter.project1.Model.RecyclerItemClickListener;
import com.example.peter.project1.Model.SanPham;
import com.example.peter.project1.Service.OnClearFromRecentService;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;

import static com.example.peter.project1.Adapter.adapter_rc_gio_hang.getEditSoluong;



public class GioHangActivity extends AppCompatActivity {
    Button btn_thanh_toan;
    ImageButton img_btn_back_gio_hang;
    static RecyclerView rc_giohang;
    static ArrayList<SanPham> arrayList_giohang;
    static TextView tv_giasp_giohang;
    static int positionItem;
    adapter_rc_gio_hang adapter_rc_gio_hang;
    String url ="http://immense-scrubland-98497.herokuapp.com/public/images/28.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        Anhxa();
        getDataFromSanPhamActivity();
//        setData();
        tinhtong();
        setUpRcGiohang();
        AddVentforKeybord();
        img_btn_back_gio_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update 4/12/2018
                if(arrayList_giohang.size()!=0){
                    updateSoluong( getEditSoluong());
                    tinhtong();
                }
                //
                updateDataToSever();
                sendBackArrayGioHangEditSanPhamActivity();
            }
        });
        //Go to MuaNgay
        btn_thanh_toan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGiohangToMuaNgayActivity();
            }
        });
    }

    public void Anhxa() {
        btn_thanh_toan=findViewById(R.id.btn_thanh_toan);
        tv_giasp_giohang = findViewById(R.id.tv_giasp_giohang);
        img_btn_back_gio_hang = findViewById(R.id.img_btn_back_gio_hang);
        rc_giohang = findViewById(R.id.rc_giohang);
    }

    public void setUpRcGiohang() {
        adapter_rc_gio_hang = new adapter_rc_gio_hang(arrayList_giohang, GioHangActivity.this, GioHangActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rc_giohang.setLayoutManager(layoutManager);
        rc_giohang.setAdapter(adapter_rc_gio_hang);
    }

    public  static void  tinhtong() {
        int tong=0;
        for (int i = 0; i < arrayList_giohang.size(); i++) {
            int dongia=arrayList_giohang.get(i).getDongia();
            int soluong=arrayList_giohang.get(i).getSoluong();
            tong = tong + (dongia*soluong);
        }
        setTongTien(tong);


    }

       public void getDataFromSanPhamActivity() {
        arrayList_giohang = (ArrayList<SanPham>) getIntent().getSerializableExtra("arrayGioHang");
//           Toast.makeText(this, "Đã Nhận danh sách giỏ hàng từ SanphamAcTivity - "+arrayList_giohang.size(), Toast.LENGTH_SHORT).show();
    }

    public static ArrayList<SanPham> getArryGioHang() {
        return arrayList_giohang;
    }

    public void setData() {
        for (int i = 0; i < 20; i++) {
            SanPham sp = new SanPham("sp" + i, 5 + i * 10, url,1,i+1);
            arrayList_giohang.add(sp);
        }

    }
    public static void setTongTien(int tong){
        tv_giasp_giohang.setText(tong + " đồng");
    }
    public  static void updateArraylistgiohang(ArrayList<SanPham> arrayList){
            arrayList_giohang=arrayList;
//        Log.d("AAA","size "+arrayList_giohang.size());
    }
    public  void updateDataToSever(){
        Toast.makeText(GioHangActivity.this, "đả gửi giỏ hàng lên sever", Toast.LENGTH_SHORT).show();
    }
    public void sendBackArrayGioHangEditSanPhamActivity(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("arrayListEdit",arrayList_giohang);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
    public  void AddVentforKeybord(){
        KeyboardVisibilityEvent.setEventListener(
                GioHangActivity.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if(isOpen){

                        }else{
                            updateSoluong( getEditSoluong());
                            tinhtong();

                        }
                    }
                });
    }
    public static void updatePositionEditItem(int position){
        positionItem=position;
    }
    public void updateSoluong(String chuoiEdit){
        SanPham sp = arrayList_giohang.get(positionItem);
        if(chuoiEdit.trim().length()>0){
                    if(Integer.parseInt(chuoiEdit.trim())<=0){
                        AlertDialogError();
                        View itemView=rc_giohang.getChildAt(positionItem);
                        EditText edt=itemView.findViewById(R.id.et_soluong_giohang);
                        edt.setText("1");
                        edt.setFocusable(false);
                    }
                    sp.setSoluong(Integer.parseInt(chuoiEdit.toString()));
                     arrayList_giohang.set(positionItem,sp);
                    adapter_rc_gio_hang.UpdateAraylistGiohang(arrayList_giohang);

                }else{
            AlertDialogError();
            // Tìm cách show bàn phím số

        }
    }
    public void AlertDialogError(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                GioHangActivity.this);

        // set title
        alertDialogBuilder.setTitle("Thông báo");

        // set dialog message
        alertDialogBuilder
                .setMessage("Số lượng sản phẩm tối thiểu là 1")
                .setCancelable(false)

                .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    public void sendGiohangToMuaNgayActivity(){
        Intent i = new Intent(GioHangActivity.this,MuaNgayActivity.class);
        i.putExtra("arrayListgiohang",arrayList_giohang);
        startActivity(i);
    }
    public static void RemoveArraylistGiohang(){
        arrayList_giohang.clear();

    }

}

