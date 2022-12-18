package com.example.extraedge.ui.views

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.extraedge.R

class ProgressView : FrameLayout {
    private var _context: Context? = null
    private var tv_title: TextView? = null
    private var tv_message: TextView? = null
    private var view: View? = null

    constructor(context: Context) : super(context) {
        this._context = context
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        this._context = context
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        this._context = context
        initView()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        this._context = context
        initView()
    }

    private fun initView() {
        view = View.inflate(getContext(), R.layout.dialog_progress, this)
        tv_title = view!!.findViewById(R.id.tv_title)
        tv_message = view!!.findViewById(R.id.tv_message)
    }

    fun setTitle(title: String?): ProgressView {
        tv_title!!.text = title
        return this
    }

    fun setMessage(message: String?): ProgressView {
        tv_message!!.text = message
        return this
    }
}