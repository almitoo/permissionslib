# PermissionsLib

`PermissionsLib` is an Android library designed to simplify the management of runtime permissions. It provides an easy-to-use interface for checking, requesting, and handling permissions in your Android applications.

## Features

- **Singleton Instance**: `PermissionsManager` follows the Singleton design pattern, ensuring a single instance is used throughout the application.
- **Permission Checks**: Easily check if your app has the necessary permissions.
- **Request Permissions**: Request multiple permissions at once, with built-in handling for denied requests.
- **Declared Permissions Retrieval**: Retrieve all permissions declared in the app's manifest.
- **Rationale Dialog**: Display a rationale dialog if a user denies a permission request, with an option to redirect them to the app's settings.
- **Listener Support**: Register and unregister listeners to get notified whenever permission states change.

## CheckPermissions App

This repository includes an example application that demonstrates how to use `PermissionsLib` in a real-world scenario.
The app checks for various permissions and displays their status using a RecyclerView with a card-based UI.

### How It Works

The example app initializes the `PermissionsManager` singleton in `MainActivity`.
It retrieves all declared permissions and checks their status.
The permissions are displayed as a list with icons, and the user can click on each permission to request it if not already granted.
 The app automatically updates the permission status after the user responds to the permission requests.


## Installation

To use `PermissionsLib` in your Android project, add the following dependency to your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.example:permissionslib:1.0.0'
}

