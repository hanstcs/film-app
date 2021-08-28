package com.example.filmapp.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.filmapp.databinding.LayoutFailedBinding
import com.example.filmapp.extension.gone
import com.example.filmapp.extension.visible

class FailedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var binding: LayoutFailedBinding = LayoutFailedBinding.inflate(
        LayoutInflater.from(context), this, false
    )

    init {
        addView(binding.root)
    }

    fun show() {
        binding.ivErrorImage.visible()
        binding.tvMessage.visible()
    }

    fun hide() {
        binding.ivErrorImage.gone()
        binding.tvMessage.gone()
    }
}
