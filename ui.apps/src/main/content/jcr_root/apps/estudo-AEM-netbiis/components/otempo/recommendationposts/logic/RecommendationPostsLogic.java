package apps.tcblog.components.otempo.recommendationposts.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.ValueMap;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

public class RecommendationPostsLogic extends WCMUsePojo {
    private List<Map<String, String>> postInfoList;

    private String contentPath = "root/container/container/post";

    @Override
    public void activate() throws Exception {
        postInfoList = new ArrayList<>();

        Page currentPage = getCurrentPage();

        Page parentPage = currentPage.getParent();
    
        Iterator<Page> children = parentPage.listChildren();

        while(children.hasNext()) {
            Page page = children.next();

            ValueMap content = page.getContentResource(contentPath).getValueMap();

            String subtitulo = content.get("subtitulo", String.class);
            String href = page.getName() + ".html";
            String createdAt = page.getProperties().get("jcr:created", String.class);

            Map<String, String> postInfo = new HashMap<>();

            postInfo.put("subtitulo", subtitulo);
            postInfo.put("href", href);
            postInfo.put("createdAt", createdAt);
            
            postInfoList.add(postInfo);
        }

        Collections.reverse(postInfoList);
    }
    
    public List<Map<String, String>> getPostInfoList() {
        return this.postInfoList;
    }
}
