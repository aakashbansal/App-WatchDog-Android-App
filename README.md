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
from upper left corner of ANdroid Studio Screen
```


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

##License

GNU GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 

