apply(from = "../dsl/android-library.gradle")
apply(from = "../dsl/ktlint.gradle")
apply(from = "../dsl/bintray.gradle")

/**
 * Jetpack Libraries.
 *      https://developer.android.com/jetpack/androidx/versions
 *
 * RxJava Libraries
 *      https://github.com/ReactiveX/RxJava
 *      https://github.com/ReactiveX/RxAndroid
 *      https://github.com/ReactiveX/RxKotlin
 */
dependencies {

    /**
     * Core architexture
     */
    "api"("io.reactivex.rxjava2:rxkotlin:2.4.0")  // Reactive Extension
    "api"("io.reactivex.rxjava2:rxandroid:2.1.1")   // Reactive Extension
    "api"("com.annimon:stream:1.2.1")               // RxKotlin with Nullable value.
    "api"("androidx.activity:activity:1.1.0")
    "api"("androidx.activity:activity-ktx:1.1.0")
    "api"("androidx.annotation:annotation:1.1.0")
    "api"("androidx.appcompat:appcompat:1.1.0")
    "api"("androidx.appcompat:appcompat-resources:1.1.0")
    "api"("androidx.arch.core:core-common:2.1.0")
    "api"("androidx.arch.core:core-runtime:2.1.0")
    "api"("androidx.collection:collection:1.1.0")
    "api"("androidx.collection:collection-ktx:1.1.0")
    "api"("androidx.core:core:1.3.0")
    "api"("androidx.core:core-ktx:1.3.0")
    "api"("androidx.fragment:fragment:1.2.4")
    "api"("androidx.fragment:fragment-ktx:1.2.4")
    "api"("androidx.lifecycle:lifecycle-extensions:2.2.0")
    "api"("androidx.lifecycle:lifecycle-viewmodel:2.2.0")
    "api"("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.2.0")
    "api"("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    "api"("androidx.lifecycle:lifecycle-runtime:2.2.0")
    "api"("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    "api"("androidx.lifecycle:lifecycle-process:2.2.0")
    "api"("androidx.lifecycle:lifecycle-common-java8:2.2.0")
    "api"("androidx.lifecycle:lifecycle-reactivestreams:2.2.0")
    "api"("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.2.0")
    "api"("androidx.savedstate:savedstate:1.0.0")

    /**
     * Basic architecture
     */
    "api"("androidx.concurrent:concurrent-futures:1.0.0")
    "api"("androidx.concurrent:concurrent-listenablefuture:1.0.0-beta01")
    "api"("androidx.concurrent:concurrent-listenablefuture-callback:1.0.0-beta01")
    "api"("androidx.loader:loader:1.1.0")
    "api"("androidx.localbroadcastmanager:localbroadcastmanager:1.0.0")
    "api"("androidx.preference:preference:1.1.1")
    "api"("androidx.work:work-runtime:2.3.4")
    "api"("androidx.work:work-runtime-ktx:2.3.4")
    "api"("androidx.work:work-rxjava2:2.3.4")
    "api"("androidx.vectordrawable:vectordrawable:1.1.0")
    "api"("androidx.vectordrawable:vectordrawable-animated:1.1.0")
    "api"("androidx.versionedparcelable:versionedparcelable:1.1.1")

    /**
     * UI Modules
     */
    "api"("androidx.navigation:navigation-fragment:2.2.2")
    "api"("androidx.navigation:navigation-ui:2.2.2")
    "api"("androidx.navigation:navigation-common-ktx:2.3.0-rc01")
    "api"("androidx.navigation:navigation-fragment-ktx:2.3.0-rc01")
    "api"("androidx.navigation:navigation-runtime-ktx:2.3.0-rc01")
    "api"("androidx.navigation:navigation-ui-ktx:2.3.0-rc01")
    "api"("androidx.paging:paging-runtime:2.1.2")
    "api"("androidx.paging:paging-rxjava2:2.1.2")
    "api"("androidx.asynclayoutinflater:asynclayoutinflater:1.0.0")
    "api"("androidx.cursoradapter:cursoradapter:1.0.0")
    "api"("androidx.slice:slice-builders:1.0.0")
    "api"("androidx.slice:slice-builders-ktx:1.0.0-alpha6")
    "api"("androidx.slice:slice-core:1.0.0")
    "api"("androidx.slice:slice-view:1.0.0")
    "api"("androidx.recyclerview:recyclerview:1.1.0")
    "api"("androidx.recyclerview:recyclerview-selection:1.0.0")
    "api"("androidx.transition:transition:1.3.1")
    "api"("androidx.viewpager:viewpager:1.0.0")
    "api"("androidx.viewpager2:viewpager2:1.0.0")
    "api"("androidx.interpolator:interpolator:1.0.0")

    /**
     * Design modules.
     */
    "api"("androidx.swiperefreshlayout:swiperefreshlayout:1.0.0")
    "api"("androidx.slidingpanelayout:slidingpanelayout:1.0.0")
    "api"("androidx.cardview:cardview:1.0.0")
    "api"("androidx.constraintlayout:constraintlayout:1.1.3")
    "api"("androidx.customview:customview:1.0.0")
    "api"("androidx.drawerlayout:drawerlayout:1.0.0")
    "api"("androidx.dynamicanimation:dynamicanimation:1.0.0")
    "api"("androidx.emoji:emoji:1.0.0")
    "api"("androidx.percentlayout:percentlayout:1.0.0")
    "api"("com.google.android.material:material:1.1.0") // Material components
    "api"("androidx.palette:palette:1.0.0")
    "api"("androidx.palette:palette-ktx:1.0.0")


    /**
     * Multi Media modules
     */
    "api"("androidx.camera:camera-core:1.0.0-beta05")
    "api"("androidx.camera:camera-camera2:1.0.0-beta05")
    "api"("androidx.exifinterface:exifinterface:1.2.0")
    "api"("androidx.gridlayout:gridlayout:1.0.0")
    "api"("androidx.heifwriter:heifwriter:1.0.0")
    "api"("androidx.media:media:1.1.0")
    "api"("androidx.media2:media2-common:1.0.3")
    "api"("androidx.media2:media2-player:1.0.3")
    "api"("androidx.media2:media2-session:1.0.3")

    /**
     * Database modules
     */
    "api"("androidx.sqlite:sqlite:2.1.0")
    "api"("androidx.sqlite:sqlite-ktx:2.1.0")
    "api"("androidx.room:room-runtime:2.2.5")
    "api"("androidx.room:room-ktx:2.2.5")
    "api"("androidx.room:room-rxjava2:2.2.5")
    "api"("androidx.room:room-guava:2.2.5")

    /**
     * External modules
     */
    "api"("androidx.browser:browser:1.2.0")
    "api"("androidx.biometric:biometric:1.0.1")
    "api"("androidx.car:car:1.0.0-alpha7")
    "api"("androidx.documentfile:documentfile:1.0.1")
    "api"("androidx.enterprise:enterprise-feedback:1.0.0")
    "api"("androidx.leanback:leanback:1.0.0")
    "api"("androidx.leanback:leanback-preference:1.0.0")
    "api"("androidx.print:print:1.0.0")
    "api"("androidx.recommendation:recommendation:1.0.0")
    "api"("androidx.remotecallback:remotecallback:1.0.0-alpha02")
    "api"("androidx.security:security-crypto:1.0.0-rc02")
    "api"("androidx.sharetarget:sharetarget:1.0.0")
    "api"("androidx.textclassifier:textclassifier:1.0.0-alpha03")
    "api"("androidx.tvprovider:tvprovider:1.0.0")
    "api"("androidx.wear:wear:1.0.0")
    "api"("androidx.webkit:webkit:1.2.0")

    /**
     * Google Play Services
     */
    "api"("com.google.android.gms:play-services-auth:18.0.0")
    "api"("com.google.android.gms:play-services-analytics:17.0.0")
    "api"("com.google.android.gms:play-services-appinvite:18.0.0")
    "api"("com.google.android.gms:play-services-cast:18.1.0")
    "api"("com.google.android.gms:play-services-cast-framework:18.1.0")
    "api"("com.google.android.gms:play-services-measurement-api:17.4.3")
    "api"("com.google.android.gms:play-services-tagmanager:17.0.0")
    "api"("com.google.android.gms:play-services-vision:20.1.0")
//    "api"("com.google.android.gms:play-services-gass:18.3.0") // AdMob library.

    /**
     * Firebase
     */
    "api"("com.google.firebase:firebase-analytics:17.4.3")
    "api"("com.google.firebase:firebase-dynamic-links:19.1.0")
    "api"("com.google.firebase:firebase-iid:20.2.0")
    "api"("com.google.firebase:firebase-messaging:20.2.0")
    "api"("com.google.firebase:firebase-ml-common:22.1.1")
    "api"("com.google.firebase:firebase-ml-model-interpreter:22.0.3")
    "api"("com.google.firebase:firebase-ml-vision:24.0.3")
    "api"("com.google.firebase:firebase-ml-vision-image-label-model:20.0.1")
    "api"("com.google.firebase:firebase-core:17.4.3")
    "api"("com.google.firebase:firebase-invites:17.0.0")
    "api"("com.google.firebase:firebase-perf:19.0.7")
    "api"("com.google.firebase:firebase-auth:19.3.1")
    "api"("com.google.firebase:firebase-config:19.1.4")
}
