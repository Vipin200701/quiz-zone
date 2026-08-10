# 🎯 Quiz Zone

Quiz Zone is a free, no-login quiz platform designed for learning, practice, and testing your knowledge.

It includes quizzes across multiple categories such as:

- 💻 Developer / IT
- 🌎 General Knowledge
- 🧮 Mathematics
- 🔬 Science
- 📊 General Aptitude
- 📝 Exam Preparation
- 💼 Interview Preparation

New topics, difficulty levels, and questions can be added over time.

---

## ✨ Features

- 🆓 Completely free to use
- 🔐 No login or account required
- 📚 Multiple quiz categories
- 🎯 Easy, Medium, and Hard difficulty levels
- ⚡ Simple and fast interface
- 📱 Responsive design
- 📄 JSON-based quiz system
- 🚀 Easy to add new quizzes
- 🗄️ No database required

---

## 🛠️ Technology Stack

### Backend
- Java
- Spring Boot
- Maven

### Frontend
- Thymeleaf
- HTML
- CSS
- Bootstrap
- JavaScript

### Data
- JSON files

### Deployment
- Docker
- Render

---

## 📂 How Quiz Data Works

Quiz Zone does not use a database.

Each quiz is stored in a JSON file. This makes it very easy to add or remove quizzes.

For example:

```text
src/main/resources/
└── quizzes/
    ├── developer/
    │   ├── java/
    │   │   ├── easy.json
    │   │   ├── medium.json
    │   │   └── hard.json
    │   │
    │   └── spring-boot/
    │       ├── easy.json
    │       ├── medium.json
    │       └── hard.json
    │
    ├── maths/
    │   ├── easy.json
    │   ├── medium.json
    │   └── hard.json
    │
    └── general-knowledge/
        ├── easy.json
        ├── medium.json
        └── hard.json
````

To add a new quiz, simply add a new JSON file in the appropriate folder.

No database changes are required.

---

# 💻 Run Quiz Zone on Your Computer

You can download this project and run it locally on your computer.

## Requirements

Before running the project, install:

* Java 21 or later
* Maven (optional if using the included Maven configuration)
* Git (optional)

You can also run the project using an IDE such as IntelliJ IDEA or VS Code.

---

## 📥 Download the Project

Go to the GitHub repository:

**Quiz Zone**

[https://github.com/Vipin200701/quiz-zone](https://github.com/Vipin200701/quiz-zone)

Click:

**Code → Download ZIP**

Extract the downloaded ZIP file on your computer.

---

## ▶️ Run Using IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Select **Open**.
3. Open the `quiz-app-main` folder.
4. Allow IntelliJ to import the Maven project.
5. Make sure Java 21 is selected.
6. Find the main Spring Boot application class.
7. Click the ▶️ **Run** button.

The application will start locally.

Open your browser and visit:

```text
http://localhost:8080
```

---

## ▶️ Run Using Terminal

Open Terminal inside the `quiz-app-main` folder.

### macOS / Linux

```bash
./mvnw spring-boot:run
```

If the Maven wrapper is not available, use:

```bash
mvn spring-boot:run
```

### Windows

```bash
mvnw.cmd spring-boot:run
```

Then open:

```text
http://localhost:8080
```

---

# 🐳 Run Using Docker

Quiz Zone also includes Docker support.

Make sure Docker Desktop is installed and running.

From the `quiz-app-main` directory:

```bash
docker build -t quiz-zone .
```

Then run:

```bash
docker run -p 8080:8080 quiz-zone
```

Open:

```text
http://localhost:8080
```

---

# ➕ Adding a New Quiz

Adding a new quiz is simple.

Create a JSON file following the existing quiz structure.

Example:

```json
{
  "title": "Java Basics - Easy",
  "category": "Developer",
  "difficulty": "Easy",
  "questions": [
    {
      "questionText": "Which keyword is used to inherit a class in Java?",
      "option1": "implements",
      "option2": "extends",
      "option3": "inherits",
      "option4": "super",
      "answer": "extends"
    }
  ]
}
```

Save the file in the appropriate quiz directory.

The application will automatically discover the new quiz.

---

# 🚀 Live Website

Quiz Zone is also available online.

Visit:

**https://quiz-zone-ewnv.onrender.com/about**

---

# 👨‍💻 About the Developer

Quiz Zone was created by **Vipin Kumar**, a Java Developer working at **Infosys**.

Vipin works primarily with:

* Java
* Spring Boot
* REST APIs
* Microservices
* PostgreSQL
* Docker
* Backend Development

He enjoys building practical applications, solving programming problems, and continuously learning new technologies.

Quiz Zone was created as a personal project to combine software development with learning and knowledge sharing.

---

# 🎯 Project Goal

The goal of Quiz Zone is to provide a simple platform where anyone can:

* Learn new concepts
* Practice questions
* Prepare for interviews
* Prepare for exams
* Test their general knowledge
* Improve their technical skills

No account. No complicated setup. Just choose a topic and start the quiz.

---

## 📄 License

This project is available for learning and personal use.

---

⭐ If you find Quiz Zone useful, consider giving the repository a star!

````

### One thing you should change

Replace:

```markdown
**https://quiz-zone-ewnv.onrender.com/about**
````

with your actual Render URL, for example:

```markdown
https://quiz-zone-xxxx.onrender.com
```


