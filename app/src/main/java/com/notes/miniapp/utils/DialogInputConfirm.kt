package com.notes.miniapp.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.notes.miniapp.R
import com.notes.miniapp.databinding.DialogInputConfirmBinding

class DialogInputConfirm : DialogFragment() {
    private lateinit var binding: DialogInputConfirmBinding
    var onPositiveClick: ((String) -> Unit)? = null
    var onNegativeClick: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return dialog ?: super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.dialog_input_confirm, container, false)
        binding = DialogInputConfirmBinding.bind(v)
        binding.editTextInput.doOnTextChanged { text, start, before, count ->
            binding.textCount.text = "${text?.length ?: 0}/40"
        }
        return v
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            tvPositive.setOnClickListener {
                val value = normalizeData(editTextInput.text.toString())
                if (value.isNotBlank()) {
                    onPositiveClick?.invoke(value)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "Please enter a valid text", Toast.LENGTH_SHORT).show()
                }
            }

            tvNegative.setOnClickListener {
                onNegativeClick?.invoke()
                dismiss()
            }
        }
    }

    private fun normalizeData(text: String?): String {
        return if (text.isNullOrBlank()) {
            ""
        } else {
            var result = text.trim().replace("\\s+".toRegex(), " ")
            result = result.capitalize()
            result
        }
    }
}
