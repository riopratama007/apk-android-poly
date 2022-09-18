package com.poly.ui.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.poly.core.data.Resource
import com.poly.core.data.source.remote.response.Auth
import com.poly.core.viewmodel.AuthViewModel
import com.poly.databinding.FragmentLoginBinding
import com.poly.ui.activity.MainActivity
import com.poly.utils.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(), View.OnClickListener {

    private val authViewModel: AuthViewModel by viewModel()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tvRegister?.setOnClickListener(this)
        binding?.btnLogin?.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.tvRegister -> {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                )
            }
            binding?.btnLogin -> {
                binding?.apply {
                    val phoneNumber = edtPhoneNumber.text.toString()
                    val password = edtPassword.text.toString()
                    if (phoneNumber.isNotEmpty() && password.isNotEmpty()) {
                        val request = Auth.RequestLogin(phoneNumber, password)
                        authViewModel.login(request).observe(viewLifecycleOwner) { response ->
                            if (response != null) {
                                when (response) {
                                    is Resource.Loading -> {
                                        this@LoginFragment.showLoading()
                                    }
                                    is Resource.Success -> {
                                        hideLoading()
                                        putPreference(
                                            requireContext(), Constant.USER_ID,
                                            response.data?.access_token?.sub.toString()
                                        )
                                        startActivity(
                                            Intent(
                                                requireActivity(),
                                                MainActivity::class.java
                                            )
                                        )
                                        requireActivity().finish()
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
    }
}