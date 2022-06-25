package id.finalproject.binar.secondhand

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.google.android.material.snackbar.Snackbar
import id.finalproject.binar.secondhand.databinding.FragmentDaftarJualBinding

class DaftarJualFragment : Fragment() {

    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDaftarJualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val snackbarId = arguments?.getBoolean("snackbar")

        if (snackbarId == true){

            val position = binding.myCoordinatorLayout
            val halDaftarJual = binding.layoutDaftarJual

            val customSnackbar = LayoutInflater.from(requireContext()).inflate(R.layout.custom_snackbar, null)
            val snackbar = Snackbar.make(halDaftarJual, "", Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.TRANSPARENT)
            val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
            snackbarLayout.setPadding(0,0,0,0)

            customSnackbar.findViewById<ImageButton>(R.id.btn_snackbar_close).setOnClickListener {
                snackbar.dismiss()
            }
            snackbarLayout.addView(customSnackbar)
            snackbar.anchorView = position
            snackbar.show()
            }
        }

    }
