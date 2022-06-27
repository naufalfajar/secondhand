package id.finalproject.binar.secondhand.fragment.sell

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import id.finalproject.binar.secondhand.MainActivity
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentFormJualBinding


class FormJualFragment : Fragment() {

    private var _binding: FragmentFormJualBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormJualBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()


        val kategori = resources.getStringArray(R.array.kategori)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, kategori)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toPreviewPage()
        toDaftarJualPage()
    }

    private fun toPreviewPage() {
        binding.btnPreview.setOnClickListener{
            it.findNavController().navigate(R.id.action_formJualFragment_to_previewFragment)
        }
    }

    private fun toDaftarJualPage() {
        binding.btnTerbitkan.setOnClickListener{
            val bundle = Bundle()
            bundle.putBoolean("snackbar", true)

//            val intent = Intent(this@FormJualFragment.requireContext(), MainActivity::class.java)
//            startActivity(intent)
//            requireActivity().finish()

            findNavController().navigate(R.id.action_formJualFragment_to_daftarJualFragment, bundle)
        }
    }


}