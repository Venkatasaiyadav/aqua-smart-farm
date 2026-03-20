# 🦐 AquaFarm Pro – Smart Prawn Farm Management System

## 📌 Overview

AquaFarm Pro is a smart aquaculture management system designed to help prawn farmers efficiently manage pond operations, track growth, monitor feed, analyze expenses, and make data-driven decisions using AI.

---

## 🚀 Features

* 👤 User Authentication (JWT-based)
* 🏞️ Pond Management
* 🌾 Feed Tracking & Recommendations
* 📈 Growth Monitoring (ABW, Biomass)
* 💰 Expense Management
* 📊 Dashboard & Analytics
* 🤖 AI Assistant for farm guidance
* 🔔 Notifications & Alerts

---

## 🧠 Key Concepts

* **ABW (Average Body Weight)** – Average weight of prawns
* **FCR (Feed Conversion Ratio)** – Feed efficiency
* **Biomass** – Total weight of prawns in pond

---

## 🏗️ Tech Stack

### Frontend

* React + Vite
* Tailwind CSS
* Chart.js / Recharts

### Backend

* Spring Boot (REST APIs)
* JWT Authentication

### Database

* MySQL

### Deployment

* AWS / Render / Vercel

---

## 📁 Project Structure

```
aqua-ai-platform/
 ├── backend/     # Spring Boot APIs
 ├── frontend/    # React App
 ├── README.md
 └── .gitignore
```

---

## 🔗 API Endpoints

### Public APIs

* `GET /api/auth/health`
* `POST /api/auth/register`
* `POST /api/auth/login`

### Protected APIs

* `GET /api/dashboard`
* `POST /api/ponds`
* `GET /api/ponds`
* `POST /api/feed`
* `POST /api/growth`
* `POST /api/expenses`

---

## 📅 Workflows

### Daily

* Check dashboard
* Add feed entry
* Monitor alerts

### Weekly

* Record growth samples
* Analyze trends

### Monthly

* Review expenses
* Analyze farm performance

---

## 🔐 Security

* JWT-based authentication
* Password hashing
* Secure API access

---

## 🌍 Supported Languages

* English
* Telugu
* Hindi

---

## 📈 Future Enhancements

* IoT pond sensors
* Water quality monitoring
* Weather integration
* AI disease detection
* Marketplace for selling prawns

---

## 🎯 Goal

To build a scalable, AI-powered platform that improves productivity, profitability, and decision-making in prawn farming.

---

## 👨‍💻 Author

**Venkatasai Udatha**

---
