# Codery Kotlin Compiler Runtime

Official Kotlin compiler components prepared for Android-hosted compilation.

## Contents

```text
official/kotlin/          Kotlin 2.4.10 compiler components
official/dex-r8-9.1.31/  Android DEX payload converted with R8/D8 9.1.31
official/tools/           R8/D8 toolchain
```

## Intended Runtime

The DEX payload is loaded on Android with `DexClassLoader`. The compiler entry point is:

```text
org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
```

The host application must provide a compatible JDK runtime, Android API classpath, project dependencies, and compiler classpath before invoking the compiler.

## Version

```text
Kotlin: 2.4.10
R8/D8: 9.1.31
Android API: 36
Minimum Android API for DEX: 30
```

## Gradle Dependency

After publishing a release tag, add JitPack and use:

```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.CODERY-CHANNEL:codery-kotlinc-android-compiler:v1.0.2")
}
```

## Licensing

Kotlin compiler components remain licensed by their respective copyright holders. See `NOTICE` and the upstream component licenses before redistribution.
