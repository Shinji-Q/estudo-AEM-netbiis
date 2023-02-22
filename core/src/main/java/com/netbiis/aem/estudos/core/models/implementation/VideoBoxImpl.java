package com.netbiis.aem.estudos.core.models.implementation;

import com.netbiis.aem.estudos.core.models.VideoBox;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(adaptables = Resource.class, adapters = VideoBox.class)
public class VideoBoxImpl implements VideoBox {

  @Inject
  @Default(values = "")
  private String url;

  @Self
  private Resource resource;

  @PostConstruct
  private void init() {
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
}
