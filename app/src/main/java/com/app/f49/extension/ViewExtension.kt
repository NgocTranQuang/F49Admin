package extension

import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import com.app.f49.R
import com.app.f49.custom.SingleClickListener
import java.text.NumberFormat
import java.util.*


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
fun View.showDialogAsk(message: String, okAction: () -> Unit) {
    val builder1 = AlertDialog.Builder(context)
    builder1.setMessage(message)
    builder1.setCancelable(true)

    builder1.setPositiveButton(
        context.getString(R.string.yes),
        DialogInterface.OnClickListener { dialog, id ->
            okAction.invoke()
            dialog.cancel()
        })

    builder1.setNegativeButton(
        context.getString(R.string.no),
        DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

    val alert11 = builder1.create()
    alert11.show()
}

fun EditText.addCurrencyFormatter() {

    // Reference: https://stackoverflow.com/questions/5107901/better-way-to-format-currency-input-edittext/29993290#29993290
    this.addTextChangedListener(object : TextWatcher {

        private var current = ""

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            if (s.toString() != current) {
                this@addCurrencyFormatter.removeTextChangedListener(this)
                // strip off the currency symbol

                // Reference for this replace regex: https://stackoverflow.com/questions/5107901/better-way-to-format-currency-input-edittext/28005836#28005836
                val cleanString = s.toString().replace("\\D".toRegex(), "")
                val parsed = if (cleanString.isBlank()) 0.0 else cleanString.toDouble()

                current = formatMoney(parsed)


                this@addCurrencyFormatter.setText(current)
                this@addCurrencyFormatter.setSelection(current.length)

                this@addCurrencyFormatter.addTextChangedListener(this)
            }
        }
    })

}
fun formatMoney(price: Double): String {
    try {
        var moneyUSD = NumberFormat.getCurrencyInstance(Locale.US).format(price)
        moneyUSD = moneyUSD.replace("$", "")
        if (moneyUSD.endsWith(".00")) {
            val centsIndex = moneyUSD.lastIndexOf(".00")
            if (centsIndex != -1) {
                moneyUSD = moneyUSD.substring(0, centsIndex)
            }
        }
        moneyUSD = moneyUSD.replace(",", ".")
//        moneyUSD = String.format("%s đ", moneyUSD)
        return moneyUSD
    } catch (e: Exception) {
        return "0"
    }

}