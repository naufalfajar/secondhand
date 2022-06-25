package id.fajarjudo.binar.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import id.fajarjudo.binar.login.databinding.FragmentLoginBinding
import id.fajarjudo.binar.login.databinding.FragmentRegisterBinding


class register : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val registerBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding = registerBinding
        return registerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvOrOptions.setOnClickListener { tologin() }
        }
    }
    private fun tologin() {
        findNavController().navigate(R.id.action_register_to_login)
    }

}