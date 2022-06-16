package id.finalproject.binar.secondhand.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.adapter.NotificationAdapter
import id.finalproject.binar.secondhand.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private var dataList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataList.add("Motor N-MAX")
        dataList.add("Laptop ASUS ROG")
        dataList.add("Iphone 13 Pro")
        dataList.add("Samsung Galaxy S20")
        dataList.add("Mobil Honda Jazz")

        binding.rvData.adapter = NotificationAdapter(dataList)

        binding.tvHeader.setOnClickListener {
            val viewer =
                LayoutInflater.from(requireContext()).inflate(R.layout.dialog_contact, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(viewer)
            dialog.show()
        }

    }

}