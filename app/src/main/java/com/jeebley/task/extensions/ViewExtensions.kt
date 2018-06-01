package com.jeebley.task.extensions

import android.content.Context
import android.graphics.Color
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import java.util.regex.Pattern


/**
 * ViewGroup extension function to inflate a layout
 *
 * Inflate a view using LayoutInflater
 * examples:
 *      parent.inflate(R.layout.my_layout)
 *      parent.inflate(R.layout.my_layout, true)
 *
 * @param layoutRes Layout resource identifier
 * @param attachToRoot Attach the inflated view to the root, default is false
 */


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun EditText.isValidEmail(error: String): Boolean {
    return if (text.toString().isValidEmail()) true
    else {
        this.error = error; this.requestFocus(); false
    }
}

fun TextView.empty(): TextView{
    this.text = ""

    return this
}

fun EditText.isValidPassword(error: String): Boolean {
    return if (!this.text.isNullOrEmpty()) true else {
        this.error = error; this.requestFocus(); false
    }
}

/*
// Password Validation With Special characters
fun EditText.isValidPassword(error: String): Boolean {

    val PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[ !\"#\$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]).{8,20})"
    val pattern = Pattern.compile(PASSWORD_PATTERN);

    val matcher = pattern.matcher(this.text)

    return if (matcher.matches()) true else {
        this.error = error; this.requestFocus(); false
    }
}*/

fun EditText.isSamePassword(password: String, error: String): Boolean {
    return if (this.text.toString() == password && !(this.text.isNullOrEmpty())) {
        true
    } else {
        this.error = error
        this.requestFocus()
        false
    }
}

fun EditText.isNullOrEmpty(error: String): Boolean {
    return if (!this.text.isNullOrEmpty()) true else {
        this.error = error; this.requestFocus(); false
    }
}

fun Spinner.isValidSpinnerItem(error: String, position: Int): Boolean {
    return if (position > 0) true else {
        val errorText = this.selectedView as TextView
        errorText.error = ""
        errorText.setTextColor(Color.RED)//just to highlight that this is an error
        errorText.text = error; false
    }
}

fun TextView.isNullOrEmpty(error: String): Boolean {
    return if (!this.text.isNullOrEmpty()) true else {
        this.error = error; this.requestFocus(); false
    }
}

fun EditText.isValidPhoneNumber(error: String): Boolean {
    return if (Pattern.compile("^[2-9]{2}[0-9]{8}\$").matcher(this.text).matches()) true
    else {
        this.error = error
        this.requestFocus()
        false
    }
}

fun getUnitWidth(context: Context): Int {
    return dipToPixels(context, 5)
}

fun dipToPixels(context: Context, dip: Int): Int {
    val r = context.resources
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip.toFloat(),
            r.displayMetrics)
    return px.toInt()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.snack(message: String?, length: Int = Snackbar.LENGTH_LONG) {
    this.takeIf { null != message }?.apply {
        val snack = Snackbar.make(this, message!!, length)
        snack.duration = 5000;// 5 sec
        val layout = snack.view as Snackbar.SnackbarLayout
        val textView = layout.findViewById(android.support.design.R.id.snackbar_text) as TextView
        textView.maxLines = 10;

        snack.show()
    }
}