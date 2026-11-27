# Recipe Book & Weather App

An Android application containing **Lab Activity 4.1: Recipe Book** with `RecyclerView`, item grouping by flavor, `AlertDialog` detail inspection, swipe-to-delete (`ItemTouchHelper`), and recipe addition forms.

---

## 📱 Features (Lab Activity 4.1 - Recipe Book)

- **RecyclerView with Grouped Headers**: Displays recipes categorized under **Savory** and **Sweet** flavor headers.
- **Add Sweet or Savory Recipes**: Input fields for Recipe Title and Recipe Description, with buttons to dynamically append recipes under their respective flavor section (`ADD SAVORY`, `ADD SWEET`).
- **Form Reset**: Automatically clears the input fields after a recipe is added.
- **Recipe Description Dialog**: Clicking any recipe item pops up an `AlertDialog` displaying its title and detailed description.
- **Swipe-to-Delete (`ItemTouchHelper`)**: Swipe any recipe left or right to remove it from the app (flavor headers are preserved and locked from swiping).

---

## 🛠 Tech Stack & Libraries

- **Language**: Kotlin
- **UI Framework**: Android XML Views, `RecyclerView`, `ConstraintLayout`, `MaterialComponents`
- **Gestures**: `ItemTouchHelper` for swipe actions
- **Dialogs**: `AlertDialog` for recipe details
- **Testing**: JUnit 4

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
