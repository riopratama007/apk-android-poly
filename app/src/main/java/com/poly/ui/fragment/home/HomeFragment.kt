package com.poly.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.poly.core.data.Resource
import com.poly.core.viewmodel.BusinessViewModel
import com.poly.core.viewmodel.MomentViewModel
import com.poly.databinding.FragmentHomeBinding
import com.poly.ui.adapter.MomentAdapter
import com.poly.ui.adapter.SliderMomentAdapter
import com.poly.ui.fragment.main.MainFragmentDirections
import com.poly.utils.Datas.generateDataSliderMoment
import com.poly.utils.gone
import com.poly.utils.hideLoading
import com.poly.utils.showLoading
import com.poly.utils.visible
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var momentAdapter: MomentAdapter
    private val momentViewModel: MomentViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.layoutHomeMemories?.btnShareYourMoment?.setOnClickListener(this)
        binding?.layoutHomeMemories?.menuContentCreative?.setOnClickListener(this)
        binding?.layoutHomeMemories?.menuVideo?.setOnClickListener(this)
        binding?.layoutHomeMemories?.menuCommunity?.setOnClickListener(this)
        binding?.layoutHomeMemories?.menuAds?.setOnClickListener(this)

        loadMoment()
    }

    private fun loadMoment() {
        momentAdapter = MomentAdapter()
        binding?.layoutHomeMoment?.rvMoment?.adapter = momentAdapter

        momentViewModel.moment().observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    is Resource.Loading -> {
                        this@HomeFragment.showLoading()
                    }
                    is Resource.Success -> {
                        if (response.data?.data?.isNotEmpty() == true) {
                            momentAdapter.setListData(response.data.data)
                        }
                        hideLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()
                    }
                }
            }
        }
        momentAdapter.onItemClick = {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.layoutHomeMemories?.btnShareYourMoment -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddMomentFragment())
            }
            binding?.layoutHomeMemories?.menuContentCreative -> {

            }
            binding?.layoutHomeMemories?.menuVideo -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToVideoFragment())
            }
            binding?.layoutHomeMemories?.menuCommunity -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToCommunityFragment())
            }
            binding?.layoutHomeMemories?.menuAds -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToAdsFragment())
            }
        }
    }
}