package com.jesse.xtts2.presentation.templates.tema

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesse.xtts2.R
import com.jesse.xtts2.databinding.ItemSmallBinding

class TemAAdapter(private val list: List<String>, private val onclickListener:(String)->Unit) : RecyclerView.Adapter<TemAVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemAVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TemAVH(layoutInflater.inflate(R.layout.item_small, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TemAVH, position: Int) {
        holder.bind(list[position],onclickListener)
    }
}

class TemAVH(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemSmallBinding.bind(view)

    fun bind(s: String, onclickListener: (String) -> Unit) {
        binding.btnGen.text = s
        binding.btnGen.setOnClickListener { onclickListener(s) }
    }
}