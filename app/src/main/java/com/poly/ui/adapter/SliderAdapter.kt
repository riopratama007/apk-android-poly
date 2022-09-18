package com.poly.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poly.databinding.LayoutSliderBinding
import com.poly.utils.Datas

class SliderAdapter : RecyclerView.Adapter<SliderAdapter.HomeViewHolder>() {

    private val datas = ArrayList<Datas.Slider>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Datas.Slider>?) {
        if (listData == null) return
        datas.clear()
        datas.addAll(listData)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: LayoutSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Datas.Slider) {
            binding.data = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            LayoutSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

}