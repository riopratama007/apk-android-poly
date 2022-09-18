package com.poly.ui.fragment.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.poly.databinding.FragmentPreviewMomentBinding
import com.poly.utils.visible
import java.io.File

class PreviewMomentFragment : DialogFragment() {
    private var _binding: FragmentPreviewMomentBinding? = null
    private val binding get() = _binding

    companion object {
        fun newInstance(path: String?, type: String?) = PreviewMomentFragment().apply {
            arguments = Bundle().apply {
                putString("path", path)
                putString("type", type)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPreviewMomentBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val path = checkNotNull(arguments?.getString("path"))
        val type = checkNotNull(arguments?.getString("type"))

        binding?.apply {
            if (type == "IMAGE") {
                Glide.with(requireContext())
                    .load(Uri.fromFile(File(path)))
                    .into(imgPreviewMedia)
                imgPreviewMedia.visible()
            } else {
                vidPreviewMedia.apply {
                    val mediaController = MediaController(requireContext())
                    mediaController.setAnchorView(this)
                    setMediaController(mediaController)
                    keepScreenOn = true
                    setVideoPath(path)
                    start()
                    requestFocus()
                }
                vidPreviewMedia.visible()
            }
        }
        binding?.imgClearMedia?.setOnClickListener {
            dismiss()
        }

        isCancelable = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}