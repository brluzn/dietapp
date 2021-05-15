package com.example.dietapp.Models;

public class MenuModel {
    public String menu;
    public String video;
    public String recipe;
    public String recipe_title;

    public MenuModel() {
    }

    public MenuModel(String menu, String video, String recipe, String recipe_title) {
        this.menu = menu;
        this.video = video;
        this.recipe = recipe;
        this.recipe_title = recipe_title;
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

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getRecipe_title() {
        return recipe_title;
    }

    public void setRecipe_title(String recipe_title) {
        this.recipe_title = recipe_title;
    }
}
