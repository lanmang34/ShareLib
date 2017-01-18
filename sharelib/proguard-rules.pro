# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\myself\program\android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# 实体
# 避免混淆泛型,异常,内部类
-keep class com.lanmang.sharelib.entry.** { *; }
# support library
-keepnames class android.support.** { *; }

# 保护注解、泛型。保留源文件名、行号，便于分析崩溃堆栈信息。
-keepattributes *Annotation*,Signature,SourceFile,LineNumberTable,InnerClasses

# Sharesdk
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class com.mob.** {*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.mob.**
-dontwarn **.R$*
# End Sharesdk