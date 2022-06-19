package id.finalproject.binar.secondhand

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
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

    }

    private fun toFormJualPage() {
        binding.btnBack.setOnClickListener{

            it.findNavController()
                .navigate(R.id.action_previewFragment_to_formJualFragment)
        }
    }

}