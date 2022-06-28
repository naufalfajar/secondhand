package id.finalproject.binar.secondhand.fragment.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.finalproject.binar.secondhand.databinding.FragmentUpdateprofilBinding

class UpdateProfilFragment : Fragment() {

    private var _binding: FragmentUpdateprofilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateprofilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textUbahAkun.setOnClickListener { back() }
        }
    }

    private fun back() {
        findNavController().popBackStack()
    }
}