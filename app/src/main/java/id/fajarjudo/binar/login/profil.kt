package id.fajarjudo.binar.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import id.fajarjudo.binar.login.databinding.FragmentLoginBinding
import id.fajarjudo.binar.login.databinding.FragmentProfilBinding
import kotlinx.android.synthetic.main.fragment_profil.*
import kotlinx.coroutines.launch


class profilFragment : Fragment(R.layout.fragment_profil) {

    private lateinit var binding: FragmentProfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profilBinding = FragmentProfilBinding.inflate(inflater, container, false)
        binding = profilBinding
        return profilBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btn_toUpdate.setOnClickListener { toUpdate() }
            btnToexit.setOnClickListener { logout() }
        }
    }
    private fun toUpdate() {
        findNavController().navigate(R.id.action_profilFragment_to_updateprofil2)
    }

    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Yakin ingin keluar?")
            .setPositiveButton("Ya") {_, _->
                findNavController().navigate(R.id.action_profilFragment_to_login2)
            }
            .setNegativeButton("Tidak") {dialog, _->
                dialog.dismiss()
            }
            .show()
    }
}