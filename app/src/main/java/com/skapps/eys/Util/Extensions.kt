package com.skapps.eys.Util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skapps.eys.R
import es.dmoral.toasty.Toasty
import java.io.File
import java.util.*


@SuppressLint("CheckResult")
infix fun Context.infoToast(message:String){
    Toasty.info(this,message, Toasty.LENGTH_SHORT).show()
}

@SuppressLint("CheckResult")
infix fun Context.toast(message:String){
    Toasty.normal(this,message, Toasty.LENGTH_SHORT).show()
}

@SuppressLint("CheckResult")
infix fun Context.warningToast(message:String){
    Toasty.warning(this,message, Toasty.LENGTH_SHORT).show()
}
@SuppressLint("CheckResult")
infix fun Context.succesToast(message:String){
    Toasty.success(this,message, Toasty.LENGTH_SHORT).show()
}

fun NavController.safeNavigate(direction: NavDirections) {
    Log.d("safe", "Click happened")
    currentDestination?.getAction(direction.actionId)?.run {
        Log.d("safe", "Click Propagated")
        navigate(direction)
    }
}
fun Context.succesAlert(titleText:String,confirmText:String){
    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        .setTitleText(titleText)
        .setConfirmText(confirmText)
        .show()
}
fun Context.warningAlert(titleText:String,confirmText:String){
    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        .setTitleText(titleText)
        .setConfirmText(confirmText)
        .show()
}
fun AutoCompleteTextView.addItemList(clasList: ArrayList<String>, context: Context){
    val arrayAdapter = ArrayAdapter(context, R.layout.dropdown_item,clasList)
    // get reference to the autocomplete text view
    val autocompleteTV = this
    autocompleteTV.setText(R.string.sinif_sec)
    // set adapter to the autocomplete tv to the arrayAdapter
    autocompleteTV.setAdapter(arrayAdapter)
}
fun Bitmap.makeSmallerBitmap(image: Bitmap, maximumSize: Int): Bitmap {
    var height = image.height
    var width = image.width
    val bitmapRatio :Double=width.toDouble()/height.toDouble()
    if(bitmapRatio > 1 ){
        //landSpace
        width=maximumSize
        val scaledHeight=width/bitmapRatio
        height = scaledHeight.toInt()
    }else{
        //potrait
        height=maximumSize
        val scaledWidht=height* bitmapRatio
        width=scaledWidht.toInt()
    }
    return Bitmap.createScaledBitmap(image,width,height,true)
}
fun getFileChooserIntentForImageAndPdf(): Intent {
    val mimeTypes = arrayOf("application/pdf")
    val intent = Intent(Intent.ACTION_GET_CONTENT,)
        .setType("application/pdf")
        .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
    return intent
}
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

@SuppressLint("Range")
fun Context.getPdfName(pdfData: Uri?): String {
    var pdfName = ""
    if (pdfData!!.toString().startsWith("content://")) {
        var cursor: Cursor? = null
        try {
            cursor = this.contentResolver.query(pdfData, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
            cursor!!.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } else if (pdfData.toString().startsWith("file://")) {
        pdfName = File(pdfData.toString()).name
    }
    return pdfName
}

fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun placeholderProgressBar(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

fun downloadImage(view: ImageView, url:String?) {
    view.downloadFromUrl(url, placeholderProgressBar(view.context))
}
