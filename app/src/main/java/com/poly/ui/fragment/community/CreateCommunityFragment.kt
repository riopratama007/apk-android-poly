package com.poly.ui.fragment.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.poly.R
import com.poly.databinding.FragmentCommunityBinding
import com.poly.databinding.FragmentCreateCommunityBinding
import com.poly.utils.gone
import com.poly.utils.navigateUp
import com.poly.utils.visible

class CreateCommunityFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCreateCommunityBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateCommunityBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.setNavigationOnClickListener {
            navigateUp(it)
        }

        binding?.btnCreateCommunity?.setOnClickListener(this)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.btnCreateCommunity -> {

            }
        }
    }
}