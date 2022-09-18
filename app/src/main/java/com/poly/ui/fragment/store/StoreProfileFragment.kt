package com.poly.ui.fragment.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.poly.R
import com.poly.core.data.source.remote.response.Store
import com.poly.databinding.FragmentAddStoreBinding
import com.poly.databinding.FragmentStoreProfileBinding
import com.poly.utils.navigateUp
import com.poly.utils.showToast

class StoreProfileFragment : Fragment() {
    private var _binding: FragmentStoreProfileBinding? = null
    private val binding get() = _binding
    private val args: StoreProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreProfileBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.setNavigationOnClickListener {
            navigateUp(it)
        }

        binding?.toolbar?.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_edit) {
                val action = StoreProfileFragmentDirections.actionStoreProfileFragmentToAddStoreFragment()
                action.type = getString(R.string.edit)
                action.dataStore = args.dataStore
                findNavController().navigate(action)
            }
            true
        }

        populateView()
    }

    private fun populateView() {
        binding?.apply {
            args.dataStore?.apply {
                tvStoreName.text = this.noHp
                tvStoreDescription.text = this.description
                tvStoreAddress.text = this.alamat
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}