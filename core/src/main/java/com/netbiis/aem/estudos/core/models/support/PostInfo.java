package com.tcblog.core.models.support;

public class PostInfo {
    private String title;
    private String subtitle;
    private String texto;

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
