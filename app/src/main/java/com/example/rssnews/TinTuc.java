package com.example.rssnews;

import java.io.Serializable;

public class TinTuc implements Serializable {
    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAnhBia() {
        return anhBia;
    }

    public void setAnhBia(String anhBia) {
        this.anhBia = anhBia;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    private String tieuDe;
    private String link;
    private String anhBia;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    private String logo;

    public TinTuc(String tieuDe, String link, String anhBia, String ngayDang,String logo) {
        this.tieuDe = tieuDe;
        this.link = link;
        this.anhBia = anhBia;
        this.ngayDang = ngayDang;
        this.logo = logo;
    }

    public TinTuc() {
    }

    private String ngayDang;
}
