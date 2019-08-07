import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * author: tang
 * created on: 2019-08-07 14:05
 * description:
 */
class RemoveResPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        print("this is RemoveResPlugin")
    }
}