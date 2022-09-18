package com.poly.ui.fragment.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.poly.R
import com.poly.databinding.FragmentCommunityBinding
import com.poly.databinding.FragmentVideoBinding
import com.poly.ui.fragment.community.CommunityFragmentDirections
import com.poly.utils.Constant.DUMMY_URL_VIDEO
import com.poly.utils.gone
import com.poly.utils.navigateUp
import com.poly.utils.visible

class VideoFragment : Fragment() {
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.setNavigationOnClickListener {
            navigateUp(it)
        }

        val player = ExoPlayer.Builder(requireContext()).build()
        binding?.videoView?.player = player

        val mediaItem = MediaItem.fromUri(DUMMY_URL_VIDEO)
        player.setMediaItem(mediaItem)
        player.prepare()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}