package id.finalproject.binar.secondhand.fragment.account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.AuthActivity
import id.finalproject.binar.secondhand.MainActivity
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentProfilBinding
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.repository.UserRepo
import id.finalproject.binar.secondhand.repository.viewModelsFactory
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import kotlin.math.log
import id.finalproject.binar.secondhand.helper.SharedPreferences


class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPrefs: SharedPreferences

    private lateinit var sharedPref: SharedPreferences

    private val apiService: ApiService by lazy { ApiClient.instance }
    private val userRepo: UserRepo by lazy { UserRepo(apiService) }
    private val profileViewModel: ProfileViewModel by viewModelsFactory { ProfileViewModel(userRepo) }


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
        sharedPref = requireContext().getSharedPreferences("ini_token", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "null")
        if (token != null) {
            observeData(token)
        }
        tvUpdate()
        logout()
    }

    private fun tvUpdate() {
        binding.apply {
            btnToUpdate.setOnClickListener {
                findNavController().navigate(R.id.action_profilFragment3_to_updateProfilFragment2)
            }
        }
    }

    private fun logout() {
        binding.apply {
            btnToexit.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Yakin ingin keluar?")
                    .setPositiveButton("Ya") { _, _ ->
                        val intent =
                            Intent(this@ProfilFragment.requireContext(), AuthActivity::class.java)
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