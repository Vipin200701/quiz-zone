package com.vipin.QuizeApp.service;

import com.vipin.QuizeApp.model.Quiz;

import java.util.List;
import java.util.Map;

public interface QuizService {

    /** All quizzes loaded from JSON, flat list. */
    List<Quiz> getAllQuizzes();

    /** A single quiz by its id (derived from its file path). */
    Quiz getQuizById(String id);

    /**
     * Quizzes grouped for the browse page.
     * Key = "Category / Subcategory" (e.g. "Developer / Java"), preserving insertion order.
     */
    Map<String, List<Quiz>> getGroupedQuizzes();

    /** Distinct top-level categories, in first-seen order (used for home page buttons). */
    List<String> getCategories();

    /**
     * Quizzes belonging to a single category, grouped by subcategory
     * (or by the category itself if a quiz has no subcategory).
     */
    Map<String, List<Quiz>> getGroupedQuizzesByCategory(String category);

    /** Quizzes whose title, category, subcategory or difficulty matches the given text. */
    List<Quiz> searchQuizzes(String query);
}
