package com.netbiis.aem.estudos.core.models.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.netbiis.aem.estudos.core.models.PhotosGallery;

@Model(adaptables = Resource.class, adapters = PhotosGallery.class)
public class PhotosGalleryImpl implements PhotosGallery {
    private List<String> imagePaths;

    @Self
    private Resource resource;

    @PostConstruct
    void init() {
        try {
            imagePaths = new ArrayList<>();

            Resource multifield = resource.getChild("multifield");

            if (multifield == null) return;
            
            Iterator<Resource> items = multifield.getChildren().iterator();

            while (items.hasNext()) {
                Resource item = items.next();

                imagePaths.add(item.getValueMap().get("fileReference", String.class));
            }
        } catch(Exception e) {
            imagePaths.add("Exception: " + e.getMessage());
        }
    }

    @Override
    public List<String> getImagePaths() {
        return imagePaths;
    }
}
