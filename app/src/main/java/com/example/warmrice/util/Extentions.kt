package com.example.warmrice.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import androidx.fragment.app.Fragment
import com.example.warmrice.R
import com.google.firebase.firestore.Blob
import java.io.ByteArrayOutputStream

/* TODO
// Usage: Show an error dialog from fragment
fun Fragment.errorDialog(text: String) {
    AlertDialog.Builder(context)
        .setIcon(R.drawable.ic_error).setTitle("Error").setMessage(text)
        .setPositiveButton("Dismiss", null).show()
}*/

// Usage: Crop and resize bitmap (upscale)
fun Bitmap.crop(width: Int, height: Int): Bitmap {
    // Source width, height and ratio
    val sw = this.width
    val sh = this.height
    val sRatio = 1.0 * sw / sh

    // Target offset (x, y), width, height and ratio
    val x: Int
    val y: Int
    val w: Int
    val h: Int
    val ratio = 1.0 * width / height

    if (ratio >= sRatio) {
        // Retain width, calculate height
        w = sw
        h = (sw / ratio).toInt()
        x = 0
        y = (sh - h) / 2
    } else {
        // Retain height, calculate width
        w = (sh * ratio).toInt()
        h = sh
        x = (sw - w) / 2
        y = 0
    }
    return Bitmap
        .createBitmap(this, x, y, w, h) // Crop
        .scale(width, height) // Resize
}

// Usage: Convert from Bitmap to Blob
@Suppress("DEPRECATION")
fun Bitmap.toBlob(): Blob {
    ByteArrayOutputStream().use {
        this.compress(Bitmap.CompressFormat.WEBP, 80, it)
        return Blob.fromBytes(it.toByteArray())
    }
}

// Usage: Convert from Blob to Bitmap
fun Blob.toBitmap(): Bitmap? {
    val bytes = this.toBytes()
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

// Usage: Crop to Blob
fun ImageView.cropToBlob(width: Int, height: Int): Blob {
    return if (this.drawable == null)
        Blob.fromBytes(ByteArray(0))
    else
        this.drawable.toBitmap().crop(width, height).toBlob()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.openKeyboard() {
    requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}
