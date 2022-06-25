package id.finalproject.binar.secondhand.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

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
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

}