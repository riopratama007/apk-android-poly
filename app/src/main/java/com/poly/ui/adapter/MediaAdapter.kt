package com.poly.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poly.core.data.source.remote.response.Media
import com.poly.databinding.LayoutListMediaBinding

class MediaAdapter : RecyclerView.Adapter<MediaAdapter.HomeViewHolder>() {

    private val datas = ArrayList<Media.Response>()
    var onItemClick: ((Media.Response) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Media.Response>?) {
        if (listData == null) return
        datas.clear()
        datas.addAll(listData)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: LayoutListMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Media.Response) {
            binding.viewmodel = data
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            LayoutListMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

}