package com.poly.ui.fragment.job

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
import com.poly.core.data.source.remote.response.Job
import com.poly.core.viewmodel.JobViewModel
import com.poly.databinding.BottomSheetMoreBinding
import com.poly.databinding.FragmentJobBinding
import com.poly.ui.adapter.JobAdapter
import com.poly.ui.fragment.main.MainFragmentDirections
import com.poly.utils.*
import org.koin.android.viewmodel.ext.android.viewModel

class JobFragment : Fragment(), View.OnClickListener {

    private lateinit var jobAdapter: JobAdapter
    private val jobViewModel: JobViewModel by viewModel()
    private var _binding: FragmentJobBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentJobBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobAdapter = JobAdapter()
        binding?.rvJob?.apply {
            setHasFixedSize(true)
            adapter = jobAdapter
        }
        jobAdapter.onItemMoreClick = {
            showBottomMore(it)
        }

        binding?.imgAdd?.setOnClickListener(this)
    }

    private fun loadLowongan() {
        binding?.apply {
            jobViewModel.job().observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> {
                            this@JobFragment.showLoading()
                        }
                        is Resource.Success -> {
                            if (response.data?.data?.isNotEmpty() == true) {
                                jobAdapter.setListData(response.data.data)
                                rvJob.visible()
                                tvNotFound.gone()
                                lottieNotFound.gone()
                            } else {
                                rvJob.gone()
                                tvNotFound.visible()
                                lottieNotFound.visible()
                            }
                            hideLoading()
                        }
                        is Resource.Error -> {
                            rvJob.gone()
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

    private fun showBottomMore(data: Job.Response.Data) {
        val dialog = BottomSheetDialog(requireContext())
        val binding = BottomSheetMoreBinding.inflate(layoutInflater)

        binding.tvEdit.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAddJobFragment()
            action.dataJob = data
            action.type = getString(R.string.edit)
            findNavController().navigate(action)
            dialog.cancel()
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
                    jobViewModel.job(data.id).observe(viewLifecycleOwner) { response ->
                        if (response != null) {
                            when (response) {
                                is Resource.Loading -> {
                                    this@JobFragment.showLoading()
                                }
                                is Resource.Success -> {
                                    hideLoading()
                                    requireView().showSnackBar(getString(R.string.job_success_deleted))
                                    loadLowongan()
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
        loadLowongan()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.imgAdd -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddJobFragment())
            }
        }
    }
}