package com.example.newsdaily.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class APIResponse implements Serializable {
    String status;
    int totalResult;
    List<Articles> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
}
