package com.vipin.QuizeApp.model;

/**
 * Display-only wrapper used to render category buttons on the home page.
 * Not persisted anywhere - built on the fly from whatever categories
 * currently exist in the loaded quizzes.
 */
public class CategoryInfo {

    private final String name;
    private final String icon;
    private final int quizCount;

    public CategoryInfo(String name, String icon, int quizCount) {
        this.name = name;
        this.icon = icon;
        this.quizCount = quizCount;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public int getQuizCount() {
        return quizCount;
    }
}
