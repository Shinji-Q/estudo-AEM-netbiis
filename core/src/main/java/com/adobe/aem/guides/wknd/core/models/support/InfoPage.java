package com.adobe.aem.guides.wknd.core.models.support;

import java.util.Date;

import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;

public class InfoPage {
    
    private String title;

    private Date date;

    private String createdBy;

    private String path;

    private String tags;

    public InfoPage(){}

    public InfoPage(Page page){
        ValueMap pageProperties = page.getProperties();
        setTitle(page.getTitle());
        setCreatedBy(pageProperties.get("jcr:createdBy", String.class));
        setDate(pageProperties.get("jcr:created",Date.class));
        setPath(page.getPath());
        setTags(pageProperties.get("cq:tags", String.class));
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    

}
