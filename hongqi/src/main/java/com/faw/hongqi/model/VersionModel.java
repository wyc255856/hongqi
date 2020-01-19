package com.faw.hongqi.model;

import java.io.Serializable;
import java.util.List;

public class VersionModel extends BaseModel implements Serializable {
    private String category;
    private String news;
    private String version;
    private List<String> zip_address;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getZip_address() {
        return zip_address;
    }

    public void setZip_address(List<String> zip_address) {
        this.zip_address = zip_address;
    }
}
