package apps.tcblog.components.otempo.relatedtopics.logic;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.IteratorUtils;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.tagging.Tag;

public class RelatedTopicsLogic extends WCMUsePojo {
    public List<String> tagList;

    @Override
    public void activate() throws Exception {
        Tag[] tags = getCurrentPage().getParent().getTags();
        
        // Command
        tagList = IteratorUtils
            .toList(tags[0].getParent().listChildren())
            .stream()
            .map(tag -> tag.getTitle())
            .collect(Collectors.toList());
    }
    
    public List<String> getTagList() {
        return this.tagList;
    }
}
