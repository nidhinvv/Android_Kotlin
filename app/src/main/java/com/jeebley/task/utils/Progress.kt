package com.jeebley.task.utils

import android.app.ProgressDialog
import android.content.Context

class Progress {
    companion object {
        var dialog: ProgressDialog? = null

        fun show(context: Context, message: String) {
            dialog = ProgressDialog(context).apply {
                setMessage(message)
                show()
            }
        }

        fun dismiss() {
            dialog?.dismiss()
        }
    }
}