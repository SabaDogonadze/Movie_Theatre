package com.example.movietheatre.feature_movie_quiz.presentation.screen.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.movietheatre.R
import com.example.movietheatre.databinding.FragmentWrongAnswerDialogBinding

class WrongAnswerDialog : DialogFragment() {

    private var _binding: FragmentWrongAnswerDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWrongAnswerDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        binding.btnContinue.setOnClickListener {
            dismiss()
            try {
                requireParentFragment().findNavController()
                    .popBackStack(R.id.quizCategoryFragment, false)
            } catch (e: Exception) {
                Log.e("WrongAnswerDialog", "Error navigating: ${e.message}")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): WrongAnswerDialog {
            return WrongAnswerDialog()
        }
    }
}