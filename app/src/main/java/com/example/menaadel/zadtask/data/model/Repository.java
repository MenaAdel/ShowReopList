package com.example.menaadel.zadtask.data.model;

/**
 * Created by MenaAdel on 1/3/2018.
 */

public class Repository {
    private String id;
    private String name;
    private String description;
    private String full_name;
    private Owner owner;
    private String html_url;
    private String owner_html_url;
    private boolean fork ;

    public Repository(){}

    public String getHtml_url() {
        return html_url;
    }

    public String getOwner_html_url() {
        return owner_html_url;
    }

    public boolean isFork() {
        return fork;
    }

    public Repository(String id, String repo_name, String description, String username, boolean fork, String html_url,
                      String owner_html_url) {
        this.id = id;
        this.name = repo_name;
        this.description = description;
        this.full_name = username;
        this.fork=fork;
        this.html_url=html_url;
        this.owner_html_url=owner_html_url;
    }

    public String getId() {
        return id;
    }

    public String getRepo_name() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return full_name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setRepository(String repo_name, String description, Owner owner, String html_url, String username, String fork) {
        this.name = repo_name;
        this.description = description;
        this.full_name = username;
        this.fork= Boolean.parseBoolean(fork);
        this.html_url=html_url;
        this.owner=owner;
    }
}
