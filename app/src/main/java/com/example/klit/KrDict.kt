package com.example.klit

import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory

class Memoize1<in T, out R>(val f: (T) -> R) : (T) -> R {
    private val values = mutableMapOf<T, R>()
    override fun invoke(x: T): R {
        return values.getOrPut(x, { f(x) })
    }
}

fun <T, R> ((T) -> R).memoize(): (T) -> R = Memoize1(this)

val callAPI = { x: String -> callAPIRaw(x) }.memoize()

fun callAPIRaw(koreanWordTranslate: String): String {
    val key = BuildConfig.KrDictApiKey
    val krdicturl = "https://krdict.korean.go.kr/api/search"
    val transxml =
        "$krdicturl?key=$key&type_search=search&part=word&q=$koreanWordTranslate&sort=dict&translated=y&trans_lang=1"
    return readXml(transxml).joinToString()
}


private fun readXml(transxml: String): List<String> {
    val dbFactory = DocumentBuilderFactory.newInstance()
    val dBuilder = dbFactory.newDocumentBuilder()
    var translations = dBuilder.parse(transxml).getElementsByTagName("trans_word")

    fun NodeList.asList() = (0 until length).map { item(it).textContent }
    return translations.asList()
}
