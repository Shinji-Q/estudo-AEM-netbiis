package com.netbiis.aem.estudos.core.models.helper;

import org.apache.sling.api.resource.Resource;

import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.PageManager;

public class ResourceHelper {
    public static TagManager getTagManager(Resource resource) {
        return resource.getResourceResolver().adaptTo(TagManager.class);
    }
    
    public static PageManager getPageManager(Resource resource) {
        return resource.getResourceResolver().adaptTo(PageManager.class);
    }
}
