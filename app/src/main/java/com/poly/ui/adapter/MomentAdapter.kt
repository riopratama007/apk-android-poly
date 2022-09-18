package com.poly.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poly.R
import com.poly.core.data.source.remote.response.Moment
import com.poly.databinding.LayoutListMomentBinding
import com.poly.utils.gone
import com.poly.utils.loadImagePost
import com.poly.utils.visible

class MomentAdapter : RecyclerView.Adapter<MomentAdapter.HomeViewHolder>() {

    private val datas = ArrayList<Moment.Response.Data>()
    var onItemClick: ((Moment.Response.Data) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Moment.Response.Data>?) {
        if (listData == null) return
        datas.clear()
        datas.addAll(listData)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: LayoutListMomentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Moment.Response.Data) {
            binding.apply {
                viewmodel = data
                executePendingBindings()

                if (data.postFiles.isNotEmpty()) {
                    when (data.postFiles.size) {
                        1 -> {
                            imgPost.visible()
                            imgPost2.gone()
                            frameMoreImage.gone()

                            imgPost.loadImagePost("post/", data.postFiles[0].file_url)
                        }
                        2 -> {
                            imgPost.visible()
                            imgPost2.visible()
                            frameMoreImage.gone()

                            imgPost.loadImagePost("post/", data.postFiles[0].file_url)
                            imgPost2.loadImagePost("post/", data.postFiles[1].file_url)

                            imgPost.layoutParams.width = layout.width / 2
                            imgPost2.layoutParams.width = layout.width / 2
                        }
                        3 -> {
                            imgPost.visible()
                            imgPost2.visible()
                            frameMoreImage.visible()

                            imgPost.loadImagePost("post/", data.postFiles[0].file_url)
                            imgPost2.loadImagePost("post/", data.postFiles[1].file_url)
                            imgPost3.loadImagePost("post/", data.postFiles[2].file_url)

                            tvCountMoreFiles.text = itemView.context.getString(R.string.more_files_count, data.postFiles.size - 3)
                        }
                    }
                }
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            LayoutListMomentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

}