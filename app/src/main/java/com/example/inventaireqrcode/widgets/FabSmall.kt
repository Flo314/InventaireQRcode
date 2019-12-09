package com.example.inventaireqrcode.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.inventaireqrcode.R
import kotlinx.android.synthetic.main.view_fab_small.view.*

class FabSmall(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    val labelView: TextView
    get() = label

    var offsetYAnimation = 0.0f

    init {
        View.inflate(context, R.layout.view_fab_small, this)
        // récupération des attributes à partir de view_fab_small
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FabSmall)
        label.text = attributes.getString(R.styleable.FabSmall_name)
        fabSmall.setImageResource(attributes.getResourceId(R.styleable.FabSmall_iconSrc, R.mipmap.ic_launcher))
        offsetYAnimation = attributes.getDimension(R.styleable.FabSmall_offset_y, offsetYAnimation)

        attributes.recycle()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        fabSmall.setOnClickListener(l)
    }
}