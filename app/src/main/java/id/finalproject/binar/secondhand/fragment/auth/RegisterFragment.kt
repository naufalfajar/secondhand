package id.finalproject.binar.secondhand.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentRegisterBinding
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.repository.UserRepo
import id.finalproject.binar.secondhand.repository.viewModelsFactory
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val apiService: ApiService by lazy { ApiClient.instance }
    private val userRepo: UserRepo by lazy { UserRepo(apiService) }
    private val regisViewModel: RegisViewModel by viewModelsFactory { RegisViewModel(userRepo) }

    private lateinit var path: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        path = ""
        btnRegis()
        tvLogin()
    }

    private fun btnRegis() {
        binding.apply {
            btnRegis.setOnClickListener {
                val nama = etNama.text.toString()
                val email = etEmail.text.toString()
                val pass = etPass.text.toString()

                when {
                    nama.isEmpty() -> {
                        etNama.error = "Username tidak boleh kosong!"
                    }
                    email.isEmpty() -> {
                        etEmail.error = "Email tidak boleh kosong!"
                    }
                    pass.isEmpty() -> {
                        etPass.error = "Password tidak boleh kosong!"
                    }
                    else -> {
                        regis(
                            nama = nama,
                            email = email,
                            pass = pass,
                            phoneNumber = 0,
                            address = "",
                            city = ""
                        )

                        etNama.text.clear()
                        etEmail.text.clear()
                        etPass.text!!.clear()
                        etNama.requestFocus()
                    }
                }
            }
        }
    }

    private fun regis(
        nama: String,
        email: String,
        pass: String,
        phoneNumber: Int,
        address: String,
        city: String
    ) {
        val file = File(path)
        val reqFile = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
        val nama1 = nama.toRequestBody("text/plain".toMediaType())
        val email1 = email.toRequestBody("text/plain".toMediaType())
        val pass1 = pass.toRequestBody("text/plain".toMediaType())
        val phoneNumber1 = phoneNumber.toString().toRequestBody("text/plain".toMediaType())
        val address1 = address.toRequestBody("text/plain".toMediaType())
        val image1 = MultipartBody.Part.createFormData("image", file.name, reqFile)
        val city1 = city.toRequestBody("text/plain".toMediaType())

        regisViewModel.postRegisUser(
            nama1,
            email1,
            pass1,
            phoneNumber1,
            address1,
            image1,
            city1
        ).observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESS -> {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    Toast.makeText(requireContext(), "Regis Berhasil.", Toast.LENGTH_SHORT).show()
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

    private fun tvLogin() {
        binding.apply {
            tvOrOptions.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }
}