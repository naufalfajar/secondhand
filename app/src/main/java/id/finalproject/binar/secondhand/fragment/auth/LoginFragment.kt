package id.finalproject.binar.secondhand.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.finalproject.binar.secondhand.MainActivity
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        binding = loginBinding
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogin.setOnClickListener { toHome() }
            tvOrOptions.setOnClickListener { toRegister() }
        }
    }
    private fun toRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }
    private fun toHome() {
        val intent = Intent(this@LoginFragment.requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}