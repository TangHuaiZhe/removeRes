# removeRes
移除Android项目中没有使用到的资源.

集成:
```gradle
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.tangnb.plugin:removeRes:1.0.1-SNAPSHOT"
  }
}

apply plugin: "com.tangnb.plugin"
```

然后运行:
`./gradlew RemoveRes`
