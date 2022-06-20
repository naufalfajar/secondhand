package id.finalproject.binar.secondhand

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import id.finalproject.binar.secondhand.databinding.ActivityMainBinding
import id.finalproject.binar.secondhand.databinding.FragmentFormJualBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.*


class FormJualFragment : Fragment() {

    private var _binding: FragmentFormJualBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormJualBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val items = listOf("Pakaian", "Makeup", "Skin care", "Buku", "Lainnya")
//        val adapter = ArrayAdapter(this, R.layout.list_item, items)
//        binding.dropdownField.setAdapter(adapter)

//        showToast()
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

            it.findNavController()
                .navigate(R.id.action_formJualFragment_to_daftarJualFragment)

//            Toast(this).apply {
//                duration = Toast.LENGTH_LONG
//                setGravity(Gravity.TOP, 10, 10)
//                view = layoutInflater.inflate(R.layout.custom_toast, toastLayout)
//            }.show()

        }
    }

}