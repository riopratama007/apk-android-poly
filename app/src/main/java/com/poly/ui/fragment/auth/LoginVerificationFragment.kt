package com.poly.ui.fragment.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding2.widget.RxTextView
import com.poly.R
import com.poly.databinding.FragmentLoginVerificationBinding
import com.poly.ui.activity.MainActivity
import com.poly.utils.addCountryCodeInPhoneNumber
import io.reactivex.Observable

class LoginVerificationFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginVerificationBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginVerificationBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialOtpCodeView()
        countDownTimeResendVerificationCode()

        binding?.imgButtonBack?.setOnClickListener(this)
        binding?.tvResendCode?.setOnClickListener(this)
    }

    @SuppressLint("CheckResult")
    private fun initialOtpCodeView() {
        binding?.apply {
            tvGuide.text = getString(R.string.input_otp_code, addCountryCodeInPhoneNumber("021"))

            edtOtp1.requestFocus()
            val codeOtp1 = RxTextView.textChanges(edtOtp1)
                .skipInitialValue()
                .map {
                    it.isNotEmpty()
                }
            codeOtp1.subscribe {
                if (it == true) {
                    requestEdtFocus(2)
                }
            }

            val codeOtp2 = RxTextView.textChanges(edtOtp2)
                .skipInitialValue()
                .map {
                    it.isNotEmpty()
                }
            codeOtp2.subscribe {
                if (it == true) {
                    requestEdtFocus(3)
                } else {
                    requestEdtFocus(1)
                }
            }

            val codeOtp3 = RxTextView.textChanges(edtOtp3)
                .skipInitialValue()
                .map {
                    it.isNotEmpty()
                }
            codeOtp3.subscribe {
                if (it == true) {
                    requestEdtFocus(4)
                } else {
                    requestEdtFocus(2)
                }
            }

            val codeOtp4 = RxTextView.textChanges(edtOtp4)
                .skipInitialValue()
                .map {
                    it.isNotEmpty()
                }
            codeOtp4.subscribe {
                if (it == true) {
                    requestEdtFocus(5)
                } else {
                    requestEdtFocus(3)
                }
            }

            val validationOtpCode = Observable.combineLatest(
                codeOtp1,
                codeOtp2,
                codeOtp3,
                codeOtp4) { otp1: Boolean, opt2: Boolean, opt3: Boolean, opt4: Boolean ->
                otp1 && opt2 && opt3 && opt4
            }

            validationOtpCode.subscribe {
                if (it) {
//                    val verifyCode = "${edtOtp1.text}${edtOtp2.text}${edtOtp3.text}${edtOtp4.text}"
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                }
            }
        }
    }

    private fun requestEdtFocus(nextCode: Int) {
        binding?.apply {
            when (nextCode) {
                1 -> {
                    edtOtp1.requestFocus()
                }
                2 -> {
                    edtOtp2.requestFocus()
                }
                3 -> {
                    edtOtp3.requestFocus()
                }
                4 -> {
                    edtOtp4.requestFocus()
                }
            }
        }
    }

    private fun countDownTimeResendVerificationCode() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding?.tvResendCode?.text = getString(R.string.code_not_send_timer, (millisUntilFinished / 1000).toString())
            }

            override fun onFinish() {
                binding?.tvResendCode?.text = getString(R.string.resend_otp_code)
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.imgButtonBack -> {
                requireActivity().onBackPressed()
            }
            binding?.tvResendCode -> {
                if (binding?.tvResendCode?.text.toString() == getString(R.string.resend_otp_code)) {
                    countDownTimeResendVerificationCode()
                }
            }
        }
    }
}