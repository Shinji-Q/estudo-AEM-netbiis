package com.netbiis.aem.estudos.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Page;

import javax.annotation.PostConstruct;

@Model(adaptables=SlingHttpServletRequest.class)
public class TestComponentModel {

    private String message;

    @Self
    private Resource resource;
    

    @PostConstruct
    protected void init(){
        message = "certamente uma das mensagens já escrita";
        Page current = resource.getResourceResolver().adaptTo(PageManager.class).getContainingPage(resource);
        current.getContentResource("root/container/container/title").getValueMap().get("jcr:title");
        current.getContentResource("root/container/container/text").getValueMap().get("text");
    }

    public String getMessage(){
        return message;
    }
}
