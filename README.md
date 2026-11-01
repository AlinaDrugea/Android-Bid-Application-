📱 Native Android Bidding Application
Native Android Bidding Application is an online auction and real-time bidding platform developed for Android devices. The project combines a native mobile frontend with a backend architecture (Spring Boot and PostgreSQL) to support user authentication, live item listings, and dynamic bidding functionality.

✨ Features
User Authentication: Secure registration and login system with user profiles.

Auction Feed: Browse active items available for bidding with details, starting prices, and images.

Real-time Bidding: Place bids dynamically and track the current highest bid on items.

Create Auctions: List new items for auction directly through the mobile interface.

Activity Dashboard: Monitor personal listings, active bids, and won auctions.

🛠️ Tech Stack
Platform: Android (Native)

Language: Java / Kotlin

Backend & Database: Spring Boot, REST APIs, PostgreSQL / SQLite

Development Tools: Android Studio, Gradle

📂 Project Structure
Plaintext
Native-Android-Bidding-Application/
│
├── app/                  # Main Android application module
│   ├── src/main/java     # Activities, fragments, adapters, and models
│   ├── src/main/res      # Layouts (XML), drawables, values, and navigation graphs
│   └── build.gradle      # App-level build configurations
│
├── gradle/               # Gradle wrapper files
├── build.gradle          # Project-level build configurations
└── settings.gradle       # Project settings
⚙️ Getting Started
Prerequisites
Android Studio (Koala, Iguana, or latest recommended version)

Android SDK (API level 24 or higher)

JDK 17 or compatible version

Installation & Setup
Clone the repository:

Bash
git clone https://github.com/LiviuGhilinta/Native-Android-Bidding-Application.git
Open the project:

Launch Android Studio and select Open an Existing Project.

Navigate to the cloned folder and select it.

Sync Gradle:

Allow Android Studio to download dependencies and sync the project files.

Run the application:

Connect a physical Android device with USB Debugging enabled or start an Android Virtual Device (AVD / Emulator).

Click the Run (' ▶ ') button in Android Studio.

💡 Usage
Launch the app and Register a new account or log in with your credentials.

Explore the home screen to view active items up for auction.

Tap on an item to check details and submit a higher bid.

Access the menu to list a new item or review your bidding history.
