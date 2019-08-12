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
        return """(@($resourceName|${resourceName}StateList)\/$target["|<|.])|(R\.$resourceName\.${toCamelCaseWithUnderscore(
            target
        )}[,|)|;|"|\s|:])|($target\.)|(parent="$target")"""
      }
      Type.DRAWABLE -> {
        val targetDrawable = target.replace(".9", "")
        return """(@($resourceName|${resourceName}StateList)\/$targetDrawable["|<])|(R\.$resourceName\.$targetDrawable[,|)|;|"|\s|:| ])"""
      }
      Type.LAYOUT -> {
        return """(@($resourceName|${resourceName}StateList)\/$target["|<])|(R\.$resourceName\.$target[,|)|;|"|\s|:])|(${toCamelCase(
            target
        )}Binding)"""
      }
      else -> {
        return """(@($resourceName|${resourceName}StateList)\/$target["|<])|(R\.$resourceName\.$target[,|)|;|"|\s|:| ])"""
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
    val builder = StringBuilder()
    builder.append(name)
    name.forEachIndexed { index, value ->
      if (value == '.') {
        builder.setCharAt(index, '_')
        if (index + 1 <= name.length) {
          builder.setCharAt(index + 1, name[index + 1].toUpperCase())
        }
      }
    }
    return builder.toString()
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
