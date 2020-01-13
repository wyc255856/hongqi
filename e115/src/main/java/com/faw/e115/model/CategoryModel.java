package com.faw.e115.model;

import com.faw.e115.dbutil.CarDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

@ModelContainer
@Table(database = CarDatabase.class)
public class CategoryModel extends BaseModel {
    @PrimaryKey(autoincrement = true)
    private int catid;
    @Column
    private int caid;

    @Column
    private int type;
    @Column
    private int parentid;
    @Column
    private String catname;
    @Column
    private String description;
    @Column
    private String url;
    @Column
    private String setting;
    @Column
    private int listorder;
    @Column
    private int ismenu;
    @Column
    private String img;
    @Column
    private String img_path;
    @Column
    private String level;
    @Column
    private String template;
    @Column
    private int is_benceng_duyou;
    @Column
    private int sdss;
    @Column
    private int sdhh;
    @Column
    private int sdzg;
    @Column
    private int zdss;
    @Column
    private int zdhh;
    @Column
    private int zdzg;
    @Column
    private int zdqj;

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getCatid() {
        return catid;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getParentid() {
        return parentid;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getCatname() {
        return catname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getSetting() {
        return setting;
    }

    public void setListorder(int listorder) {
        this.listorder = listorder;
    }

    public int getListorder() {
        return listorder;
    }

    public void setIsmenu(int ismenu) {
        this.ismenu = ismenu;
    }

    public int getIsmenu() {
        return ismenu;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public void setIs_benceng_duyou(int is_benceng_duyou) {
        this.is_benceng_duyou = is_benceng_duyou;
    }

    public int getIs_benceng_duyou() {
        return is_benceng_duyou;
    }

    public void setSdss(int sdss) {
        this.sdss = sdss;
    }

    public int getSdss() {
        return sdss;
    }

    public void setSdhh(int sdhh) {
        this.sdhh = sdhh;
    }

    public int getSdhh() {
        return sdhh;
    }

    public void setSdzg(int sdzg) {
        this.sdzg = sdzg;
    }

    public int getSdzg() {
        return sdzg;
    }

    public void setZdss(int zdss) {
        this.zdss = zdss;
    }

    public int getZdss() {
        return zdss;
    }

    public void setZdhh(int zdhh) {
        this.zdhh = zdhh;
    }

    public int getZdhh() {
        return zdhh;
    }

    public void setZdzg(int zdzg) {
        this.zdzg = zdzg;
    }

    public int getZdzg() {
        return zdzg;
    }

    public void setZdqj(int zdqj) {
        this.zdqj = zdqj;
    }

    public int getZdqj() {
        return zdqj;
    }

    public int getCaid() {
        return caid;
    }

    public void setCaid(int caid) {
        this.caid = caid;
    }
}
