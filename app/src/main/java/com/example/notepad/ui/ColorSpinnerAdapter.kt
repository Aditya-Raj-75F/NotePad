package com.example.notepad.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.notepad.R

class ColorSpinnerAdapter(val context: Context, private val itemColors: List<Int>): BaseAdapter() {
    override fun getCount(): Int {
       return  itemColors.size
    }

    override fun getItem(position: Int): Any {
        return itemColors[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        var inflater = LayoutInflater.from(context)
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.color_spinner_item, parent, false)
    }
        val imageView = convertView!!.findViewById<ImageView>(R.id.circleBackground)
        imageView.setImageDrawable(createOvalShape(getItem(position) as Int))
        return convertView
}

    @SuppressLint("ResourceAsColor")
    fun createOvalShape(color: Int): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.OVAL
        gradientDrawable.setStroke(2, R.color.black)
        gradientDrawable.setColor(color)
        return gradientDrawable
    }
}