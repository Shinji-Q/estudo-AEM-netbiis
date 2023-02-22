package com.netbiis.aem.estudos.core.models;

import java.util.List;

import com.netbiis.aem.estudos.core.models.support.PostInfo;

public interface CategoryItems {
    public List<PostInfo> getItems();

    public String getTitle();
}