package com.github.marfikus.helloworld.helloworld

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.github.marfikus.helloworld.R

class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        this.isCancelable = false
        val dialogBuilder = AlertDialog.Builder(requireContext())
//        dialogBuilder.setCancelable(false)
//        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog, contentLayout, false)
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog, null)
        dialogBuilder.setView(dialogView)
        val dialog = dialogBuilder.create()
        dialogView.findViewById<View>(R.id.closeDialogButton).setOnClickListener {
            dialog.dismiss()
        }
        return dialog
    }
}