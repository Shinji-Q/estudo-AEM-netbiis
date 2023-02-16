package com.netbiis.aem.estudos.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

import com.day.cq.wcm.api.PageManager;
import com.adobe.cq.wcm.core.components.models.Image;
import com.day.cq.commons.ImageHelper;
import com.day.cq.wcm.api.Page;

import javax.annotation.PostConstruct;

@Model(adaptables=SlingHttpServletRequest.class,
        defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class CategoryArticles {

    @Self
    private SlingHttpServletRequest request;

    @ScriptVariable
    private Page currentPage;

    private List<Page> siblings;
    private String message;
    private Resource resource; //current component resource

    @PostConstruct
    protected void init(){

        message = "iniciado, meu compatriota";

        siblings = new ArrayList<Page>();
        resource = request.getResource();

        Page parent = currentPage.getParent();

        parent.listChildren().forEachRemaining((sibling)->{
            siblings.add(sibling);
        });
    }

    public String getMessage(){
        return message;
    }

    public String getCategoryName(){
        return currentPage.getParent().getName();
    }



    /*
     * 
     */
    public List<ArticleCardContent> getSiblingsContent(){

        List<ArticleCardContent> siblingsContent = new ArrayList();


        //TODO organizar por data e selecionar os 4 mais recentes
        siblings.forEach((sibling)->{
            ArticleCardContent sc = new ArticleCardContent();

            // extraindo o conteúdo
            sc.setTitle(
                sibling
                .getContentResource("root/container/title")
                .getValueMap()
                .get("jcr:title", String.class)
            );
            sc.setBodyText(
                sibling
                .getContentResource("root/container/text")
                .getValueMap()
                .get("text", String.class)
            );
            //TODO descobrir como fazer as imagens funcionarem 
            sc.setImage(
                sibling
                .getContentResource("root/container/image/file/jcr:content")
                .getValueMap()
                .get("jcr:data", Image.class)
            );

            siblingsContent.add(sc);
        });
        return siblingsContent;
    }

}
