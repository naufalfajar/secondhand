package id.finalproject.binar.secondhand.fragment.sell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentPreviewBinding

class PreviewFragment : Fragment() {
    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toFormJualPage()
        toDaftarJualPage()
    }

    private fun toFormJualPage() {
        binding.btnBack.setOnClickListener {

            it.findNavController()
                .navigate(R.id.action_previewFragment_to_formJualFragment)
        }
    }

    private fun toDaftarJualPage() {
        binding.btnTerbitkan2.setOnClickListener {

            it.findNavController()
                .navigate(R.id.action_previewFragment_to_daftarJualFragment)

        }

    }

}