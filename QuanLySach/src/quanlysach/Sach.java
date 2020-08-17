/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlysach;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Sach {
//    Masach nvarchar(20)not null,
//	TenSach nvarchar(50) not null,
//	NXB nvarchar(50) not null,
//	Sotrang int ,
//	SoLuong int ,
//	Giatien money ,
//	NgayNhap datetime,
//	vitridat nvarchar(30),
//	MaTheLoai nvarchar(15) not null,

    private String maS, tenS, NXB, vtDat, maTL;
    private int soTrang, soLuong, giaTien;
    private Date ngayNhap;

    public Sach() {
    }

    public Sach(String maS, String tenS, String NXB, int soTrang, int soLuong, int giaTien, Date ngayNhap, String vtDat, String maTL) {
        this.maS = maS;
        this.tenS = tenS;
        this.NXB = NXB;
        this.vtDat = vtDat;
        this.maTL = maTL;
        this.soTrang = soTrang;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.ngayNhap = ngayNhap;
    }

    public String getMaS() {
        return maS;
    }

    public void setMaS(String maS) {
        this.maS = maS;
    }

    public String getTenS() {
        return tenS;
    }

    public void setTenS(String tenS) {
        this.tenS = tenS;
    }

    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public String getVtDat() {
        return vtDat;
    }

    public void setVtDat(String vtDat) {
        this.vtDat = vtDat;
    }

    public String getMaTL() {
        return maTL;
    }

    public void setMaTL(String maTL) {
        this.maTL = maTL;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    @Override
    public String toString() {
        return "Sach{" + "maS=" + maS + ", tenS=" + tenS + ", NXB=" + NXB + ", vtDat=" + vtDat + ", maTL=" + maTL + ", soTrang=" + soTrang + ", soLuong=" + soLuong + ", giaTien=" + giaTien + ", ngayNhap=" + ngayNhap + '}';
    }
    
    

}
