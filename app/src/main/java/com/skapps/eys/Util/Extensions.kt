package com.skapps.eys.Util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import cn.pedant.SweetAlert.SweetAlertDialog
import com.skapps.eys.R
import es.dmoral.toasty.Toasty
import java.util.ArrayList


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

