package com.tcblog.core.models.support;

import java.util.Date;

import com.day.cq.wcm.api.Page;

public class PostInfo {
    private String title;
    private String subtitle;
    private String texto;
    private Date created;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    static public PostInfo fromPage(Page page) {
        String contentPath = "root/container/container/post";

        Date date = page.getProperties().get("jcr:created", Date.class);
        String title = page
            .getContentResource(contentPath)
            .getValueMap()
            .get("titulo", String.class);

        PostInfo postInfo = new PostInfo();

        postInfo.setCreated(date);
        postInfo.setTitle(title);

        return postInfo;
    }

    public PostInfo() {};

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public String getTexto() {
        return this.texto;
    }
}
