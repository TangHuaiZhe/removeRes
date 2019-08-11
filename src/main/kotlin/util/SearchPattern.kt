package util

/**
 * author: tang
 * created on: 2019-08-07 17:11
 * description:
 */
import groovy.lang.Closure
import org.codehaus.groovy.runtime.DefaultGroovyMethods
import org.codehaus.groovy.runtime.StringGroovyMethods
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting

object SearchPattern {
  /**
   * @param target is file name or attribute name
   * @return pattern string to grep src
   */
  fun create(resourceName: String, target: String, type: Type): String {
    when (type) {
      Type.STYLE -> {
        return """(@($resourceName|${resourceName}StateList)\/$target[\s!"#\$%&'()\*\+\-\,\\/:;<=>?@\[\\\]^`{|}~])|(R\.$resourceName\.${toCamelCaseWithUnderscore(
            target
        )})|($target\.)|(parent="$target")"""
      }
      Type.DRAWABLE -> {
        val targetDrawable = target.replace(".9", "")
        return """(@($resourceName|${resourceName}StateList)\/$targetDrawable["|<])|(R\.$resourceName\.$targetDrawable[,|)|;|"|\n])"""
      }
      Type.LAYOUT -> {
        return """(@($resourceName|${resourceName}StateList)\/$target["|<])|(R\.$resourceName\.$target[,|)|;|"|\n])|(${toCamelCase(
            target
        )}Binding)"""
      }
      else -> {
        return """(@($resourceName|${resourceName}StateList)\/$target["|<])|(R\.$resourceName\.$target)"""
      }
    }
  }

  /**
   * @param target is file name or attribute name
   * @return pattern string to grep src
   */
  fun create(resourceName: String, target: String): String {
    return SearchPattern.create(resourceName, target, Type.DEFAULT)
  }

  @VisibleForTesting
  fun toCamelCaseWithUnderscore(name: String): String {
    return name.replace(Regex("(\\.)([A-Za-z0-9])")) {
      { "_${it.value.toUpperCase()}" }.toString()
    }
  }

  private fun toCamelCase(text: String): String {
    return StringGroovyMethods.capitalize(
        StringGroovyMethods.replaceAll(text, "(_)([A-Za-z0-9])", object : Closure<Any>(null, null) {
          fun doCall(it: Array<Any>): Any {
            return DefaultGroovyMethods.invokeMethod(it[2], "toUpperCase", arrayOfNulls<Any>(0))
          }
        })
    )
  }

  enum class Type {
    STYLE,
    DRAWABLE,
    LAYOUT,
    DEFAULT;

    companion object {

      fun from(type: String): Type {
        return when {
          StringGroovyMethods.isCase("style", type) -> STYLE
          StringGroovyMethods.isCase("drawable", type) -> DRAWABLE
          StringGroovyMethods.isCase("layout", type) -> LAYOUT
          else -> DEFAULT
        }
      }
    }
  }
}