# App-WatchDog-Android-App
App WatchDog is an android app built natively in Java using Android Studio. This app tracks the usage statistics of all installed apps (except system apps) and usage restriction can also be set on any app to control its usage.

## Getting Started

 Open the terminal:
 ```
 $ cd \<required-path>
 $ git clone https://github.com/aakashbansal/App-WatchDog-Android-App.git
 ```

After that open the directory from Android Studio. Go to Run -> Run app to launch the app into emulator or device.

## Project Structure

**Java Files**
* activity- contains the files corresponding to different activities
* adapter - contains list view adapters
* database - contains files manipulating SQLite Database
* DialogController - manages the on screen dialogs
* fragments- contains codes for displaying fragments
* helper - contains various files each containing different functions responsible for handling the different business logic of the app codebase. Each type of role is abstracted into different java class.
* model - contains the classes responsible for modelling the data into an entity
* receiver - contains various broadcast receivers, each responsible for a different role like device turned on , service stopped, sending notification,etc.
* service- contains the Android services that run in background
* sharedPreferences- just a wrapper over native Android SharedPreferences API
* ToastController - just a wrapper over native Android ToastController with separate class contaiining all the Toast Mesaages.
