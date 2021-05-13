package com.example.dietapp.Models;

public class MenuModel {
    public String menu;
    public String video;

    public MenuModel() {
    }

    public MenuModel(String menu, String video) {
        this.menu = menu;
        this.video = video;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
