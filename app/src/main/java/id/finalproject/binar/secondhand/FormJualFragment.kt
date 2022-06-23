package id.finalproject.binar.secondhand


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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

            it.findNavController()
                .navigate(R.id.action_formJualFragment_to_previewFragment)
        }
    }

    private fun toDaftarJualPage() {
        binding.btnTerbitkan.setOnClickListener{

            val bundle = Bundle()
            bundle.putBoolean("snackbar",true)

            findNavController().navigate(
                R.id.action_formJualFragment_to_daftarJualFragment, bundle)


        }
    }



}