package com.adobe.aem.guides.wknd.core.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;

import com.adobe.aem.guides.wknd.core.models.support.InfoPage;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = Resource.class)
public class ReadMore {
    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    private List<InfoPage> listInfoPages = new ArrayList<InfoPage>();

    private List<Page> listPages = new ArrayList<Page>();

    @PostConstruct
    protected void init(){
        
        
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        
        Page currentPage = Optional.ofNullable(pageManager)
            .map(pm -> pm.getContainingPage(currentResource))
            .orElse(null);

        if (currentPage == null){
            return;
        }

        Page parentPage = currentPage.getParent();

        if (parentPage == null){
            return;
        }

        Iterator<Page> pages = parentPage.listChildren();

        while(pages.hasNext()){
            Page page = pages.next();

            listInfoPages.add(new InfoPage(page));
            listPages.add(page);
        }
    }

    private Comparator<InfoPage> sortByDate = (a, b) -> {
        Date aDate = a.getDate();
        Date bDate = b.getDate();

        return bDate.compareTo(aDate); 
    };

    public List<InfoPage> getListInfoPages() {
        List<InfoPage> list = new ArrayList<InfoPage>();
        listInfoPages.stream()
            .sorted(sortByDate)
            .limit(4)
            .forEach(page -> {
                list.add(page);
            });
        return list;
    }

    public List<Page> getListPages() {
        return listPages;
    }
    
}
