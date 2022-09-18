package com.poly.ui.fragment.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.poly.R
import com.poly.databinding.FragmentCollectionBinding
import com.poly.databinding.FragmentCommunityBinding
import com.poly.utils.gone
import com.poly.utils.navigateUp
import com.poly.utils.visible

class CommunityFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.setNavigationOnClickListener {
            navigateUp(it)
        }

        binding?.cardForYou?.setOnClickListener(this)
        binding?.cardYourCommunity?.setOnClickListener(this)
        binding?.cardCreateCommunity?.setOnClickListener(this)
        binding?.btnCreateCommunity?.setOnClickListener(this)

    }

    private fun setColor(colorCardForYou: Int, colorTvForYou: Int, colorCardYourCommunity: Int, colorTvYourCommunity: Int) {
        binding?.apply {
            cardForYou.setCardBackgroundColor(ContextCompat.getColor(requireContext(), colorCardForYou))
            tvForYou.setTextColor(ContextCompat.getColor(requireContext(), colorTvForYou))
            cardYourCommunity.setCardBackgroundColor(ContextCompat.getColor(requireContext(), colorCardYourCommunity))
            tvYourCommunity.setTextColor(ContextCompat.getColor(requireContext(), colorTvYourCommunity))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.cardForYou -> {
                binding?.llForYou?.visible()
                binding?.llYourCommunity?.gone()
                setColor(R.color.colorPrimaryVariant, R.color.white, R.color.white, R.color.colorSecondaryVariant)
            }
            binding?.cardYourCommunity -> {
                binding?.llForYou?.gone()
                binding?.llYourCommunity?.visible()
                setColor(R.color.white, R.color.colorPrimaryVariant, R.color.colorSecondaryVariant, R.color.white)
            }
            binding?.cardCreateCommunity -> {
                findNavController().navigate(CommunityFragmentDirections.actionCommunityFragmentToCreateCommunityFragment())
            }
            binding?.btnCreateCommunity -> {
                findNavController().navigate(CommunityFragmentDirections.actionCommunityFragmentToCreateCommunityFragment())
            }
        }
    }
}