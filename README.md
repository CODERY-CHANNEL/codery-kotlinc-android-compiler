# Codery Kotlin Compiler Runtime

Official Kotlin compiler components prepared for Android-hosted compilation.

## Contents

```text
official/kotlin/          Kotlin 2.4.10 compiler components
official/dex-r8-9.1.31/  Android DEX payload converted with R8/D8 9.1.31
official/tools/           R8/D8 tool and conversion log
official/dex/             DEX payload converted with Android Build Tools 36
dex/                      Original Sketchware-compatible reference payload
kotlinc-for-sketchware-2.1.21_rc3.jar  Reference artifact
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

## Licensing

Kotlin compiler components remain licensed by their respective copyright holders. See `NOTICE` and the upstream component licenses before redistribution.
