package id.fajarjudo.binar.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import id.fajarjudo.binar.login.databinding.FragmentLoginBinding


class login : Fragment() {

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
        findNavController().navigate(R.id.action_login_to_register)
    }
    private fun toHome() {
        findNavController().navigate(R.id.action_login_to_home2)
    }
}