# MineSweeper Java Edition

## Authors: Joseph Hable

### Created in Java 11.0.2 using JavaFX 11.0.1

This is my personal adaptation of the famous DOS game MineSweeper. This game is made entirely in the Java language and is meant to be a close imitation of Minesweeper for Windows XP. This project is fully open source and anyone may use the source files as long as I am credited in the final submission.

## Note

This application requires JRE or JDK 11 or higher to run. To get version 11 of the JRE, please go here and select the installer for your operating system:

https://adoptopenjdk.net/releases.html?variant=openjdk11&jvmVariant=hotspot

# Change log

## Version 1.0.1

Fixed a bug allowing users to infinitely enter their high score if they attempted to close out of the window first.

## Version 1.0.2

Fixed two bugs related to registering high scores. One prevented the current high scores from being shown if the user got a new high score that was not 1st place. The other fixed a bug that prevented users from changing which high scores were shown after they registered a new high score without restarting the application.

## Version 1.1.0

### New Features

#### Added Hotkey Functionality

Hotkeys have been added for the core functions of the game. They work simply by pressing the corresponding button while in the application. The hotkeys default to the specified hotkeys if the Hotkeys.ini file is missing from the user directory. The default hotkeys are; P for pause, R for restart, H opens the help menu, A opens the about menu, N starts a new game and S opens the high score dialogue. Added these hotkeys to a new "hotkeys" menu which can be found under help. Changing hotkeys through the GUI will be added soon. Hotkeys can be manually edited by changing the corresponding letter in the Hotkeys.ini file found in the main filesystem. You can set the same character to mutiple hotkeys which may cause undesired side effects. I will remove this later.

### Bug Fixes/Improvements

#### New Board Size Logic

Updated the logic for determining the size of squares on the board so strangely shaped boards occur less frequently.

#### Updated Score Modifiers

Changed the score modifiers for the intermediate and expert difficulties. As it stood getting a new high score in expert was very difficult and was based heavily on the seed RNG for that run. The new values are as follows:

Intermediate - Lose 4 score every second

Expert - Lose 1 score every 2 seconds rounded down (I.E. if you win in 501 seconds, you will lose 250 points as 250.5 will be rounded down.)

#### Fixed Pause Bugs

Pausing and restarting the game no longer randomly crashes or leaves the application in an unusable state.

Fix to prevent the user from playing the game in the paused state by choosing certain options.

Fixed the game crashing when finishing a game in the paused state under certain circumstances.

#### Fixed Bugs With Log Files

Fixed a null pointer exception when generating a log file caused by beginning generation, starting a new game after and finishing. This would be thrown upon attempting to close the writer as the writer never properly existed.

Fixed empty logs being generated by creating new games after selecting generate logs in the same fashion as described above. Although this bug would never crash it could potentially corrupt the high score files and break the application.

Fix for not being able to enter in high scores with the generate logs option selected after starting a new game before finishing one.

## Version 1.1.1

### New Features

#### Hotkey modification added to GUI

You can now change your desired hotkeys directly within the GUI by selecting the appropriate option from the hotkeys menu. In addition, you can reset the hotkeys to the default at any time by hitting the reset button.

If the hotkey file is corrupt or missing a new one will be created in the user directory automatically. This makes it so you don’t need to see an error telling you the file is missing every time you attempt to open the application.

### Bug Fixes

#### Fixed hotkey bugs

Changing hotkeys directly through the .ini file will now update when you complete an action in the window.

Pausing the game and resuming will no longer cause the hotkey for the pause button to disappear from its spot in the menu.

Added the pause hotkey to the resume button in the menu as well to avoid any confusion regarding using that hotkey multiple times.

Empty or invalid hotkeys will no longer show up as “(null)” in the menu unless manually edited by the user through the .ini file. A logical check for updating the hotkeys will be added with the next major code revision as it requires a full refactor of how hotkeys are loaded into the application.

## Version 1.3.0

Yes I know I skipped 1.2.0 I was just too lazy to update it here.

### Jar change

The jar file containing JavaFX libraries will no longer be updated on minor updates until the update is completed (I.E. 1.3.1 A). The information for minor updates will still be found in this readme. The source files will be updated for each minor change made.

### New Features

#### Launch Settings

Launch Settings added! You can now specify what settings the application loads with from the new launch settings window.

These settings can be found under a new file called Settings.cfg

### Improvements

Launch settings and hotkeys are now both found within the Settings.cfg file. This file also is now output in standard XML format for you nerds out there. This means that you can now delete the Hotkeys.ini file as it is no longer used by any subset of the application.

Updated the way that hotkey data is stored in the application so it will now only update if a change is made and saved rather than every time they were referenced.

Improved load times for different windows through optimized window handling.

Moved all image files to be located inside of the jar instead of in a separate folder. This means no more custom images can be loaded unless you compile the code yourself.

