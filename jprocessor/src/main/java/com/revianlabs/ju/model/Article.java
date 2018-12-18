package com.revianlabs.ju.model;

public class Article {
    private String id;
    private String title;
    private String byline;
    private String body;
    private Article related;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Article getRelated() {
        return related;
    }

    public void setRelated(Article related) {
        this.related = related;
    }
}
