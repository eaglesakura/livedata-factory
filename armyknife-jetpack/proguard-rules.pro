# for Jetpack / Google Play Service
-keepnames class com.google.firebase.** { *; }
-keepnames class com.google.android.gms.** { *; }
-keepnames class androidx.** { *; }
-keepnames class android.** { *; }
-keep public class * extends android.os.Parcelable {
  <init>();
}
-keep public class * extends androidx.versionedparcelable.VersionedParcelable {
  <init>();
}
-keep public class * extends androidx.lifecycle.ViewModel {
  <init>(***);
}
-keep public class * extends androidx.fragment.app.Fragment {
  <init>(***);
}
-keep public class * extends androidx.core.app.ComponentActivity {
  <init>(***);
}