package com.poly.ui.fragment.home

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.poly.core.data.Resource
import com.poly.core.data.source.remote.response.Media
import com.poly.core.viewmodel.MomentViewModel
import com.poly.databinding.FragmentAddMomentBinding
import com.poly.ui.adapter.MediaAdapter
import com.poly.ui.fragment.auth.ProgressDialogFragment
import com.poly.utils.*
import com.poly.utils.Constant.USER_ID
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File


class AddMomentFragment : Fragment(), View.OnClickListener {

    private val momentViewModel: MomentViewModel by viewModel()
    private lateinit var files: ArrayList<MultipartBody.Part>
    private lateinit var mOptionDialogFragment: PreviewMomentFragment
    private lateinit var mediaAdapter: MediaAdapter
    private var getFile: File? = null
    private var _binding: FragmentAddMomentBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddMomentBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tvCameraOrVideo?.setOnClickListener(this)
        binding?.tvLocation?.setOnClickListener(this)
        binding?.btnPost?.setOnClickListener(this)

        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSION,
                REQUEST_CODE_PERMISSIONS
            )
        }

        mOptionDialogFragment = PreviewMomentFragment()
        mediaAdapter = MediaAdapter()

        binding?.rvMedia?.apply {
            adapter = mediaAdapter
            setHasFixedSize(true)
        }
        mediaAdapter.onItemClick = {
            val mFragmentManager = this.childFragmentManager
            mOptionDialogFragment = PreviewMomentFragment.newInstance(it.path, it.type)
            mOptionDialogFragment.show(
                mFragmentManager,
                ProgressDialogFragment::class.java.simpleName
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionGranted()) {
                requireContext().showToast("Tidak Mendapatkan Permission")
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(
            requireActivity().baseContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.tvCameraOrVideo -> {
                UwMediaPicker
                    .with(this)
                    .setGalleryMode(UwMediaPicker.GalleryMode.ImageAndVideoGallery)
                    .setGridColumnCount(3)
                    .setMaxSelectableMediaCount(10)
                    .setLightStatusBar(true)
                    .enableImageCompression(true)
                    .setCompressionMaxWidth(1280F)
                    .setCompressionMaxHeight(1280F)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setCompressionQuality(100)
                    .setCancelCallback { }
                    .launch { selectedMediaList ->
                        if (selectedMediaList != null) {
                            files = ArrayList()
                            files.clear()
                            val listMedia = ArrayList<Media.Response>()
                            for (i in selectedMediaList) {
                                getFile = File(i.mediaPath)
                                getFile?.apply {
                                    val typeExtension = if (i.mediaType.toString() == "IMAGE") "image/jpeg" else "video/mp4"
                                    val requestFile = this.asRequestBody(typeExtension.toMediaTypeOrNull())
                                    val fileMultipart = MultipartBody.Part.createFormData(
                                        "files", this.name, requestFile)
                                    files.add(fileMultipart)
                                }
                                val media = Media.Response(i.mediaPath, i.mediaType.toString())
                                listMedia.add(media)
                            }
                            mediaAdapter.setListData(listMedia)
                            binding?.rvMedia?.visible()
                        }
                    }
            }

            binding?.tvLocation -> {

            }

            binding?.btnPost -> {
                val userId = getPreference(requireContext(), USER_ID)
                val location = "Test"
                val description = binding?.edtShareMoment?.text.toString()

                val partUserId = userId.toRequestBody("text/plain".toMediaType())
                val partLocation = location.toRequestBody("text/plain".toMediaType())
                val partDescription = description.toRequestBody("text/plain".toMediaType())

                momentViewModel.postMultiple(partUserId, partLocation, partDescription, files).observe(viewLifecycleOwner) { response ->
                    if (response != null) {
                        when (response) {
                            is Resource.Loading -> {
                                this@AddMomentFragment.showLoading()
                            }
                            is Resource.Success -> {
                                hideLoading()
                                requireContext().showToast("Success")
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