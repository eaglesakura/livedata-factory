# What is this Library?

`LiveDataFactory` is LiveData create utils for androidx.ViewModel.

# Example.

```kotlin

val url = MutableLiveData<String>()
val name = MutableLiveData<String>()
val description = MutableLiveData<String>()

val ok: LiveData<Boolean> =  LiveDataFactory.transform(url, name, description) { url, name, description ->
    url.isNotEmpty() && name.isNotEmpty() && description.isNotEmpty()
}
```

# How to Install.

```groovy
// /app/build.gradle
dependencies {
    implementation 'io.github.eaglesakura.livedata-factory:livedata-factory:1.0.0'
}
```

