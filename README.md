# DevTwin Platform 🚀

AI-Powered Developer Intelligence & Career Analytics Platform

DevTwin Platform is a modern microservices-based application that analyzes developer profiles, GitHub activity, repository quality, and technical growth using AI-powered insights.

The platform helps developers:
- Analyze coding habits
- Measure technical strengths
- Identify missing skills
- Generate learning roadmaps
- Improve interview readiness
- Track engineering growth

---

# ✨ Features

## 🔐 Authentication & Security
- JWT Authentication
- Role-Based Authorization
- BCrypt Password Encryption
- Secure REST APIs
- Spring Security Integration

---

## 📊 GitHub Intelligence Engine
- GitHub Profile Analysis
- Repository Quality Scoring
- Commit Activity Tracking
- Language Usage Analytics
- Contribution Insights

---

## 🤖 AI-Powered Features
- AI Career Roadmap Generator
- Skill Gap Detection
- Interview Readiness Analysis
- Repository Improvement Suggestions
- AI Developer Mentor

---

## 📈 Analytics Dashboard
- Skill Heatmaps
- Contribution Graphs
- Developer Growth Tracking
- Tech Stack Insights
- Performance Analytics

---

## ⚡ System Design Features
- Microservices Architecture
- API Gateway
- Event-Driven Communication
- Redis Caching
- Kafka Messaging
- Dockerized Deployment

---

# 🏗️ Architecture

```text
Frontend (Angular)
        |
API Gateway
        |
------------------------------------------------
|              |             |                 |
Auth Service   AI Service    Analytics Service
|              |             |                 |
MySQL          Redis         Kafka
```

---

# 🛠️ Tech Stack

## Backend
- Java 17
- Spring Boot 3
- Spring Security
- Spring Cloud Gateway
- Spring Data JPA
- Hibernate
- REST APIs

## Database
- MySQL
- Redis

## Messaging
- Apache Kafka

## Frontend
- Angular 18
- Tailwind CSS
- Chart.js

## DevOps
- Docker
- GitHub Actions

## AI
- Gemini API

---

# 📂 Project Structure

```text
devtwin-platform/

backend/
    auth-service/
    gateway-service/
    ai-service/
    analytics-service/

frontend/
    angular-app/

docs/
docker/
k8s/
```

---

# 🚀 Getting Started

## Clone Repository

```bash
git clone https://github.com/Uday-Saraswat/devtwin-platform.git
```

---

## Backend Setup

### Navigate to auth service

```bash
cd backend/auth-service
```

### Run application

```bash
mvn spring-boot:run
```

---

# ⚙️ Environment Variables

Create `application.properties`

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=86400000
```

---

# 📌 Current Progress

- [x] Project Setup
- [x] MySQL Configuration
- [x] User Registration API
- [x] Spring Security Setup
- [ ] JWT Authentication
- [ ] GitHub Analysis Engine
- [ ] AI Integration
- [ ] Docker Setup
- [ ] Kafka Integration
- [ ] Angular Dashboard

---

# 🔥 Future Enhancements

- AI Resume Analyzer
- Open Source Readiness Score
- Live Coding Analytics
- Developer Personality Insights
- Kubernetes Deployment
- AI Mock Interview Engine

---

# 🧪 API Endpoints

## Register User

```http
POST /auth/register
```

### Request

```json
{
  "name": "Uday",
  "email": "uday@gmail.com",
  "password": "123456"
}
```

### Response

```json
{
  "success": true,
  "message": "User Registered Successfully",
  "data": {
    "id": 1,
    "name": "Uday",
    "email": "uday@gmail.com",
    "role": "USER"
  }
}
```

---

# 📸 Screenshots

Add screenshots here later.

Example:
- Login Page
- Dashboard
- Analytics Charts
- AI Insights

---

# 📖 Learning Goals

This project is focused on mastering:

- Microservices Architecture
- Spring Security
- JWT Authentication
- Event-Driven Systems
- Distributed Systems
- Docker & DevOps
- AI Integration
- Production-Level Backend Engineering

---

# 👨‍💻 Author

Uday Saraswat

- GitHub: https://github.com/Uday-Saraswat

---

# ⭐ Support

If you found this project useful, consider giving it a star ⭐
