package com.poly.ui.fragment.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.poly.R
import com.poly.core.data.Resource
import com.poly.core.data.source.remote.response.Store
import com.poly.core.viewmodel.StoreViewModel
import com.poly.databinding.FragmentAddStoreBinding
import com.poly.ui.fragment.job.AddJobFragmentDirections
import com.poly.utils.*
import com.poly.utils.Constant.USER_ID
import org.koin.android.viewmodel.ext.android.viewModel

class AddStoreFragment : Fragment(), View.OnClickListener {

    private val storeViewModel: StoreViewModel by viewModel()
    private var _binding: FragmentAddStoreBinding? = null
    private val binding get() = _binding
    private val args: AddStoreFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddStoreBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.setNavigationOnClickListener {
            navigateUp(it)
        }

        binding?.toolbar?.setNavigationOnClickListener { navigateUp(it) }
        binding?.imgStore?.setOnClickListener(this)
        binding?.btnAdd?.setOnClickListener(this)

        if (args.type == getString(R.string.edit)) {
            args.dataStore?.let {
                populateView(it)
            }
        }

    }

    private fun populateView(data: Store.Response.Data.Result) {
        binding?.apply {
            edtStoreName.setText(data.noHp)
            edtDescription.setText(data.description)
            edtAddress.setText(data.alamat)
            btnAdd.text = getString(R.string.edit_store)
            toolbar.title = getString(R.string.edit_store)
        }
    }

    private fun postStore() {
        binding?.apply {
            val name = edtStoreName.text.toString()
            val address = edtAddress.text.toString()
            val description = edtDescription.text.toString()
            val userId = getPreference(requireContext(), USER_ID)
            if (address.isNotEmpty() && description.isNotEmpty()) {
                val request = Store.Request(address, name, description, userId)
                if (args.type != getString(R.string.edit)) {
                    storeViewModel.store(request).observe(viewLifecycleOwner) { response ->
                        if (response != null) {
                            when (response) {
                                is Resource.Loading -> {
                                    this@AddStoreFragment.showLoading()
                                }
                                is Resource.Success -> {
                                    hideLoading()
                                    requireView().showSnackBar(getString(R.string.store_success_created))
                                    backToMainFragment()
                                }
                                is Resource.Error -> {
                                    hideLoading()
                                    requireView().showSnackBar(response.message.toString())
                                }
                            }
                        }
                    }
                } else {
                    storeViewModel.store(args.dataStore?.id.toString(), request).observe(viewLifecycleOwner) { response ->
                        if (response != null) {
                            when (response) {
                                is Resource.Loading -> {
                                    this@AddStoreFragment.showLoading()
                                }
                                is Resource.Success -> {
                                    hideLoading()
                                    requireView().showSnackBar(getString(R.string.store_success_edited))
                                    backToMainFragment()
                                }
                                is Resource.Error -> {
                                    hideLoading()
                                    requireView().showSnackBar(response.message.toString())
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun backToMainFragment() {
        val action = AddStoreFragmentDirections.actionAddStoreFragmentToMainFragment()
        action.index = 2
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.btnAdd -> {
                postStore()
            }
        }
    }
}