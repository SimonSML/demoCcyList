# demoCcyList

For error:
### Installed Build Tools revision 32.0.0 is corrupted
### Installed Build Tools revision 32.0.0 is corrupted. Remove and install again using the SDK Manager

If re-install can not solve, you can try:
1. Go to the SDK folder ( you can check it in Android Studio -> File -> Project Structure -> SDK location)
2. From SDK folder -> build-tools -> 32.0.0 -> rename the file d8 to dx, then go to lib folder, rename d8.jar to dx.jar

Or
Go to build.gradle(app), update the following lines:

compileSdkVersion 30
buildToolsVersion '30.0.0'
targetSdkVersion 30
