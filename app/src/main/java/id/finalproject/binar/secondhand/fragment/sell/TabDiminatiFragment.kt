package id.finalproject.binar.secondhand.fragment.sell

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.BidderActivity
import id.finalproject.binar.secondhand.BuyerActivity
import id.finalproject.binar.secondhand.fragment.notification.BidderInfoFragment
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.adapter.SellerOrderAdapter
import id.finalproject.binar.secondhand.databinding.FragmentTabDiminatiBinding
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.local.entity.Product
import id.finalproject.binar.secondhand.model.network.response.seller.GetSellerOrderItem
import id.finalproject.binar.secondhand.repository.network.SellerOrderRepository
import id.finalproject.binar.secondhand.repository.viewModelsFactory
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.viewmodel.SellerOrderViewModel

@AndroidEntryPoint
class TabDiminatiFragment : Fragment() {

    private var _binding: FragmentTabDiminatiBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentTabDiminatiBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPreferences(requireContext())

        getDataDiminati()

    }

    private fun getDataDiminati() {
        val token = sharedPref.getToken()
        sellerOrderViewModel.getOrderSeller(token!!).observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    val data = it.data

                    val adapter = SellerOrderAdapter { id: Int, order: GetSellerOrderItem ->
                        val bundle = Bundle()
                        bundle.putInt("id", id)

                        val intent = Intent(this@TabDiminatiFragment.requireContext(), BidderActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent, bundle)
                    }
                    val list = mutableListOf<GetSellerOrderItem>()

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
                else -> {}
            }
        }
    }
}
