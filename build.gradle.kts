// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

//ext {
//    set("appCompatVersion", "1.2.0")
//    set("constraintLayoutVersion", "2.0.2")
//    set("coreTestingVersion", "2.1.0")
//    set("coroutines", "1.3.9")
//    set("lifecycleVersion", "2.2.0")
//    set("materialVersion", "1.2.1")
//    // testing
//    set("junitVersion", "4.13.1")
//    set("espressoVersion", "3.1.0")
//    set("androidxJunitVersion", "1.1.2")
//}
//

val activityVersion by extra("1.4.0")
val appCompatVersion by extra("1.4.0")
val constraintLayoutVersion by extra("2.1.2")
val coreTestingVersion by extra("2.1.0")
val coroutines by extra("1.5.2")
val lifecycleVersion by extra("2.4.0")
val materialVersion by extra("1.11.0")
val junitVersion by extra("4.13.2")
val espressoVersion by extra("3.4.0")
val androidxJunitVersion by extra("1.1.3")