package com.poly.ui.fragment.ads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.poly.R
import com.poly.databinding.FragmentAdsBinding
import com.poly.databinding.FragmentVideoBinding
import com.poly.utils.Constant
import com.poly.utils.navigateUp

class AdsFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentAdsBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.setNavigationOnClickListener {
            navigateUp(it)
        }

        binding?.fabAddAds?.setOnClickListener(this)
        binding?.toolbar?.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_ads) {
                findNavController().navigate(AdsFragmentDirections.actionAdsFragmentToAddSponsorAdsFragment())
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.fabAddAds -> {
                findNavController().navigate(AdsFragmentDirections.actionAdsFragmentToAddMyAddFragment())
            }
        }
    }
}