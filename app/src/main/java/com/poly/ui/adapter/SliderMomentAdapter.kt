package com.poly.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.MediaController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.poly.BuildConfig.BASE_URL_IMAGE
import com.poly.core.data.source.remote.response.Moment
import com.poly.databinding.LayoutListMomentMediaBinding
import com.poly.utils.visible
import java.io.File

class SliderMomentAdapter(val context: Context) : RecyclerView.Adapter<SliderMomentAdapter.HomeViewHolder>() {

    private val datas = ArrayList<Moment.Response.Data.PostFiles>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Moment.Response.Data.PostFiles>?) {
        if (listData == null) return
        datas.clear()
        datas.addAll(listData)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: LayoutListMomentMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Moment.Response.Data.PostFiles) {

            binding.tvText.text = data.file_url
            if (data.type == "IMAGE") {
                Glide.with(context)
                    .load(Uri.fromFile(File(BASE_URL_IMAGE+"post/"+data.file_url)))
                    .into(binding.imgPreviewMedia)
                binding.imgPreviewMedia.visible()
            } else {
                binding.vidPreviewMedia.apply {
                    val mediaController = MediaController(context)
                    mediaController.setAnchorView(this)
                    setMediaController(mediaController)
                    keepScreenOn = true
                    setVideoPath(BASE_URL_IMAGE+"post/"+data.file_url)
                    start()
                    requestFocus()
                }
                binding.vidPreviewMedia.visible()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            LayoutListMomentMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

}