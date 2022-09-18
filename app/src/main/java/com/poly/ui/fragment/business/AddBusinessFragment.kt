package com.poly.ui.fragment.business

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
import com.poly.core.data.source.remote.response.Business
import com.poly.core.data.source.remote.response.Service
import com.poly.core.viewmodel.BusinessViewModel
import com.poly.core.viewmodel.ServiceViewModel
import com.poly.databinding.FragmentAddBusinessBinding
import com.poly.utils.*
import com.poly.utils.Constant.IMAGE_ONE
import com.poly.utils.Constant.BUSINESS_PROFILE_IMAGE
import com.poly.utils.Constant.USER_ID
import org.koin.android.viewmodel.ext.android.viewModel

class AddBusinessFragment : Fragment(), View.OnClickListener {

    private val serviceViewModel: ServiceViewModel by viewModel()
    private val businessViewModel: BusinessViewModel by viewModel()
    private var _binding: FragmentAddBusinessBinding? = null
    private val binding get() = _binding
    private val args: AddBusinessFragmentArgs by navArgs()
    private var isUploadNewPhoto = false
    private var pathImage = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBusinessBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.toolbar?.setNavigationOnClickListener { navigateUp(it) }
        binding?.imgBusiness?.setOnClickListener(this)
        binding?.btnAdd?.setOnClickListener(this)

        if (args.type == getString(R.string.edit)) {
            args.dataBusiness?.let {
                populateView(it)
            }
        }
    }

    private fun populateView(data: Business.Response.Data) {
        binding?.apply {
            IMAGE_ONE = data.file
            imgBusiness.loadImage(IMAGE_ONE)
            edtDescription.setText(data.keterangan)
            edtAddress.setText(data.lokasi)
            btnAdd.text = getString(R.string.edit_business)
            toolbar.title = getString(R.string.edit_business)
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
                        binding?.progressCircular?.gone()
                        binding?.imgBusiness?.loadImageFromUri(imageUri)
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
            val description = edtDescription.text.toString()
            val address = edtAddress.text.toString()
            val userId = getPreference(requireContext(), USER_ID)

            if (IMAGE_ONE != "" && description.isNotEmpty() && address.isNotEmpty()) {
                if (isUploadNewPhoto) {
                    val request = Service.Request(IMAGE_ONE, BUSINESS_PROFILE_IMAGE)
                    serviceViewModel.serviceFileImage(request).observe(viewLifecycleOwner) { response ->
                        if (response != null) {
                            when (response) {
                                is Resource.Loading -> {
                                    this@AddBusinessFragment.showLoading()
                                }
                                is Resource.Success -> {
                                    pathImage = response.data?.result?.image_path.toString()
                                    postBusiness(pathImage, description, address, userId)
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
                    pathImage = args.dataBusiness?.file.toString()
                    postBusiness(pathImage, description, address, userId)
                }
            }
        }
    }

    private fun postBusiness(pathImage: String, description: String, address: String, userId: String) {
        val request = Business.Request(description, pathImage, address, userId)
        if (args.type != getString(R.string.edit)) {
            businessViewModel.business(request).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            hideLoading()
                            requireView().showSnackBar(getString(R.string.business_success_added))
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
            businessViewModel.business(args.dataBusiness?.id.toString(), request).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> {
                            this@AddBusinessFragment.showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()
                            requireView().showSnackBar(getString(R.string.business_success_edited))
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
        val action = AddBusinessFragmentDirections.actionAddBusinessFragmentToMainFragment()
        action.index = 1
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.imgBusiness -> {
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