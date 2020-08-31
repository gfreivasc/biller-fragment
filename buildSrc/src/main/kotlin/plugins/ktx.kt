package plugins

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

inline fun <reified Ex : BaseExtension> Project.getAndroid(): Ex = try {
    extensions.findByType(Ex::class.java)!!
} catch (ex: NullPointerException) {
    throw IllegalArgumentException("Getting android extension on non-android module")
}
