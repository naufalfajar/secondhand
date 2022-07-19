package id.finalproject.binar.secondhand.fragment.sell

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import binar.lima.satu.secondhand.data.utils.Status
import binar.lima.satu.secondhand.model.seller.order.GetSellerOrderResponseItem
import binar.lima.satu.secondhand.view.adapter.SellerOrderAdapter
import id.finalproject.binar.secondhand.fragment.notification.BidderInfoFragment
import binar.lima.satu.secondhand.viewmodel.ApiViewModel
import binar.lima.satu.secondhand.viewmodel.UserViewModel
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentTabDiminatiBinding
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.repository.network.SellerOrderRepository
import id.finalproject.binar.secondhand.repository.viewModelsFactory
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.viewmodel.SellerOrderViewModel


class TabDiminatiFragment : Fragment() {

    private var _binding: FragmentTabDiminatiBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentTabDiminatiBinding.inflate(inflater, container, false)
        getDataDiminati()
        return binding.root
    }

    private fun getDataDiminati() {
        sellerOrderViewModel.getToken().observe(viewLifecycleOwner) { token ->
            apiService.getOrderSeller(access_token = token,"").observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        val data = it.data

                        val adapter = SellerOrderAdapter { order ->
                            val mBundle = bundleOf(BidderInfoFragment.EXTRA_ORDER_ID to order.id)
                            Navigation.findNavController(requireView()).navigate(
                                R.id.action_daftarJualFragment2_to_bidderInfoFragment,
                                mBundle
                            )
                        }
                        val list = mutableListOf<GetOrderSellerResponseItem>()

                        for (order in data!!) {
                            if (order.status == "pending" && order.product.status == "available" || order.status == "success" && order.product.status == "available") {
                                list.add(order)
                            }
                        }

                        binding.ivListKosong.visibility = if (list.isEmpty()) {
                            View.VISIBLE
                        } else {
                            View.INVISIBLE
                        }

                        adapter.submitData(list)
                        binding.apply {
                            rvDiminati.adapter = adapter
                            rvDiminati.layoutManager = LinearLayoutManager(requireContext())
                        }
                    }
                }
            }
        }
    }
}