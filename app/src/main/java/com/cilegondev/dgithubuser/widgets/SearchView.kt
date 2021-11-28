package com.cilegondev.dgithubuser.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.cilegondev.dgithubuser.R


class SearchView : AppCompatEditText {
    private lateinit var mClearButtonImage: Drawable
    private lateinit var mSearchImage: Drawable
    private lateinit var onClear: OnClear

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun setOnClear(onClear: OnClear) {
        this.onClear = onClear
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setHintTextColor(ResourcesCompat.getColor(resources, android.R.color.black, null))
        setTextColor(ResourcesCompat.getColor(resources, R.color.orange, null))
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_background, null)
        setPadding(60,60,60,60)
        imeOptions = EditorInfo.IME_ACTION_SEARCH
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(focused){
            imm.showSoftInput(this, 0)
        }
    }

    private fun init() {
        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_close, null) as Drawable
        mSearchImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_search, null) as Drawable
        hideClearButton()
        setOnTouchListener(OnTouchListener { _, event ->
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, 0)
            if (compoundDrawablesRelative[2] != null) {
                val clearButtonStart: Float
                val clearButtonEnd: Float
                var isClearButtonClicked = false
                when (layoutDirection) {
                    View.LAYOUT_DIRECTION_RTL -> {
                        clearButtonEnd = (mClearButtonImage.intrinsicWidth + paddingStart).toFloat()
                        when {
                            event.x < clearButtonEnd -> isClearButtonClicked = true
                        }
                    }
                    else -> {
                        clearButtonStart = (width - paddingEnd - mClearButtonImage.intrinsicWidth).toFloat()
                        when {
                            event.x > clearButtonStart -> isClearButtonClicked = true
                        }
                    }
                }
                when {
                    isClearButtonClicked -> when {
                        event.action == MotionEvent.ACTION_DOWN -> {
                            mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_close, null) as Drawable
                            showClearButton()
                            return@OnTouchListener true
                        }
                        event.action == MotionEvent.ACTION_UP -> {
                            mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_close, null) as Drawable
                            when {
                                text != null -> text?.clear()
                            }
                            hideClearButton()
                            onClear.onClear()
                            return@OnTouchListener true
                        }
                        else -> return@OnTouchListener false
                    }
                    else -> return@OnTouchListener false
                }
            }
            false
        })
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                when {
                    !s.toString().isEmpty() -> showClearButton()
                }
            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
    private fun showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, // Top of text.
            mClearButtonImage, null)// Start of text.
    }
    private fun hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(mSearchImage, null, null, null)// Start of text.
    }

    interface OnClear{
        fun onClear()
    }
}