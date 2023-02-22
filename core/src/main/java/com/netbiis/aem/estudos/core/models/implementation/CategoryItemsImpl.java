package com.netbiis.aem.estudos.core.models.implementation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.compress.utils.Lists;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.commons.RangeIterator;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.netbiis.aem.estudos.core.models.CategoryItems;
import com.netbiis.aem.estudos.core.models.support.PostInfo;

@Model(adaptables = Resource.class, adapters = CategoryItems.class)
public class CategoryItemsImpl implements CategoryItems {
    private List<PostInfo> items;

    private String title;

    @Inject
    @Default(values = "otempo:pol-tica")
    private String tag;

    @Inject
    @Default(values = "2")
    private int quantity;

    @Self
    private Resource resource;

    private Comparator<Page> sortByDate = (a, b) -> {
        Date aDate = a.getProperties().get("jcr:created", Date.class);
        Date bDate = b.getProperties().get("jcr:created", Date.class);

        return bDate.compareTo(aDate); 
    };

    private Resource getFirstResourceThatContainsTag(String tag) {
        RangeIterator<Resource> rIterator = getTagManager().find(tag);
        Resource resource = rIterator.next();
        return resource;
    }

    private TagManager getTagManager() {
        return resource.getResourceResolver().adaptTo(TagManager.class);
    }

    private String getTitleFromTagId(String tagId) {
        Tag tagInfo = getTagManager().resolve(tag);
        return tagInfo.getTitle();
    }

    private PageManager getPageManager() {
        return resource.getResourceResolver().adaptTo(PageManager.class);
    }

    private List<Page> groupPagesFromAllTopicsPagesOfCategoryPage(Page categoryPage) {
        Iterator<Page> topicPages = categoryPage.listChildren();
        List<Page> pagesFromTopics = new ArrayList<>();

        while (topicPages.hasNext()) {
            Page topicPage = topicPages.next();

            Iterator<Page> pagesFromTopicIterator = topicPage.listChildren();

            pagesFromTopics.addAll(Lists.newArrayList(pagesFromTopicIterator));
        }

        return pagesFromTopics;
    }

    @PostConstruct
    void init() {
        items = new ArrayList<>();
        Resource resource = getFirstResourceThatContainsTag(tag);
        this.title = getTitleFromTagId(tag);

        Page categoryPage = getPageManager().getContainingPage(resource);
        List<Page> pagesFromTopics = groupPagesFromAllTopicsPagesOfCategoryPage(categoryPage);

        pagesFromTopics.stream().sorted(sortByDate).limit(quantity).forEach(page -> {
            items.add(PostInfo.fromPage(page));
        });
    }

    @Override
    public List<PostInfo> getItems() {
        return items;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
