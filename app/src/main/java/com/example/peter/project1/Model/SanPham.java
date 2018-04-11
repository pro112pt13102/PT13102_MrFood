package com.example.peter.project1.Model;

import java.io.Serializable;

/**
 * Created by daovip on 3/27/2018.
 */

public class SanPham implements Serializable{
    private String tenSanPha;
    private int dongia;
    private int hinh;
    private int soluong;
    private int MaSP;

    public int getSoluong() {
        return soluong;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public int getMaSP() {
        return MaSP;

    }

    public void setSoluong(int soluong) {

        this.soluong = soluong;
    }

    public SanPham(String tenSanPha, int dongia, int hinh, int soluong,int MaSP) {
        this.tenSanPha = tenSanPha;
        this.dongia = dongia;
        this.hinh = hinh;
        this.soluong = soluong;
        this.MaSP=MaSP;
    }

    public SanPham(String tenSanPha, int dongia, int hinh) {
        this.tenSanPha = tenSanPha;
        this.dongia = dongia;
        this.hinh = hinh;
    }

    public SanPham() {
    }

    public String getTenSanPha() {
        return tenSanPha;
    }

    public int getDongia() {
        return dongia;
    }

    public int getHinh() {
        return hinh;
    }

    public void setTenSanPha(String tenSanPha) {
        this.tenSanPha = tenSanPha;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }
}
