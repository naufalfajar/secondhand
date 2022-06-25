package id.finalproject.binar.secondhand.fragment.sell

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentItemDetailBinding
import id.finalproject.binar.secondhand.fragment.SwipeImageAdapter

class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    private var imageList = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageList.add(R.drawable.person_watch)
        imageList.add(R.drawable.watch)
        imageList.add(R.drawable.great_watch)

        binding.vpImage.adapter = SwipeImageAdapter(imageList)
        binding.vpImage.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator = binding.circleIndicator
        indicator.setViewPager(binding.vpImage)

        val btnSend = binding.fabItemDetail

        btnSend.setOnClickListener {
            val bsdView = LayoutInflater.from(requireContext()).inflate(R.layout.bottomsheet_bid, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(bsdView)

            val btnBid = bsdView.findViewById<Button>(R.id.btn_item_bid)
            btnBid.setOnClickListener {
                binding.fabItemDetail.text = "Menunggu Respon Penjual"
                binding.fabItemDetail.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.grey_etborder
                    )
                )
                val itemDetailPage = binding.flItemDetailPage
                val position = binding.myCoordinatorLayout

                dialog.dismiss()

                val customView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.snackbar_custom, null)
                val snackbar = Snackbar.make(itemDetailPage, "", Snackbar.LENGTH_LONG)
                snackbar.view.setBackgroundColor(Color.TRANSPARENT)
                val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
                snackbarLayout.setPadding(0, 0, 0, 0)

                customView.findViewById<ImageButton>(R.id.btn_snackbar_close).setOnClickListener {
                    snackbar.dismiss()
                }
                snackbarLayout.addView(customView)
                snackbar.setAnchorView(position).show()
            }
            dialog.show()
        }
    }
}