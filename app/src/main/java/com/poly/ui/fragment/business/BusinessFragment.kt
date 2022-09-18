package com.poly.ui.fragment.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.poly.R
import com.poly.core.data.Resource
import com.poly.core.data.source.remote.response.Business
import com.poly.core.viewmodel.BusinessViewModel
import com.poly.databinding.BottomSheetMoreBinding
import com.poly.databinding.FragmentBusinessBinding
import com.poly.ui.adapter.BusinessAdapter
import com.poly.ui.fragment.main.MainFragmentDirections
import com.poly.utils.*
import org.koin.android.viewmodel.ext.android.viewModel

class BusinessFragment : Fragment(), View.OnClickListener {

    private lateinit var businessAdapter: BusinessAdapter
    private val businessViewModel: BusinessViewModel by viewModel()
    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBusinessBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        businessAdapter = BusinessAdapter()
        binding?.rvBusiness?.apply {
            setHasFixedSize(true)
            adapter = businessAdapter
        }
        businessAdapter.onItemMoreClick = {
            showBottomMore(it)
        }

        binding?.imgAdd?.setOnClickListener(this)
    }

    private fun loadBusiness() {
        binding?.apply {
            businessViewModel.business().observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> {
                            this@BusinessFragment.showLoading()
                        }
                        is Resource.Success -> {
                            if (response.data?.data?.isNotEmpty() == true) {
                                businessAdapter.setListData(response.data.data)
                                rvBusiness.visible()
                                tvNotFound.gone()
                                lottieNotFound.gone()
                            } else {
                                rvBusiness.gone()
                                tvNotFound.visible()
                                lottieNotFound.visible()
                            }
                            hideLoading()
                        }
                        is Resource.Error -> {
                            rvBusiness.gone()
                            tvNotFound.visible()
                            lottieNotFound.visible()
                            tvNotFound.text = response.message
                            hideLoading()
                        }
                    }
                }
            }
        }
    }

    private fun showBottomMore(data: Business.Response.Data) {
        val dialog = BottomSheetDialog(requireContext())
        val binding = BottomSheetMoreBinding.inflate(layoutInflater)

        binding.tvEdit.setOnClickListener {
            dialog.cancel()
            val action = MainFragmentDirections.actionMainFragmentToAddBusinessFragment()
            action.dataBusiness = data
            action.type = getString(R.string.edit)
            findNavController().navigate(action)
        }

        binding.tvDelete.setOnClickListener {
            dialog.cancel()
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.confirmation_delete_title))
                .setMessage(getString(R.string.confirmation_delete_description))
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                    dialog.cancel()
                    businessViewModel.business(data.id).observe(viewLifecycleOwner) { response ->
                        if (response != null) {
                            when (response) {
                                is Resource.Loading -> {
                                    this@BusinessFragment.showLoading()
                                }
                                is Resource.Success -> {
                                    hideLoading()
                                    requireView().showSnackBar(getString(R.string.business_success_deleted))
                                    loadBusiness()
                                }
                                is Resource.Error -> {
                                    hideLoading()
                                    requireView().showSnackBar(response.message.toString())
                                }
                            }
                        }
                    }
                }
                .show()
        }
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        loadBusiness()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.imgAdd -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddBusinessFragment())
            }
        }
    }
}