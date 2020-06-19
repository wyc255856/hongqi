package com.faw.hongqi.model;

import java.io.Serializable;

public class ContentItemModel implements Serializable {
    String image;
    String video;
    int template;
    String content;
    String video1_note;

    public String getVideo1_note() {
        return video1_note;
    }

    public void setVideo1_note(String video1_note) {
        this.video1_note = video1_note;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
