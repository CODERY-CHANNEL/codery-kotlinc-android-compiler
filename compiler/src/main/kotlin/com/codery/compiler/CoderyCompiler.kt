package com.codery.compiler

import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments
import org.jetbrains.kotlin.cli.common.ExitCode
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import org.jetbrains.kotlin.config.Services
import java.io.File

public object CoderyCompiler {
    public fun compile(request: CompilationRequest): CompilationResult {
        val sources = request.sourceRoots
            .filter(File::isDirectory)
            .flatMap { root -> root.walkTopDown().filter { it.isFile && it.extension == "kt" }.toList() }
        if (sources.isEmpty()) return CompilationResult(false, listOf("No Kotlin source files found"))
        request.outputDirectory.mkdirs()
        val collector = DiagnosticCollector()
        val arguments = K2JVMCompilerArguments().apply {
            destination = request.outputDirectory.absolutePath
            classpath = request.classpath.joinToString(File.pathSeparator) { it.absolutePath }
            freeArgs = sources.map { it.absolutePath }
            kotlinHome = request.kotlinHome?.absolutePath
            noStdlib = true
            noReflect = true
            noJdk = request.jdkHome == null
        }
        request.jdkHome?.let { home ->
            arguments.javaClass.methods.firstOrNull { method ->
                method.name == "setJavaHome" && method.parameterTypes.contentEquals(arrayOf(String::class.java))
            }?.invoke(arguments, home.absolutePath)
        }
        val exitCode = runCatching { K2JVMCompiler().exec(collector, Services.EMPTY, arguments) }
            .getOrElse {
                collector.errors += it.message ?: it.javaClass.name
                null
            }
        return CompilationResult(exitCode == ExitCode.OK && collector.errors.isEmpty(), collector.errors + collector.messages)
    }

    private class DiagnosticCollector : MessageCollector {
        val messages = mutableListOf<String>()
        val errors = mutableListOf<String>()

        override fun clear() {
            messages.clear()
            errors.clear()
        }

        override fun hasErrors(): Boolean = errors.isNotEmpty()

        override fun report(severity: CompilerMessageSeverity, message: String, location: CompilerMessageSourceLocation?) {
            val place = location?.let { "${it.path}:${it.line}:${it.column}" }.orEmpty()
            val entry = listOf(place, severity.name, message).filter(String::isNotBlank).joinToString(" ")
            if (severity.isError) errors += entry else messages += entry
        }
    }
}

public data class CompilationRequest(
    public val sourceRoots: List<File>,
    public val classpath: List<File>,
    public val outputDirectory: File,
    public val kotlinHome: File? = null,
    public val jdkHome: File? = null
)

public data class CompilationResult(
    public val success: Boolean,
    public val diagnostics: List<String>
)
