package com.netbiis.aem.estudos.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;



import com.day.cq.wcm.api.Page;

import javax.annotation.PostConstruct;

@Model(adaptables=SlingHttpServletRequest.class,
        defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class CategoryArticles {
    private static final Logger logger = LoggerFactory.getLogger(CategoryArticles.class);

    @Self
    private SlingHttpServletRequest request;

    @ScriptVariable
    private Page currentPage;

    private List<Page> siblings;
    private String message;
    private Resource resource; //current component resource

    @PostConstruct

    protected void init(){
        logger.info("\n================iniciado================");

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
            Resource titleResource = sibling.getContentResource("root/container/title");
            Resource bodyTextResource =  sibling.getContentResource("root/container/text");
            Resource imageResource = sibling.getContentResource("cq:featuredimage");
            // extraindo o conteúdo
            sc.setTitle(
                titleResource.getValueMap().get("jcr:title", String.class)
            );
            sc.setBodyText(
                bodyTextResource.getValueMap().get("text", String.class)
            );
            sc.setImage( imageResource!=null ?
                imageResource.getValueMap().get("fileReference",String.class)
                : ""
            );

            siblingsContent.add(sc);
        });
        return siblingsContent;
    }

}
