apply(from = "../dsl/android-library.gradle")
apply(from = "../dsl/ktlint.gradle")
apply(from = "../dsl/bintray.gradle")

dependencies {
    "compileOnly"(project(":armyknife-jetpack-dependencies"))
    "testImplementation"(project(":armyknife-jetpack-dependencies"))
    "androidTestImplementation"(project(":armyknife-jetpack-dependencies"))

    "api"("com.eaglesakura.armyknife.armyknife-runtime:armyknife-runtime:1.3.4")

    "androidTestImplementation"("androidx.enterprise:enterprise-feedback-testing:1.0.0-rc01")
    "testImplementation"("androidx.enterprise:enterprise-feedback-testing:1.0.0-rc01")
//    "kapt"("androidx.lifecycle:lifecycle-compiler:2.0.0")
    "testImplementation"("androidx.arch.core:core-testing:2.1.0")
    "testImplementation"("androidx.paging:paging-common:2.1.0")
    "androidTestImplementation"("androidx.paging:paging-common:2.1.0")
//    "kapt"("androidx.room:room-compiler:2.1.0")
    "kaptTest"("com.google.auto.service:auto-service:1.0-rc4")
    "kaptAndroidTest"("com.google.auto.service:auto-service:1.0-rc4")
    "testImplementation"("androidx.room:room-testing:2.2.1")
    "androidTestImplementation"("androidx.work:work-testing:2.2.0")
}
