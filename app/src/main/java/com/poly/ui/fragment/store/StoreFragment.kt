package com.poly.ui.fragment.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.poly.R
import com.poly.core.data.Resource
import com.poly.core.data.source.remote.response.Store
import com.poly.core.viewmodel.StoreViewModel
import com.poly.databinding.FragmentStoreBinding
import com.poly.ui.fragment.main.MainFragmentDirections
import com.poly.utils.Constant.USER_ID
import com.poly.utils.getPreference
import com.poly.utils.hideLoading
import com.poly.utils.showLoading
import com.poly.utils.showSnackBar
import org.koin.android.viewmodel.ext.android.viewModel

class StoreFragment : Fragment(), View.OnClickListener {

    private var dataStore: Store.Response.Data.Result? = null
    private val storeViewModel: StoreViewModel by viewModel()
    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding
    private var userId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = getPreference(requireContext(), USER_ID)

        binding?.imgAddProduct?.setOnClickListener(this)
        binding?.imgProfileProduct?.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.imgAddProduct -> {
                checkStore().observe(viewLifecycleOwner) {
                    if (it == true) {
                        findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddProductFragment())
                    } else {
                        showAlertOpenStore()
                    }
                }
            }
            binding?.imgProfileProduct -> {
                checkStore().observe(viewLifecycleOwner) {
                    if (it == true) {
                        val action = MainFragmentDirections.actionMainFragmentToStoreProfileFragment()
                        action.dataStore = dataStore
                        findNavController().navigate(action)
                    } else {
                        showAlertOpenStore()
                    }
                }
            }
        }
    }

    private fun showAlertOpenStore() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.store_not_found))
            .setMessage(getString(R.string.store_not_found_description))
            .setNegativeButton(getString(R.string.later)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                val action = MainFragmentDirections.actionMainFragmentToAddStoreFragment()
                action.type = getString(R.string.add)
                action.dataStore = dataStore
                findNavController().navigate(action)
                dialog.cancel()
            }
            .show()
    }

    private fun checkStore(): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        storeViewModel.store(userId).observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    is Resource.Loading -> {
                        this@StoreFragment.showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        response.data?.data?.result?.let { dataStore = it }
                        status.value = response.data?.data?.result != null
                    }
                    is Resource.Error -> {
                        hideLoading()
                        status.value = false
                        requireView().showSnackBar(response.message.toString())
                    }
                }
            }
        }
        return status
    }
}