package com.poly.ui.fragment.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.poly.R
import com.poly.core.data.Resource
import com.poly.core.data.source.remote.response.Auth
import com.poly.core.viewmodel.AuthViewModel
import com.poly.databinding.FragmentRegisterBinding
import com.poly.ui.activity.MainActivity
import com.poly.utils.Constant.USER_ID
import com.poly.utils.hideLoading
import com.poly.utils.putPreference
import com.poly.utils.showLoading
import com.poly.utils.showSnackBar
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(), View.OnClickListener {
    private val authViewModel: AuthViewModel by viewModel()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linkTextPrivacyPolicyAndTermCondition()

        binding?.imgButtonBack?.setOnClickListener(this)
        binding?.btnRegister?.setOnClickListener(this)
    }

    private fun linkTextPrivacyPolicyAndTermCondition() {
        val clickablePrivacyPolicySpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {}

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
            }
        }

        val clickableTermConditionSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {}

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
            }
        }

        val textPrivacyPolicy = SpannableString(getString(R.string.privacy_policy))
        val textTermCondition = SpannableString(getString(R.string.term_condition))

        textPrivacyPolicy.setSpan(
            clickablePrivacyPolicySpan,
            0,
            getString(R.string.privacy_policy).length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textTermCondition.setSpan(
            clickableTermConditionSpan,
            0,
            getString(R.string.term_condition).length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding?.tvAgreement?.apply {
            text = TextUtils.concat(
                getString(R.string.check_box_agreement), " ",
                textPrivacyPolicy, " ",
                getString(R.string.and), " ",
                textTermCondition, " ",
                getString(R.string.us)
            )
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.imgButtonBack -> {
                requireActivity().onBackPressed()
            }
            binding?.btnRegister -> {
                val name = binding?.edtName?.text.toString()
                val phoneNumber = binding?.edtPhoneNumber?.text.toString()
                val password = binding?.edtPassword?.text.toString()

                val requestRegister = Auth.RequestRegister(
                    nama = name,
                    no = phoneNumber,
                    password = password,
                    confirmation_password = password
                )

                if (name.isNotEmpty() && phoneNumber.isNotEmpty() && password.isNotEmpty()) {
                    authViewModel.register(requestRegister).observe(viewLifecycleOwner) { response ->
                        if (response != null) {
                            when (response) {
                                is Resource.Loading -> {
                                    this@RegisterFragment.showLoading()
                                }
                                is Resource.Success -> {
                                    hideLoading()
                                    putPreference(
                                        requireContext(), USER_ID,
                                        response.data?.access_token?.sub.toString()
                                    )
                                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                                    requireActivity().finish()
                                }
                                is Resource.Error -> {
                                    hideLoading()
                                    requireView().showSnackBar(response.message.toString())
                                }
                            }
                        }
                    }
                } else {
                    requireView().showSnackBar(getString(R.string.please_fill_all_field))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}