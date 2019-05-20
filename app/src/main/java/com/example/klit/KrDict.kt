package com.example.klit

import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory

fun callAPI(koreanWordTranslate: String): List<String> {
    val key = ""
    val krdicturl = "https://krdict.korean.go.kr/api/search"
    val transxml =
        "$krdicturl?key=$key&type_search=search&part=word&q=$koreanWordTranslate&sort=dict&translated=y&trans_lang=1"
    return readXml(transxml)
}


private fun readXml(transxml: String): List<String> {
    val dbFactory = DocumentBuilderFactory.newInstance()
    val dBuilder = dbFactory.newDocumentBuilder()
    var translations = dBuilder.parse(transxml).getElementsByTagName("trans_word")

    fun NodeList.asList() = (0 until length).map { item(it).textContent }
    return translations.asList()
}
