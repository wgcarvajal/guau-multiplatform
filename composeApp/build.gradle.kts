plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.sqldelight)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    //jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.components.resources)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kamel.image)
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.transitions)
                implementation(libs.voyager.tabNavigator)
                api(libs.icerock.mvvm.core)
                api(libs.icerock.mvvm.compose)
                api(libs.precompose)
                api(libs.datastore.preferences.core)
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.compose.ui)
                implementation(libs.koin.android)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.ktor.client.android)
                implementation(libs.sqldelight.android)
            }
        }


        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.sqldelight.native)
            }
        }

        /*val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.ktor.client.apache5)
                implementation(libs.sqldelight.sqlite)
            }
        }*/
    }
}


android {
    namespace = "com.carpisoft.guau"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.carpisoft.guau"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        implementation(libs.play.services.auth)
    }
}

/*compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.carpisoft.guau"
            packageVersion = "1.0.0"
        }
    }
}*/

sqldelight {
    databases {
        create("GuauDB") {
            packageName.set("com.carpisoft.guau.core.database.data")
        }
    }
}

