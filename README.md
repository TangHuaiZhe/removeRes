# removeRes
[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)


移除Android项目中没有使用到的资源文件和xml value.

集成:
```gradle
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.tangnb.plugin:removeRes:1.0.7"
  }
}

apply plugin: "com.tangnb.RemoveRes"
```

然后运行:
`./gradlew RemoveRes --parallel`
