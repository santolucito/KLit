package com.example.klit

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.min

fun genClickables(str: String, translationTextView: TextView): SpannableString {
    var ss = SpannableString(str)
    var i = 0
    while (i < ss.length
        && ss.indexOf(" ", i + 1) != -1) {

        val firstSpace = i
        val secondSpace = i +1 + str.substring(i+1).indexOfFirst { it==' ' || it == '.' || it == '\n' }
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                updateTranslation( str.substring(firstSpace, secondSpace), translationTextView)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        ss.setSpan(
            clickableSpan,
            firstSpace,
            secondSpace,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        i = secondSpace
    }
    return ss
}


private fun updateTranslation(textToTranslate: CharSequence, translationTextView: TextView) {
    print(textToTranslate.toString())
    GlobalScope.launch (Dispatchers.IO) {
        val translatedWord =
            callAPI(textToTranslate.toString())
        withContext(Dispatchers.Main) {
            translationTextView.text = textToTranslate.toString() + "\n" + translatedWord
        }
    }
}