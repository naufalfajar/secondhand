package id.finalproject.binar.secondhand.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.MainActivity
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentLoginBinding
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.model.network.request.LoginRequest
import id.finalproject.binar.secondhand.repository.UserRepository
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.viewmodel.LoginViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    private val apiService: ApiService by lazy { ApiClient.instance }
    private val userRepository: UserRepository by lazy { UserRepository(apiService) }
//    private val loginViewModel: LoginViewModel by viewModelsFactory { LoginViewModel(userRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin()
        tvRegis()
    }

    private fun btnLogin() {
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val pass = etPass.text.toString()

                when {
                    email.isEmpty() -> {
                        etEmail.error = "Email tidak boleh kosong!"
                    }
                    pass.isEmpty() -> {
                        etPass.error ="Password tidak boleh kosong!"
                    }
                    else -> {
                        val userReq = LoginRequest(email, pass)
                        login(userReq, email)

                        etEmail.text.clear()
                        etPass.text!!.clear()
                        etEmail.requestFocus()
                    }
                }
            }
        }
    }

    private fun login(req: LoginRequest, email: String) {
        loginViewModel.postLoginUser(req).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    loginViewModel.saveDataLogin(it.data!!.accessToken, email)

                    val intent =
                        Intent(this@LoginFragment.requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()

                    it.status.name.let { it1 ->
                        Toast.makeText(requireContext(), it1, Toast.LENGTH_SHORT).show()
                    }
                }
                Status.ERROR -> {
                    it.message.let { it1 ->
                        Toast.makeText(requireContext(), it1, Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {}
            }
        }
    }

    private fun tvRegis() {
        binding.apply {
            tvReg.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }
}
