package com.zero.navigationdemo

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment


inline fun Context.toast(message: CharSequence): Toast = Toast
    .makeText(this, message, Toast.LENGTH_SHORT)
    .apply {
        show()
    }

inline fun Fragment.toast(text: CharSequence) = requireActivity().toast(text)