package com.carteagal.baz_android.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

object AlertError {
    fun showAlertError(context: Context, func: () -> Unit){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error de conexión")
        builder.setMessage("No se ha podido establecer una conexión con el servidor. Por favor, verifique su conexión a Internet e intente más tarde.")
        builder.setPositiveButton("Aceptar"){dialog, which ->
            func()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}