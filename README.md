Markdown
# 🛒 Native Android Bidding Application

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Language">
  <img src="https://img.shields.io/badge/Backend-Spring%2520Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Database-PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge" alt="License">
</p>

A feature-rich, native Android application designed for real-time online auctions and bidding. Built with **Java** and **Android Studio**, this mobile client connects seamlessly with a secure backend REST API to allow users to list items, place bids, manage auctions, and track real-time bidding updates.

---

## ✨ Features

- **User Authentication & Profiles:** Secure registration, login, and user profile management.
- **Product Listing & Management:** Users can post items for auction with descriptions, starting prices, and images.
- **Live Bidding System:** Place real-time bids on active items with instant validation and status updates.
- **Search & Filter:** Easily browse products by categories, status (active/closed), or keywords.
- **RESTful API Integration:** Communicates with a robust backend service using HTTP requests.

---

## 🛠️ Tech Stack

### Mobile Frontend (Client)
- **Language:** Java
- **IDE:** Android Studio
- **Architecture & Networking:** REST APIs, JSON parsing, asynchronous background tasks

### Backend & Database (Server)
- **Framework:** Spring Boot (Java)
- **Database:** PostgreSQL
- **Communication:** RESTful web services

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio** (Koala or newer recommended)
- **JDK 17** or higher
- An Android Emulator or a physical Android device (min SDK 24+).

### Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/LiviuGhilinta/Native-Android-Bidding-Application.git](https://github.com/LiviuGhilinta/Native-Android-Bidding-Application.git)
Open in Android Studio:

Launch Android Studio.

Select Open and choose the cloned project folder.

Configure API Endpoint:

Locate the network configuration or API constants file in the project (e.g., ApiClient.java or ApiService.java).

Update the base URL to point to your running backend server (e.g., http://10.0.2.2:8080/ for local development on an emulator).

Build and Run:

Sync project with Gradle files (File > Sync Project with Gradle Files).

Click Run 'app'` (Shift + F10) to launch the app on your emulator or connected device.

📂 Project Structure

app/
├── src/
│   ├── main/
│   │   ├── java/com/example/biddingapp/  # Source code (Activities, Adapters, Models, API Client)
│   │   ├── res/                          # Layouts, drawables, values, and manifests
│   │   └── AndroidManifest.xml           # App configuration and permissions
│   └── build.gradle                      # App-level build configurations
└── build.gradle                          # Project-level build configurations
