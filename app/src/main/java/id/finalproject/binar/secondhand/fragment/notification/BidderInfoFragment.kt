package id.finalproject.binar.secondhand.fragment.notification

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.adapter.BidderInfoAdapter
import id.finalproject.binar.secondhand.databinding.FragmentBidderInfoBinding
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.model.network.response.seller.GetSellerOrderItem
import id.finalproject.binar.secondhand.repository.network.SellerOrderRepository
import id.finalproject.binar.secondhand.repository.viewModelsFactory
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.viewmodel.SellerOrderViewModel

class BidderInfoFragment : Fragment() {
    private var _binding: FragmentBidderInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var bidderInfoAdapter: BidderInfoAdapter

    private val apiService: ApiService by lazy { ApiClient.instance }

    private lateinit var sharedPref: SharedPreferences

    private val sellerOrderRepository: SellerOrderRepository by lazy {
        SellerOrderRepository(
            apiService
        )
    }
    private val sellerOrderViewModel: SellerOrderViewModel by viewModelsFactory {
        SellerOrderViewModel(
            sellerOrderRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBidderInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        sharedPref = SharedPreferences(requireContext())

        val notificationId = arguments?.getInt("id")

        initRecyclerView()
        observeOrder()
    }

    private fun initRecyclerView() {
        bidderInfoAdapter = BidderInfoAdapter { id: Int, bidderInfo: GetSellerOrderItem ->

            val viewContact =
                LayoutInflater.from(requireContext()).inflate(R.layout.dialog_contact, null)
            val btnContact = viewContact.findViewById<Button>(R.id.bt_contact)

            observeContact(id, viewContact)

            //contact
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(requireView())

            btnContact.setOnClickListener {
                val contact = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/6282323731302"))
                startActivity(contact)
            }

            dialog.show()

            //status
            val viewStatus =
                LayoutInflater.from(requireContext()).inflate(R.layout.notif_status, null)

            val success = viewStatus.findViewById<RadioButton>(R.id.rb_success)
            val fail = viewStatus.findViewById<RadioButton>(R.id.rb_failed)
            val btnSend = viewStatus.findViewById<Button>(R.id.btn_send)

            btnSend.setOnClickListener {
                if (success.isChecked) {
                    Snackbar.make(it, "Status produk berhasil diperbarui", Snackbar.LENGTH_LONG)
                        .setActionTextColor(resources.getColor(R.color.white))
                        .setTextColor(resources.getColor(R.color.white))
                        .setBackgroundTint(resources.getColor(R.color.snackbar))
                        .show()
                    dialog.dismiss()
                } else if (fail.isChecked) {
                    Snackbar.make(it, "Status produk gagal diperbarui", Snackbar.LENGTH_LONG)
                        .setActionTextColor(resources.getColor(R.color.white))
                        .setTextColor(resources.getColor(R.color.white))
                        .setBackgroundTint(resources.getColor(R.color.snackbar))
                        .show()
                    dialog.dismiss()
                }
            }
            dialog.show()


        }
        binding.rvData.apply {
            adapter = bidderInfoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeOrder() {
        val token = sharedPref.getToken()
        sellerOrderViewModel.getOrderSeller(token!!).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    bidderInfoAdapter.updateData(it.data)
                    binding.pbMovie.isVisible = false
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

    }

    private fun observeContact(orderId: Int, view: View) {
        val token = sharedPref.getToken()
        sellerOrderViewModel.getOrderByIdSeller(orderId, token!!).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val tvBuyerName = view.findViewById<Button>(R.id.tv_BuyerName)
                    val tvBuyerCity = view.findViewById<Button>(R.id.tv_BuyerCity)

                    val tvProductName = view.findViewById<Button>(R.id.tv_ProductName)
                    val tvProductPrice = view.findViewById<Button>(R.id.tv_ProductPrice)

                    tvBuyerName.text = it.data!!.user.fullName
                    tvBuyerCity.text = it.data.user.city
                    tvProductName.text = it.data.product.name
                    tvProductPrice.text = it.data.product.basePrice.toString()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

    }

    companion object{
        const val EXTRA_ORDER_ID = "extra_order_id"
    }

}
