package com.poly.ui.fragment.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.poly.databinding.FragmentSliderBinding
import com.poly.ui.adapter.SliderAdapter
import com.poly.utils.Datas.generateDataSlider
import com.poly.utils.gone
import com.poly.utils.invisible
import com.poly.utils.visible

class SliderFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentSliderBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSliderBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            val sliderAdapter = SliderAdapter()
            sliderAdapter.setListData(requireContext().generateDataSlider())

            viewPager2.apply {
                adapter = sliderAdapter
                viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                indicator.setViewPager(viewPager2)

                registerOnPageChangeCallback(object : OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        if (position == 2) llButton.visible()
                        else llButton.invisible()
                    }
                })
            }
        }

        binding?.btnRegister?.setOnClickListener(this)
        binding?.btnLogin?.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.btnRegister -> {
                findNavController().navigate(SliderFragmentDirections.actionSliderFragmentToRegisterFragment())
            }
            binding?.btnLogin -> {
                findNavController().navigate(SliderFragmentDirections.actionSliderFragmentToLoginFragment())
            }
        }
    }
}