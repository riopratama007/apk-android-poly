package com.poly.ui.fragment.job

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.dhaval2404.imagepicker.ImagePicker
import com.poly.R
import com.poly.core.data.Resource
import com.poly.core.data.source.remote.response.Job
import com.poly.core.data.source.remote.response.Service
import com.poly.core.viewmodel.JobViewModel
import com.poly.core.viewmodel.ServiceViewModel
import com.poly.databinding.FragmentAddJobBinding
import com.poly.utils.*
import com.poly.utils.Constant.IMAGE_ONE
import com.poly.utils.Constant.JOB_PROFILE_IMAGE
import com.poly.utils.Constant.USER_ID
import org.koin.android.viewmodel.ext.android.viewModel

class AddJobFragment : Fragment(), View.OnClickListener {

    private val serviceViewModel: ServiceViewModel by viewModel()
    private val jobViewModel: JobViewModel by viewModel()
    private var _binding: FragmentAddJobBinding? = null
    private val binding get() = _binding
    private val args: AddJobFragmentArgs by navArgs()
    private var isUploadNewPhoto = false
    private var pathImage = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddJobBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.toolbar?.setNavigationOnClickListener { navigateUp(it) }
        binding?.imgJob?.setOnClickListener(this)
        binding?.btnAdd?.setOnClickListener(this)

        if (args.type == getString(R.string.edit)) {
            args.dataJob?.let {
                populateView(it)
            }
        }
    }

    private fun populateView(data: Job.Response.Data) {
        binding?.apply {
            IMAGE_ONE = data.file
            imgJob.loadImage(IMAGE_ONE)
            edtTitle.setText(data.judul)
            edtDescription.setText(data.deskripsi)
            edtAddress.setText(data.alamat)
            btnAdd.text = getString(R.string.edit_job)
            toolbar.title = getString(R.string.edit_job)
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    try {
                        val imageUri = data?.data
                        val file = data?.data?.toFile()
                        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ImageDecoder.decodeBitmap(ImageDecoder.createSource(file!!))
                        } else {
                            BitmapFactory.decodeFile(imageUri.toString())
                        }
                        IMAGE_ONE = encodeImage(bitmap)
                        binding?.imgJob?.loadImageFromUri(imageUri)
                        binding?.progressCircular?.gone()
                        isUploadNewPhoto = true
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding?.progressCircular?.gone()
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    requireContext().showToast(ImagePicker.getError(data))
                    binding?.progressCircular?.gone()
                }
                else -> {
                    requireContext().showToast(getString(R.string.canceled))
                    binding?.progressCircular?.gone()
                }
            }
        }

    private fun getServiceFileImage() {
        binding?.apply {
            val title = edtTitle.text.toString()
            val description = edtDescription.text.toString()
            val address = edtAddress.text.toString()
            val userId = getPreference(requireContext(), USER_ID)

            if (IMAGE_ONE != "" && title.isNotEmpty() && description.isNotEmpty() && address.isNotEmpty()) {
                if (isUploadNewPhoto) {
                    val request = Service.Request(IMAGE_ONE, JOB_PROFILE_IMAGE)
                    serviceViewModel.serviceFileImage(request).observe(viewLifecycleOwner) { response ->
                        if (response != null) {
                            when (response) {
                                is Resource.Loading -> {
                                    this@AddJobFragment.showLoading()
                                }
                                is Resource.Success -> {
                                    pathImage = response.data?.result?.image_path.toString()
                                    postLowongan(pathImage, title, description, address, userId)
                                }
                                is Resource.Error -> {
                                    hideLoading()
                                    requireView().showSnackBar(
                                        getString(
                                            R.string.error_upload_image,
                                            response.message.toString()
                                        )
                                    )
                                }
                            }
                        }
                    }
                } else {
                    pathImage = args.dataJob?.file.toString()
                    postLowongan(pathImage, title, description, address, userId)
                }
            }
        }
    }

    private fun postLowongan(pathImage: String, title: String, description: String, address: String, userId: String) {
        val request = Job.Request(pathImage, title, description, address, userId)
        if (args.type != getString(R.string.edit)) {
            jobViewModel.job(request).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            hideLoading()
                            requireView().showSnackBar(getString(R.string.job_success_added))
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
            jobViewModel.job(args.dataJob?.id.toString(), request).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> {
                            this@AddJobFragment.showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()
                            requireView().showSnackBar(getString(R.string.job_success_edited))
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

    private fun backToMainFragment() {
        val action = AddJobFragmentDirections.actionAddJobFragmentToMainFragment()
        action.index = 5
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.imgJob -> {
                ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                        binding?.progressCircular?.visible()
                    }
            }
            binding?.btnAdd -> {
                getServiceFileImage()
            }
        }
    }
}