package extension

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import com.app.f49.R
import com.app.f49.custom.SingleClickListener


fun LinearLayout.replaceFragment(fragment: Fragment) {
    val transaction = (this.context as FragmentActivity).supportFragmentManager.beginTransaction()
    transaction.replace(this.id, fragment)
    transaction.commit()
}

fun LinearLayout.addFragment(fragment: Fragment) {
    val transaction = (this.context as FragmentActivity).supportFragmentManager.beginTransaction()
    transaction.add(this.id, fragment)
    transaction.commit()
}

fun View.setOnSingleClickListener(toDo: (View) -> Unit) {
    setOnClickListener(object : SingleClickListener() {
        override fun onClicked(v: View) {
            toDo.invoke(v)
        }
    })
}

fun WebView.init() {
    settings.javaScriptEnabled = true
    settings.loadWithOverviewMode = true
    settings.useWideViewPort = true
    webViewClient = WebViewClient()
}

fun TextView.underLine() {
    val content = SpannableString(context.getString(R.string.forgot_password))
    content.setSpan(UnderlineSpan(), 0, content.length, 0)
    text = content

}

fun Spinner.setList(list: MutableList<String?>?, seleted: Int?) {
    list?.let {
        val dataAdapter = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item, list)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = dataAdapter
        if (seleted != null && seleted != -1) {
            setSelection(seleted)
        }
    }
}

fun Spinner.selectedItemListener(color: Int? = null, listener: (Int) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            view?.let {
                if (view is TextView) {
                    color?.let {
                        view.setTextColor(it)
                    }
                }
            }
            listener.invoke(position)
        }

    }
}