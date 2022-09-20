package com.bizpro.commondialoglibrary

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View

object CommonMessageDialog {

    fun createSingleChoicePositive(context: Context, messageData: MessageData) {
        createSingleChoicePositive(context, messageData) { _, _ -> }
    }

    fun createSingleChoicePositive(context: Context, messageData: MessageData, onPositiveClick: DialogInterface.OnClickListener) {
        val builder = AlertDialog.Builder(context)
        if (messageData.title != "") {
            builder.setTitle(messageData.title)
        }
        builder.setMessage(messageData.message)
        builder.setPositiveButton(messageData.positiveTitle, onPositiveClick)
        builder.setCancelable(messageData.isCancelable)
        builder.show()
    }

    fun createSingleChoicePositive(context: Context, messageData: MessageData, customView: View): AlertDialog {
        return createSingleChoicePositive(context, messageData, customView) {_,_-> } // Do nothing when positive button has been clicked
    }

    fun createSingleChoicePositive(context: Context, messageData: MessageData, customView: View, onPositiveClick: DialogInterface.OnClickListener): AlertDialog {
        val builder = AlertDialog.Builder(context)
        if (messageData.title != "") {
            builder.setTitle(messageData.title)
        }
        builder.setView(customView)
        builder.setPositiveButton(messageData.positiveTitle, onPositiveClick)
        builder.setCancelable(messageData.isCancelable)
        val dialog = builder.create()
        dialog.show()
        return dialog
    }

    fun createMultipleChoices(context: Context, messageData: MessageData, onPositiveClick: DialogInterface.OnClickListener, onNegativeClick: DialogInterface.OnClickListener) {
        val builder = AlertDialog.Builder(context)
        if (messageData.title != "") {
            builder.setTitle(messageData.title)
        }
        builder.setMessage(messageData.message)
        builder.setPositiveButton(messageData.positiveTitle, onPositiveClick)
        builder.setNegativeButton(messageData.negativeTitle, onNegativeClick)
        builder.setCancelable(messageData.isCancelable)
        builder.show()
    }

    fun createMultipleChoices(context: Context, messageData: MessageData, customView: View, onPositiveClick: DialogInterface.OnClickListener, onNegativeClick: DialogInterface.OnClickListener): AlertDialog {
        val builder = AlertDialog.Builder(context)
        if (messageData.title != "") {
            builder.setTitle(messageData.title)
        }
        builder.setView(customView)
        builder.setPositiveButton(messageData.positiveTitle, onPositiveClick)
        builder.setNegativeButton(messageData.negativeTitle, onNegativeClick)
        builder.setCancelable(messageData.isCancelable)
        val dialog = builder.create()
        dialog.show()
        return dialog
    }
}