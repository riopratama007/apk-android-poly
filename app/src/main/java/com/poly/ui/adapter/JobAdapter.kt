package com.poly.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poly.core.data.source.remote.response.Job
import com.poly.databinding.LayoutListJobBinding

class JobAdapter : RecyclerView.Adapter<JobAdapter.HomeViewHolder>() {

    private val datas = ArrayList<Job.Response.Data>()
    var onItemClick: ((Job.Response.Data) -> Unit)? = null
    var onItemMoreClick: ((Job.Response.Data) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Job.Response.Data>?) {
        if (listData == null) return
        datas.clear()
        datas.addAll(listData)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: LayoutListJobBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Job.Response.Data) {
            binding.viewmodel = data
            binding.executePendingBindings()

            binding.imgMore.setOnClickListener {
                onItemMoreClick?.invoke(data)
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            LayoutListJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

}