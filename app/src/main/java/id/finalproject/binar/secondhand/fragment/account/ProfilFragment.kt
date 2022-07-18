package id.finalproject.binar.secondhand.fragment.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import id.finalproject.binar.secondhand.AuthActivity
import id.finalproject.binar.secondhand.databinding.FragmentProfilBinding
import id.finalproject.binar.secondhand.helper.SharedPreferences


class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPrefs: SharedPreferences

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
        sharedPrefs = SharedPreferences(requireContext())

        binding.apply {
            btnToUpdate.setOnClickListener { toUpdate() }
            btnToexit.setOnClickListener { logout() }
        }
    }
    private fun toUpdate() {
    }

    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                sharedPrefs.sessionDelete()
                val intent = Intent(this@ProfilFragment.requireContext(), AuthActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton("Tidak") {dialog, _->
                dialog.dismiss()
            }
            .show()
    }
}