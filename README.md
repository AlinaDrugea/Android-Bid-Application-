# 📱 Native Android Bidding Application

An online auction and real-time bidding platform for Android devices. The project combines a native mobile frontend with a backend built on Spring Boot and PostgreSQL, supporting user authentication, live item listings, and dynamic bidding.

## ✨ Features

- **User Authentication:** Secure registration and login with user profiles.
- **Auction Feed:** Browse active items with details, starting prices, and images.
- **Real-time Bidding:** Place bids and track the current highest bid on each item.
- **Create Auctions:** List new items for auction directly from the mobile app.
- **Activity Dashboard:** Monitor your listings, active bids, and won auctions.

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Platform | Android (native) |
| Language | <Java / Kotlin> |
| Backend | Spring Boot, REST APIs |
| Database | PostgreSQL |
| Tools | Android Studio, Gradle (Kotlin DSL) |

## 📂 Project Structure

```
Native-Android-Bidding-Application/
├── app/                    # Main Android application module
│   ├── src/main/java       # Activities, fragments, adapters, and models
│   ├── src/main/res        # Layouts (XML), drawables, values, navigation graphs
│   └── build.gradle.kts    # App-level build configuration
├── gradle/                 # Gradle wrapper files
├── build.gradle.kts        # Project-level build configuration
├── settings.gradle.kts     # Project settings
├── gradle.properties
└── gradlew / gradlew.bat
```

## ⚙️ Getting Started

### Prerequisites

- Android Studio (latest stable version recommended)
- Android SDK, API level 24 or higher
- JDK 17 or compatible
- A running instance of the backend <link to the backend repo or setup instructions>

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/LiviuGhilinta/Native-Android-Bidding-Application.git
   ```
2. Open the project in Android Studio (**File → Open**) and select the cloned folder.
3. Let Gradle sync and download the dependencies.
4. Set the backend URL: <e.g. `BASE_URL` in `<file>`; use `http://10.0.2.2:8080` for the emulator>.
5. Connect a physical device with USB debugging enabled, or start an emulator (AVD), then click **Run ▶**.

## 💡 Usage

1. Launch the app and register a new account, or log in.
2. Browse the home screen to see active auctions.
3. Tap an item to view its details and place a higher bid.
4. Open the menu to list a new item or review your bidding history.




## 📄 License

<Add a license or remove this section>