Changed the way the timer works by having it update every thousandth of a second so pausing and un-pausing can no longer be used to freeze the timer.

The version number has been added to the title of the main GUI.

Improved load times for the board through changing the way images are loaded.

Many places that threw exceptions now properly handle them or removed them all together.

Invalid settings or invalid hotkeys will not clear your saved settings for the other option.

Improved responsiveness of hotkey menus by having hotkeys pre-loaded.

Duplicate, null or blank hotkeys will no longer be accepted. Manually editing the file to create these will result in your hotkeys being reset.

Added option to enable resizing of the board due to pixel counts being incorrect on certain screen resolutions (will be creating a hard fix later and removing this option at that point.) This new option can be found under the "Game" menu.

Changed some text fields to just be text so the ugly highlighting is no longer there.

Changed the choice box in the high scores menu to a combo box which allows it to show the currently selected difficulty there as well as in the header.

The source code was finally commented properly! This means that you can finally understand what is going on inside of the application just by reading the comments.

### Bug Fixes

Restarting the game after winning or losing and opening a menu no longer places the game in an unplayable state until it is paused once.

The restart hotkey is no longer bound to the pause hotkey and vice versa.

Application no longer crashes when the Logs or Records folders do not exist.

## Version 1.3.1

### Improvements

Added a default hotkey to reset the settings file (hitting Ctrl + R will bring up a confirmation window to reset the settings then start a new game).

You can now view high scores even while playing a game with a custom board. The selection model will default to easy.

When entering new hotkeys you can once again leave fields blank to keep the ones that are currently saved.

Saving new hotkeys will now clear the associated field so attempting to edit them again without closing the window will not leave the hotkeys sitting there.

The option to resize the window is now enabled by default.

### Bug Fixes

Using a preset hash will no longer crash the application on startup when specified in launch settings.

The settings verifier now properly sets the ERROR_LEVEL to 0 before verifying the file so it no longer thinks it is invalid after failing on startup.

Using a preset hash and seed will no longer cause the board to be generated twice causing the board to overflow downward and the game to become unplayable.

Loading a game using the set seed parameter will no longer disable the restart function.

## Version 1.4.0

### New Features

#### Log File Playback

You can now playback all log files generated from version 1.4.0 and onward! A new playback menu 
has been added where you can select a log file, play it back, change the audoplay frequency, 
change the selected log, and even play back a file without showing flags on the board. Each of 
the functions details are as follows:

##### Playback Log File

Prompts the user to open a valid log file and reads in the data. If the log is invalid, an alert 
will be shown and no board will be drawn. Hitting cancel or closing out of the file select window 
will cancel the playback loading and resume the game in progress. Note that this function will 
not pause the current game so the time will continue to run.
 
##### Select New Log

This command only works once the user is in Playback mode (as denoted by the UI layout.) 
Attempting to select this option while not in playback mode will simply do nothing (the event is 
consumed.) If the user is in playback mode, it will prompt them to select a valid log file as 
before. It will then clear the board and generate a new playback. If the file is invalid it will 
still clear the board but will not draw a new one.

##### Set Autoplay Frequency

This command can be invoked at any time but will not have any effect on a currently running 
autoplay. In order for it to have effect the autoplay must be stopped and then restarted. This 
command opens up a window which prompts the user to enter a delay for moves in autoplay mode. The
lowest usable value is 0.001 and the highest is unbound. Invalid inputs will simply be ignored. 

##### Display flags in playback 

This command can be invoked at any time. If a playback is currently running it will prompt the 
user to restart the playback with this setting as it cannot be applied retroactively. If this 
setting is unchecked, the game will not display flagged tiles while playing back a file. Rather, 
these tiles will remain in the unclicked state, giving the board a more filled appearance.

#### Change to log file layout

Log files are now generated in XML format. This means that all old log files are no longer going 
to be usable. In addition, there was an issue with old logs where the x and y coordinates were 
backward for selecting tiles. This cannot be easily fixed and so logs generated before 1.4.0 will
not be able to be used. These files also now have their own extension. *.msl

### Improvements

Added new exceptions that are more specific than the ones that already existed.

Fixed the constant x and y coordinate mismatching in the board and tiles classes.

Updated board generation logic (again) to try to make them load faster.

### Bug Fixes

Application will no longer reset the settings and hotkeys when moving from one version to another. Note that the movement is **ONE WAY** in that you cannot use the same settings.cfg file in version 1.3.2 if you already loaded that file in 1.4.0.

You can no longer place more flags than there are mines present on the board

## Version 1.4.1 Alpha

### Bug Fixes

Tiles will now be flagged properly when the "Show Flags in Playback" option is not selected. This means that if you clicked a flagged tile in a game it will no longer be revealed in the playback (it will just appear blank).

Flagged tiles will no longer be overridden when they are clicked in the playback (if you flagged a tile and then clicked the tile accidentally during the game, the flag would be overridden by whatever was under the tile in the playback).