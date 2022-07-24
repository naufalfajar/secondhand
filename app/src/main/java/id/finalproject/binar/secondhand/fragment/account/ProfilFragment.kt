package id.finalproject.binar.secondhand.fragment.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.AuthActivity
import id.finalproject.binar.secondhand.ProfileActivity
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentProfilBinding
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.viewmodel.ProfileViewModel

@AndroidEntryPoint
class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

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
        val token = profileViewModel.token.toString()
        observeData(token)
        tvUpdate()
        logout()
    }

    private fun tvUpdate() {
        binding.apply {
            btnToUpdate.setOnClickListener {
                val intent = Intent(this@ProfilFragment.requireContext(), ProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun logout() {
        binding.apply {
            btnToexit.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Yakin ingin keluar?")
                    .setPositiveButton("Ya") { _, _ ->
                        profileViewModel.deleteSesi()
                        val intent = Intent(this@ProfilFragment.requireContext(), AuthActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    .setNegativeButton("Tidak") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    private fun observeData(access_token: String) {
        profileViewModel.getUser(access_token).observe(viewLifecycleOwner) {
            when (it!!.status) {
                Status.SUCCESS -> {
                    binding.apply {
                        Glide.with(requireContext())
                            .load(it.data!!.imageUrl)
                            .into(imageView5)
                    }
                }
                Status.ERROR -> {
                    it.message.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {}
            }
        }
    }
}