package com.vipin.QuizeApp.controller;

import com.vipin.QuizeApp.model.CategoryInfo;
import com.vipin.QuizeApp.model.Question;
import com.vipin.QuizeApp.model.Quiz;
import com.vipin.QuizeApp.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class QuizeController {

    private final QuizService quizService;

    public QuizeController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<CategoryInfo> categories = new ArrayList<>();
        for (String category : quizService.getCategories()) {
            int count = (int) quizService.getAllQuizzes().stream()
                    .filter(q -> q.getCategory().equalsIgnoreCase(category))
                    .count();
            categories.add(new CategoryInfo(category, iconFor(category), count));
        }
        model.addAttribute("categories", categories);
        model.addAttribute("totalQuizzes", quizService.getAllQuizzes().size());
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // Browse quizzes: optionally filtered by category, or by a search query
    @GetMapping("/quiz/join")
    public String joinQuizPage(@RequestParam(required = false) String category,
                                @RequestParam(required = false) String q,
                                Model model) {

        if (q != null && !q.isBlank()) {
            model.addAttribute("searchResults", quizService.searchQuizzes(q));
            model.addAttribute("query", q);
        } else if (category != null && !category.isBlank()) {
            model.addAttribute("groupedQuizzes", quizService.getGroupedQuizzesByCategory(category));
            model.addAttribute("selectedCategory", category);
        } else {
            model.addAttribute("groupedQuizzes", quizService.getGroupedQuizzes());
        }

        model.addAttribute("allCategories", quizService.getCategories());
        return "join_quiz";
    }

    @GetMapping("/quiz/start/{id}")
    public String startQuiz(@PathVariable String id, Model model) {
        Quiz quiz;
        try {
            quiz = quizService.getQuizById(id);
        } catch (RuntimeException e) {
            model.addAttribute("error", "Quiz not found");
            return "quiz_error";
        }

        if (quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
            model.addAttribute("error", "Quiz has no questions");
            return "quiz_error";
        }

        int totalQuestions = quiz.getQuestions().size();
        int totalTimeInMinutes = totalQuestions;

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", quiz.getQuestions());
        model.addAttribute("totalTime", totalTimeInMinutes * 60);

        return "start_quiz";
    }

    @PostMapping("/quiz/submit")
    public String submitQuiz(
            @RequestParam String quizId,
            @RequestParam Map<String, String> answers,
            Model model) {

        Quiz quiz = quizService.getQuizById(quizId);

        int score = 0;
        int total = quiz.getQuestions().size();

        for (Question q : quiz.getQuestions()) {
            String userAnswer = answers.get("answers[" + q.getId() + "]");
            if (userAnswer != null && userAnswer.equals(q.getAnswer())) {
                score++;
            }
        }

        model.addAttribute("score", score);
        model.addAttribute("total", total);
        model.addAttribute("quizTitle", quiz.getTitle());
        return "quiz_result";
    }

    private String iconFor(String category) {
        String c = category.toLowerCase();
        if (c.contains("developer") || c.contains(" it") || c.equals("it")) return "bi-laptop";
        if (c.contains("general knowledge") || c.contains("gk")) return "bi-globe-americas";
        if (c.contains("math")) return "bi-calculator";
        if (c.contains("science")) return "bi-flask";
        if (c.contains("aptitude")) return "bi-lightbulb";
        if (c.contains("exam")) return "bi-mortarboard";
        if (c.contains("interview")) return "bi-briefcase";
        return "bi-question-circle";
    }
}
