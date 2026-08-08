package com.vipin.QuizeApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Plain POJO representing a quiz. Loaded from a JSON file under
 * resources/quizzes/{category}/{subcategory}/{difficulty}.json
 * No database / JPA involved - everything lives in memory.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quiz {

    // Unique id derived from the JSON file's path, e.g. "developer-java-easy"
    private String id;

    private String title;
    private String category;
    private String subcategory;
    private String difficulty;

    private List<Question> questions;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
