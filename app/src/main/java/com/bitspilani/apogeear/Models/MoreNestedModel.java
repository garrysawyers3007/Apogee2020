package com.bitspilani.apogeear.Models;

import java.util.List;

public class MoreNestedModel {
    private String name;
    private List<MoreModel> moreModelList;

    public MoreNestedModel(String name, List<MoreModel> moreModelList) {
        this.name = name;
        this.moreModelList = moreModelList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MoreModel> getMoreModelList() {
        return moreModelList;
    }

    public void setMoreModelList(List<MoreModel> moreModelList) {
        this.moreModelList = moreModelList;
    }
}
