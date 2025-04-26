package com.example.feature.movie_quiz.presentation.screen.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.feature.movie_quiz.presentation.R

class OneTimeWarningDialog : DialogFragment() {

    private var onDismissCallback: (() -> Unit)? = null

    fun setOnDismissCallback(callback: () -> Unit) {
        onDismissCallback = callback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.warning))
            .setMessage(getString(R.string.you_only_have_one_chance_to_complete_this_quiz_are_you_ready_to_begin))
            .setPositiveButton(getString(R.string.i_m_ready)) { _, _ ->
                onDismissCallback?.invoke()
            }
            .setCancelable(false)
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dialog?.setCanceledOnTouchOutside(false)
        isCancelable = false
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        fun newInstance(): OneTimeWarningDialog {
            return OneTimeWarningDialog()
        }
    }
}

private fun DialogFragment.setOnDismissListener(listener: () -> Unit) {
    this.dialog?.setOnDismissListener {
        listener()
    }
}