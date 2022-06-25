package id.fajarjudo.binar.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import id.fajarjudo.binar.login.databinding.FragmentProfilBinding
import id.fajarjudo.binar.login.databinding.FragmentUpdateprofilBinding
import kotlinx.android.synthetic.main.fragment_profil.*

class updateprofil : Fragment() {

    private lateinit var binding: FragmentUpdateprofilBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profilupdateBinding = FragmentUpdateprofilBinding.inflate(inflater, container, false)
        binding = profilupdateBinding
        return profilupdateBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textUbahAkun.setOnClickListener { back() }
        }
    }
    private fun back(){
        findNavController().navigate(R.id.action_updateprofil_to_profilFragment2)
    }
}