# for Jetpack
-keepnames class android.** { *; }
-keepnames class androidx.** { *; }
-keep public class * extends androidx.versionedparcelable.VersionedParcelable {
  <init>();
}
-keep public class * extends androidx.lifecycle.ViewModel {
  <init>(***);
}
