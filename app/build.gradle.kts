plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // Navigation and KSP Plugins mentioned below
    id("androidx.navigation.safeargs")
    id("com.google.devtools.ksp")

    // Testing
    id("de.mannodermaus.android-junit5") version "1.10.0.0"

    // Test Coverage
    id("jacoco")
}

jacoco {
    toolVersion = "0.8.10"
}

android {
    namespace = "com.example.notepad"
    compileSdk = 34

    packaging {
        resources.excludes.add("META-INF/*")
    }

    defaultConfig {
        applicationId = "com.example.notepad"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            testCoverage {
                isDebuggable = true
                enableUnitTestCoverage = true
                enableAndroidTestCoverage = true
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
    (this as? Test)?.configure<ExtensionContainer> {
        // Define which tasks should be covered by JaCoCo
        extensions.getByType(JacocoTaskExtension::class).apply {
            isIncludeNoLocationClasses = true
        }
    }

    // Enabling Data Binding
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Room Database
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.test.espresso:espresso-contrib:3.5.1")
    ksp("androidx.room:room-compiler:2.6.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // MVVM i.e for ViewModel and Live Data
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.activity:activity-ktx:1.8.0")

    // Jacoco Test Coverage
    implementation("org.jacoco:org.jacoco.core:0.8.10")

    // Android Test Dependency

    // androidx.test
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")

    // Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:3.5.1")

    // Others
    androidTestImplementation("androidx.room:room-testing:2.6.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.4")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // JUnit5
    androidTestImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")

//    // fragment test
//    androidTestImplementation("androidx.fragment:fragment-testing:1.6.2")

    // fragment
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // JUnit Dependency
    testImplementation("androidx.navigation:navigation-testing:2.7.4")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testImplementation("androidx.test:core-ktx:1.5.0")
    testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    debugImplementation("androidx.fragment:fragment-testing:1.6.2")
}
tasks.withType<Test> {
    useJUnitPlatform()
    maxParallelForks = 1
}

tasks.register<JacocoReport>("jacocoTestReport")  {
    dependsOn("testDebugUnitTest", "createDebugCoverageReport")
    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(file("$buildDir/reports/jacoco/html"))
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*"
    )
    val debugTree = fileTree(mapOf("dir" to "${project.buildDir}/intermediates/classes/debug", "excludes" to fileFilter))
    val mainSrc = "${project.projectDir}/src/main/java"
    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(fileTree(mapOf("dir" to buildDir, "includes" to listOf(
        "jacoco/testDebugUnitTest.exec",
        "outputs/code-coverage/connected/*coverage.ec"
    ))))
}