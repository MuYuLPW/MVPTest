package com.kyty.mvptest.bean;

import java.io.Serializable;
import java.util.List;

public class AllBean implements Serializable {
    private String title;
    private List<ListBean> list;

    @Override
    public String toString() {
        return "AllBean{" +
                "title='" + title + '\'' +
                ", list=" + list.size() +
                '}';
    }

    public AllBean(String title, List<ListBean> list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }
}
