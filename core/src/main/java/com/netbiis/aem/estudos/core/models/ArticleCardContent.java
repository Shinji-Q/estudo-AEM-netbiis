package com.netbiis.aem.estudos.core.models;

import com.adobe.cq.wcm.core.components.models.Image;

/*
 * Classe feita para guardar as informações
 * que serão usadas nos cards com as matérias
 * mais recentes da mesma categoria.
 */
public class ArticleCardContent {
    private String title;
    private String bodyText;
    private String image;

    public ArticleCardContent(){}

    //acessores
    public void setTitle(String title){
        this.title = title;
    }

    public void setBodyText(String bodyText){
        this.bodyText = bodyText.substring(0, 144) + "...";
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getTitle(){
        return title;
    }

    public String getBodyText(){
        return bodyText;
    }

    public String getImage(){
        return image;
    }
    
}
