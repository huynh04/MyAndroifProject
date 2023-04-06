package com.example.newsdaily.Listener;

import com.example.newsdaily.Model.Articles;

import java.util.ArrayList;
import java.util.List;

public interface OnFetchDataListener<APIResponse>{
    void onFetchData(List<Articles> articles, String message);
    void onError(String message);
}
