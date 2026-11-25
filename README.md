# My Weather App (New York Weather)

An Android application that polls the free [OpenWeatherMap API](https://openweathermap.org/api) for current weather data in JSON format, parses it into local model objects using Retrofit & Moshi, and displays textual descriptions along with dynamic weather icons loaded via Glide.

---

## 📱 Features

- **Live Weather Updates**: Displays the current weather for **New York** (City name, main weather condition, and detailed description).
- **Dynamic Weather Icons**: Fetches and renders official OpenWeatherMap condition icons dynamically using **Glide**.
- **Error & Edge Case Handling**:
  - Catches network connection failures (`IOException`).
  - Handles HTTP status codes (e.g. `401 Unauthorized` for missing/invalid API keys, `404 Not Found`).
  - Provides an interactive **Retry** mechanism and an API key input field.

---

## 🛠 Tech Stack & Libraries

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) & Material 3
- **Networking**: [Retrofit 2](https://square.github.io/retrofit/)
- **JSON Serialization**: [Moshi](https://github.com/square/moshi) (with Kotlin Reflection Adapter)
- **Image Loading**: [Glide](https://github.com/bumptech/glide)
- **Architecture**: MVVM (`ViewModel`, `StateFlow`, `WeatherUiState`)
- **Concurrency**: Kotlin Coroutines

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio** (Ladybug / 2024.2.1 or newer recommended)
- **Android SDK**: `minSdk 24`, `targetSdk 37`
- **JDK**: Java 11 or higher

### Installation & Running

1. **Clone the repository**:
   ```bash
   git clone https://github.com/johannaa12/MyWeatherApp.git
   cd MyWeatherApp
   ```

2. **Open in Android Studio**:
   - Open Android Studio and select **Open**, then navigate to the cloned `MyWeatherApp` folder.
   - Allow Gradle sync to complete.

3. **Run the App**:
   - Select your emulator or connected physical Android device.
   - Click **Run** (`Shift + F10`).

---

## 🧪 Testing

Run unit tests locally via Gradle:
```bash
./gradlew test
```
