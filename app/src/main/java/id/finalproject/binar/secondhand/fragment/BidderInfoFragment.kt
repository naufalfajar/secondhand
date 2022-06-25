package id.finalproject.binar.secondhand.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.adapter.BidderInfoAdapter
import id.finalproject.binar.secondhand.databinding.FragmentBidderInfoBinding
import id.finalproject.binar.secondhand.model.GetSellerOrderItem
import id.finalproject.binar.secondhand.model.Status
import id.finalproject.binar.secondhand.repository.SellerOrderRepository
import id.finalproject.binar.secondhand.repository.viewModelsFactory
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.viewmodel.SellerOrderViewModel

class BidderInfoFragment : Fragment() {
    private var _binding: FragmentBidderInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var bidderInfoAdapter: BidderInfoAdapter

    private val apiService: ApiService by lazy { ApiClient.instance }

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

        val notificationId = arguments?.getInt("id")

        initRecyclerView()
        observeOrder()
    }

    private fun initRecyclerView() {
        bidderInfoAdapter = BidderInfoAdapter { id: Int, bidderInfo: GetSellerOrderItem ->

            val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_contact, null)
            val btnContact = view.findViewById<Button>(R.id.bt_contact)

            observeContact(id, view)

            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(requireView())

            btnContact.setOnClickListener {
                val contact = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/6282323731302"))
                startActivity(contact)
            }

            dialog.show()
        }
        binding.rvData.apply {
            adapter = bidderInfoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeOrder() {
        sellerOrderViewModel.getOrderSeller().observe(viewLifecycleOwner) {
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
        sellerOrderViewModel.getOrderByIdSeller(orderId).observe(viewLifecycleOwner) {
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

}