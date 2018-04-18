package com.example.config.oauth.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Github Data.
 *
 * @author yuheng.lin@hand-china.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubData {

    private long id;

    private String login;

    private String avatar_url;

    private String url;

    private String html_url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}
