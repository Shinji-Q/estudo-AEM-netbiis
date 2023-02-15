package com.tcblog.core.models.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.commons.RangeIterator;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.tcblog.core.models.CategoryItems;
import com.tcblog.core.models.support.PostInfo;

@Model(adaptables = Resource.class, adapters = CategoryItems.class)
public class CategoryItemsImpl implements CategoryItems {
    private List<PostInfo> items;

    @Inject
    @Default(values = "Aparte")
    private String tag;

    @Self
    private Resource resource;

    private String contentPath = "root/container/container/post";

    @PostConstruct
    public void init() {
        items = new ArrayList<>();

        TagManager tagManager = resource.getResourceResolver().adaptTo(TagManager.class);
        PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
        RangeIterator<Resource> rIterator = tagManager.find(tag);

        while (rIterator.hasNext()) {
            try {
                Resource r = rIterator.next();

                Page page = pageManager.getContainingPage(r);

                ValueMap contentProperties = page.getContentResource(contentPath).getValueMap();
                
                PostInfo postInfo = new PostInfo();

                postInfo.setTitle(contentProperties.get("titulo", String.class));

                items.add(postInfo);
            } catch(Exception e) {}
        }
    }

    @Override
    public List<PostInfo> getItems() {
        return items;
    }
}
