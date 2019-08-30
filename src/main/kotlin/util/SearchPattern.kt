package util

/**
 * author: tang
 * created on: 2019-08-07 17:11
 * description:
 */
import org.gradle.util.GUtil.toCamelCase

object SearchPattern {

  fun create(resType: String, resName: String, mainType: Type): String {
    when (mainType) {
      Type.STYLE -> {
        return """(@($resType|${resType}StateList)\/$resName["|<|.])|(R\.$resType\.${toCamelCaseWithUnderscore(
          resName
        )}[,|)|;|"|\s|:])|($resName\.)|(parent="$resName")"""
      }
      Type.DRAWABLE -> {
        val targetDrawable = resName.replace(".9", "")
        return """(@($resType|${resType}StateList)\/$targetDrawable["|<])|(R\.$resType\.$targetDrawable[,|)|;|"|\s|:| ])"""
      }
      Type.LAYOUT -> {
        return """(@($resType|${resType}StateList)\/$resName["|<])|(R\.$resType\.$resName[,|)|;|"|\s|:])|(${toCamelCase(
          resName
        )}Binding)"""
      }
      else -> {
        return """(@($resType|${resType}StateList)\/$resName["|<])|(R\.$resType\.$resName[,|)|;|"|\s|:| ])"""
      }
    }
  }


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

  enum class Type {
    STYLE,
    DRAWABLE,
    LAYOUT,
    DEFAULT;
  }
}

//fun main() {
//  println(toCamelCase("nd_ahN_ksd"))
//}