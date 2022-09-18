package com.poly.ui.fragment.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.poly.R
import com.poly.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding
    private val args: MainFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            val sectionsPagerAdapter = SectionPagerAdapter(requireActivity() as AppCompatActivity)
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.icon = ContextCompat.getDrawable(requireContext(), TAB_ICON[position])
            }.attach()
        }
    }

    override fun onResume() {
        super.onResume()
        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                if (args.index != 0) {
                    binding?.tabs?.getTabAt(args.index)?.select()
                }
            }, 100)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAB_ICON = intArrayOf(
            R.drawable.ic_home,
            R.drawable.ic_business,
            R.drawable.ic_store,
            R.drawable.ic_collection,
            R.drawable.ic_profession,
            R.drawable.ic_job,
        )
    }
}