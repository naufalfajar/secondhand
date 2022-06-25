package id.finalproject.binar.secondhand.fragment.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentProfilBinding


class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnToUpdate.setOnClickListener { toUpdate() }
            btnToexit.setOnClickListener { logout() }
        }
    }
    private fun toUpdate() {
        findNavController().navigate(R.id.action_profilFragment2_to_updateProfilFragment)
    }

    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Yakin ingin keluar?")
            .setPositiveButton("Ya") {_, _->
                findNavController().navigate(R.id.action_profilFragment2_to_loginFragment)
            }
            .setNegativeButton("Tidak") {dialog, _->
                dialog.dismiss()
            }
            .show()
    }
}