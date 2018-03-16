# App-WatchDog-Android-App
App WatchDog is an android app built natively in Java using Android Studio. This app tracks the usage statistics of all installed apps (except system apps) and usage restriction can also be set on any app to control its usage.

## Getting Started

 Open the terminal:
 ```
 $ cd \<required-path>
 $ git clone https://github.com/aakashbansal/App-WatchDog-Android-App.git
 ```

After that open the directory from Android Studio. Go to Run -> Run app to launch the app into emulator or device.



## Overview

The app serves in two major ways:

### Usage Stats Tracker
It works as a tracker that tracks the app usage activity of a user by telling the following info about any given installed app(not system apps) :
* When was the app **Last Used**?
* How much time has the app spent in foreground in the **Current Hour** ?
* How much time has the app spent in foreground  on the **Current Day** ?
* How many times was the app launched in the **Current Hour** ?
* How many times was the app launched on the **Current Day** ?


### Usage Limit Controller

The app can also serve as **WatchDog** that checks to see if the user has exceeded the **Usage Limit Quota** on any app that he himself has set up. If the usage limit is exceeded, the concerned app exits(on which limit is set up).

Following type of restrictions can be set up on an app:
* Restriction on number of **Launches** allowed ( Per Hour / Per Day ).
* Restriction on  **Usage Time** allowed ( Per Hour / Per Day ).
* Restriction during **Specific Time Intervals** (App Access Not Allowed during specified Time Intervals).


## App WatchDog Demo

**App Screenshots for viewing Usage Stats**

<img src="SCREENSHOTS/ViewUsageStats.png" width="200"> <img src="SCREENSHOTS/SampleUsageStats1.png" width="200"> <img src="SCREENSHOTS/SampleUsageStats2.png" width="200">

**App Screenshots for Setting Usage Limit**

<img src="SCREENSHOTS/AddRestriction.png" width="200"> <img src="SCREENSHOTS/SelectApp.png" width="200"> <img src="SCREENSHOTS/SelectRestrictionType.png" width="200"> <img src="SCREENSHOTS/LaunchRestriction.png" width="200"> <img src="SCREENSHOTS/TimeRestriction.png" width="200"> <img src="SCREENSHOTS/SpecificTimeRestrcition.png" width="200"> <img src="SCREENSHOTS/ConfirmDialog.png" width="200">

**View Added Restriction Info**

<img src="SCREENSHOTS/ViewRestrictionTypes.png" width="200"> <img src="SCREENSHOTS/ViewTimeRestrictionInfo.png" width="200">

**Remove Restriction**

<img src="SCREENSHOTS/RemoveRestriction.png" width="200">

**Settings**

<img src="SCREENSHOTS/Settings.png" width="200">

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

**XML Resource Files**

All XML resource files exist in **res->layouts** directory in the code structure.
Further, different views of the app are all structured into different logical directories.
```
To take advantage of the restructured code formart of XML files, 
the project should be opened in "Project" view and not the "Android" view. This setting exist in 3rd or 4th row 
from upper left corner of ANdroid Studio Screen.
```
For more info about how to open the Android Project in **Project** View,  [see this](https://stackoverflow.com/questions/33817556/android-studio-android-project-view-is-missing)

## Working

At its core, the app has two services that constantly run in background :

* **AppLaunchDetectService** - This service finds the current running app in foreground and checks to see if it has exceeded its **Usage Limit Quota**. If yes, the app exits. If no, app continues to run.
* **UsageStatsUpdateService** - This service , as the name suggests, updates the **Usage Stats** (Last Used Time, No of Launches, Time In foreground ) of the given app.

These two services perform the core tasks of the whole app. Everything major that is happening in the app is being constantly performed by these services in one way or the another using various helper functions defined in /**helper** directory.


## Android Permissions Required

The app requires the **USAGE STATS PERMISSION** to work. Without it, nothing in the app is going to work.

```
In some devices (such as Xiaomi), "AUTOSTART" permission will also
be required to ensure smooth functioning of the app.
```


## Contributing

The goal of this project was to learn basic Android but it grew complex with time.
If you need any help or support to understand any part of the code,please let me know.
Any improvements, bug fixes,bug reporting, new features or suggestions are definitely welcome. Those looking to contribute to this code base can do so via Pull Requests. Any contribution is more than welcome and well appreciated. :)

## Contact Info

Email Id : aakashbansals23@gmail.com

## License

Licensed under the GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 : https://www.gnu.org/licenses/gpl-3.0.en.html

 Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 

