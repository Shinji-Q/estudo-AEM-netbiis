package com.adobe.aem.guides.wknd.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.guides.wknd.core.models.support.InfoPage;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.tagging.Tag;


@Model(adaptables = Resource.class)
public class Topics {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;
    
    Tag[] tags;

    

    @PostConstruct
    public void init(){
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        
        Page currentPage = Optional.ofNullable(pageManager)
            .map(pm -> pm.getContainingPage(currentResource))
            .orElse(null);

        if (currentPage == null){
            return;
        }

        tags =currentPage.getTags();

    }

    public List<String> getTags(){
        List<String> lista = new ArrayList<String>();
        for (Tag tag : tags) {
            lista.add(tag.getTitle());
        }
        return lista;
    }


    
}
