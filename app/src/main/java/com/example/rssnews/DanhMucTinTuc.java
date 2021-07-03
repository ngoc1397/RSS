package com.example.rssnews;

public class DanhMucTinTuc {
    String tenDanhMuc;

    public DanhMucTinTuc(String tenDanhMuc, String link, int img) {
        this.tenDanhMuc = tenDanhMuc;
        this.link = link;
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    String link;
    int img;
}
