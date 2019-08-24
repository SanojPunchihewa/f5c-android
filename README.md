# f5c-android
[![Build Status](https://travis-ci.com/SanojPunchihewa/f5c-android.svg?branch=master)](https://travis-ci.com/SanojPunchihewa/f5c-android)

An optimised re-construction of the call-methylation module in [f5c](https://github.com/hasindu2008/f5c) for ARM-based mobile computing platforms.

## How to Build

### Step 1 : Build the [f5c](https://github.com/hasindu2008/f5c) library
Please follow the these [instructions](https://github.com/hiruna72/f5c/tree/add_CMake#building-using-cmake) to build f5c in the desired android device.

### Step 2 : Copy the generated libf5cshared.so to jniLibs directory.

Once you have completed the build, you will find `libf5cshared.so` inside `f5c/build-android/src/` directory. You need to copy this library to `f5c-android/app/src/main/jniLibs/{arch-type}` directory.

**Note** `arch-type` is the architecture of the android device you used in **step 1**. Most common arch-types are `arm64-v8a` and `armeabi-v7a`
