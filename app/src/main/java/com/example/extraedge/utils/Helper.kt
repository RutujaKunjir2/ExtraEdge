package com.example.extraedge.utils

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.extraedge.models.ProductItem
import com.example.extraedge.ui.views.ProgressView


object Helper
{
    fun getProgressDialog(
        activity: Activity?,
        title: String?,
        message: String?,
        isCancelable: Boolean
    ): AlertDialog? {
        val progressView =
            ProgressView(activity!!)
        progressView.setTitle(title).setMessage(message)
        val builder =
            AlertDialog.Builder(activity!!)
        builder.setView(progressView).setCancelable(isCancelable)
        return builder.create()
    }

}