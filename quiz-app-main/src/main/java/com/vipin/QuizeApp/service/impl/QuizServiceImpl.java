package com.vipin.QuizeApp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vipin.QuizeApp.model.Question;
import com.vipin.QuizeApp.model.Quiz;
import com.vipin.QuizeApp.service.QuizService;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Loads all quizzes from JSON files under resources/quizzes/**.json at
 * application startup and keeps them in memory. No database involved.
 *
 * To add a new quiz: drop a JSON file anywhere under
 * src/main/resources/quizzes/{category}/{subcategory}/{difficulty}.json
 * and redeploy - it will be picked up (and a new category button will
 * appear on the home page automatically if it's a new category).
 *
 * To remove a quiz: delete its JSON file and redeploy.
 */
@Service
public class QuizServiceImpl implements QuizService {

    private static final String BASE_LOCATION = "classpath*:quizzes/**/*.json";
    private static final List<String> DIFFICULTY_ORDER = List.of("easy", "medium", "hard");

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, Quiz> quizzesById = new LinkedHashMap<>();
    private final Map<String, List<Quiz>> groupedQuizzes = new LinkedHashMap<>();
    private final List<String> categories = new ArrayList<>();

    @PostConstruct
    public void loadQuizzes() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(BASE_LOCATION);

            List<Quiz> loaded = new ArrayList<>();

            for (Resource resource : resources) {
                try {
                    Quiz quiz = objectMapper.readValue(resource.getInputStream(), Quiz.class);
                    String relativePath = extractRelativePath(resource);
                    quiz.setId(relativePath.replace(".json", "").replace("/", "-"));

                    // Fall back to folder structure if the JSON didn't specify these
                    String[] parts = relativePath.replace(".json", "").split("/");
                    if (quiz.getCategory() == null && parts.length > 0) {
                        quiz.setCategory(capitalize(parts[0]));
                    }
                    if (quiz.getSubcategory() == null && parts.length > 1) {
                        quiz.setSubcategory(capitalize(parts[1]));
                    }
                    if (quiz.getDifficulty() == null && parts.length > 2) {
                        quiz.setDifficulty(capitalize(parts[2]));
                    }

                    if (quiz.getQuestions() != null) {
                        int idx = 0;
                        for (Question q : quiz.getQuestions()) {
                            q.setId(idx++);
                        }
                    } else {
                        quiz.setQuestions(new ArrayList<>());
                    }

                    loaded.add(quiz);
                } catch (Exception e) {
                    System.err.println("Skipping invalid quiz file " + resource.getFilename() + ": " + e.getMessage());
                }
            }

            // Sort: category, then subcategory, then difficulty (easy/medium/hard)
            loaded.sort(Comparator
                    .comparing((Quiz q) -> safe(q.getCategory()))
                    .thenComparing(q -> safe(q.getSubcategory()))
                    .thenComparing(q -> DIFFICULTY_ORDER.indexOf(safe(q.getDifficulty()).toLowerCase())));

            LinkedHashSet<String> categorySet = new LinkedHashSet<>();

            for (Quiz quiz : loaded) {
                quizzesById.put(quiz.getId(), quiz);
                categorySet.add(quiz.getCategory());

                String groupKey = quiz.getSubcategory() != null && !quiz.getSubcategory().isBlank()
                        ? quiz.getCategory() + " / " + quiz.getSubcategory()
                        : quiz.getCategory();

                groupedQuizzes.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(quiz);
            }

            categories.addAll(categorySet);

            System.out.println("Loaded " + quizzesById.size() + " quizzes across " + categories.size() + " categories from " + BASE_LOCATION);

        } catch (IOException e) {
            System.err.println("Failed to load quizzes: " + e.getMessage());
        }
    }

    private String extractRelativePath(Resource resource) throws IOException {
        String uri = resource.getURI().toString();
        int idx = uri.indexOf("quizzes/");
        return idx >= 0 ? uri.substring(idx + "quizzes/".length()) : resource.getFilename();
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private String capitalize(String s) {
        if (s == null || s.isBlank()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return new ArrayList<>(quizzesById.values());
    }

    @Override
    public Quiz getQuizById(String id) {
        Quiz quiz = quizzesById.get(id);
        if (quiz == null) {
            throw new RuntimeException("Quiz not found with id " + id);
        }
        return quiz;
    }

    @Override
    public Map<String, List<Quiz>> getGroupedQuizzes() {
        return groupedQuizzes;
    }

    @Override
    public List<String> getCategories() {
        return new ArrayList<>(categories);
    }

    @Override
    public Map<String, List<Quiz>> getGroupedQuizzesByCategory(String category) {
        Map<String, List<Quiz>> result = new LinkedHashMap<>();
        for (Quiz quiz : quizzesById.values()) {
            if (quiz.getCategory() == null || !quiz.getCategory().equalsIgnoreCase(category)) continue;
            String key = (quiz.getSubcategory() != null && !quiz.getSubcategory().isBlank())
                    ? quiz.getSubcategory() : quiz.getCategory();
            result.computeIfAbsent(key, k -> new ArrayList<>()).add(quiz);
        }
        return result;
    }

    @Override
    public List<Quiz> searchQuizzes(String query) {
        String q = query.toLowerCase().trim();
        if (q.isEmpty()) {
            return getAllQuizzes();
        }
        return quizzesById.values().stream()
                .filter(quiz ->
                        safe(quiz.getTitle()).toLowerCase().contains(q)
                                || safe(quiz.getCategory()).toLowerCase().contains(q)
                                || safe(quiz.getSubcategory()).toLowerCase().contains(q)
                                || safe(quiz.getDifficulty()).toLowerCase().contains(q))
                .collect(Collectors.toList());
    }
}
