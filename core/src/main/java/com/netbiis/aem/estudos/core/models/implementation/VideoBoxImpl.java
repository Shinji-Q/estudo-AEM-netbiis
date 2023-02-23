package com.netbiis.aem.estudos.core.models.implementation;

import com.netbiis.aem.estudos.core.models.VideoBox;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(adaptables = Resource.class, adapters = VideoBox.class)
public class VideoBoxImpl implements VideoBox {
  private String log;

  @Inject
  @Default(values = "")
  private String url;

  @Self
  private Resource resource;

  @PostConstruct
  private void init() throws Exception {
    try {
      Node embedNode = resource.adaptTo(Node.class).addNode("embed");
    
      embedNode.setProperty("url", url);
      embedNode.setProperty("type", "url");
      embedNode.setProperty("jcr:resourceType", "/apps/tcblog/components/embed");

      log = embedNode.getPath();
    } catch(Exception ex) {
      throw ex;
    }
    ModifiableValueMap embed = resource
      .getChild("test")
      .adaptTo(ModifiableValueMap.class);

    embed.put("url", url);
    embed.put("type", "url");

    try {
      resource.getResourceResolver().commit();
    } catch (PersistenceException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getLog() {
    return log;
  }
}
