package apps.tcblog.components.relatedtopics.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.IteratorUtils;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

public class RelatedTopicsLogic extends WCMUsePojo {
    public List<String> tagList;

    @Override
    public void activate() throws Exception {
        Tag[] tags = getCurrentPage().getTags();
        
        tagList = IteratorUtils
            .toList(tags[0].getParent().listChildren())
            .stream()
            .map(tag -> tag.getTitle())
            .collect(Collectors.toList());
        /* 
        tagList = Arrays.asList(tags).stream().map((tag) -> {
            return IteratorUtils
            .toList(tag.getParent().listChildren())
            .stream()
            .map(v -> v.getTitle())
            .collect(Collectors.toList());
        }).reduce(null, (acc, value) -> {
            acc.addAll(value); 
            return acc;
        }); */
    }
    
    public List<String> getTagList() {
        return this.tagList;
    }
}
