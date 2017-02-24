# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Volumes/Utilities/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

#-keep class com.j256.ormlite.** {
#    *;
#}
#-dontwarn com.j256.ormlite.**

-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
-dontwarn com.j256.ormlite.**

# Keep the annotations
-keepattributes *Annotation*
-keepattributes *DatabaseField*
-keepattributes *DatabaseTable*
-keepattributes *SerializedName*

# Keep all model classes that are used by OrmLite
# Also keep their field names and the constructor
-keep @com.j256.ormlite.table.DatabaseTable class * {
    @com.j256.ormlite.field.DatabaseField <fields>;
    @com.j256.ormlite.field.ForeignCollectionField <fields>;
    <init>();
}

#-keep class com.tech.quiz.models.databasemodel.**
#-keepclassmembers class om.tech.quiz.models.databasemodel.** { *; }

# Keep the helper class and its constructor
#-keep class * extends com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
#-keepclassmembers class * extends com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper {
#  public <init>(android.content.Context);
#}

#-dontwarn com.j256.ormlite.android.**
#-dontwarn com.j256.ormlite.logger.**
#-dontwarn com.j256.ormlite.misc.**

-keepattributes SourceFile,LineNumberTable,Signature,InnerClasses,*Annotation*
-keepclassmembers class * {public <init>(android.content.Context);}
-keep class com.j256.** { *; }

-keep class com.google.android.gms.** {
    *;
}
-dontwarn com.google.android.gms.**

-keep class okio.** {
    *;
}
-dontwarn okio**

-keep class retrofit2.** {
    *;
}
-dontwarn retrofit2.**

-keep class rx.** {
    *;
}
-dontwarn rx.**

-keep class android.support.v7.widget.SearchView { *; }