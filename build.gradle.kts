// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

    // Plugin for UI Navigation
    id("androidx.navigation.safeargs") version "2.7.4" apply false

    // Plugin for ksp annotation processor
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false

    // Tests
    id("de.mannodermaus.android-junit5") version "1.10.0.0" apply false

    id("jacoco")
}
