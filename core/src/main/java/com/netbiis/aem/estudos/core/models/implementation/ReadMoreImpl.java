package com.netbiis.aem.estudos.core.models.implementation;

import com.day.cq.wcm.api.Page;
import com.netbiis.aem.estudos.core.models.ReadMore;
import com.netbiis.aem.estudos.core.models.helper.ResourceHelper;
import com.netbiis.aem.estudos.core.models.support.PostInfo;
import com.netbiis.aem.estudos.core.models.support.ReadMoreInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(adaptables = Resource.class, adapters = ReadMore.class)
public class ReadMoreImpl implements ReadMore {

  private List<ReadMoreInfo> readMoreInfoList;

  @Self
  private Resource resource;

  @PostConstruct
  void init() {
    try {
      readMoreInfoList = new ArrayList<>();
      Iterator<Resource> nodePaths = resource
        .getChild("multifield")
        .listChildren();
      List<String> pagePaths = new ArrayList<>();

      while (nodePaths.hasNext()) {
        Resource node = nodePaths.next();

        pagePaths.add(node.getValueMap().get("path", String.class));
      }

      pagePaths.forEach(path -> {
        Page page = ResourceHelper.getPageManager(resource).getPage(path);

        PostInfo postInfo = PostInfo.fromPage(page);

        ReadMoreInfo readMoreInfo = new ReadMoreInfo();

        readMoreInfo.setPath(path);
        readMoreInfo.setTitle(postInfo.getTitle());

        readMoreInfoList.add(readMoreInfo);
      });
    } catch (Exception e) {
        
    }
  }

  @Override
  public List<ReadMoreInfo> getReadMoreInfo() {
    return this.readMoreInfoList;
  }
}
