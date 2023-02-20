package com.tcblog.core.models;

import java.util.List;

import com.tcblog.core.models.support.PostInfo;

public interface CategoryItems {
    public List<PostInfo> getItems();

    public String getTitle();
}