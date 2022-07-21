package id.finalproject.binar.secondhand.fragment.sell

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.adapter.TerjualAdapter
import id.finalproject.binar.secondhand.databinding.FragmentTabTerjualBinding
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.network.response.seller.GetSellerOrderItem
import id.finalproject.binar.secondhand.repository.network.SellerOrderRepository
import id.finalproject.binar.secondhand.repository.viewModelsFactory
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.viewmodel.SellerOrderViewModel

@AndroidEntryPoint
class TabTerjualFragment : Fragment() {

    private var _binding: FragmentTabTerjualBinding? = null
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
        _binding = FragmentTabTerjualBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPreferences(requireContext())

        getDataTerjual()

    }

    private fun getDataTerjual() {
        val token = sharedPref.getToken()
        sellerOrderViewModel.getOrderSeller(token!!).observe(viewLifecycleOwner) { it ->
                when(it.status){
                    Status.SUCCESS -> {
                        val data = it.data!!

                        val adapter = TerjualAdapter(){

                        }

                        val list = mutableListOf<GetSellerOrderItem>()
                        for (order in data!!) {
                            if (order.status == "accepted") {
                                list.add(order)
                            }
                        }

                        adapter.submitData(data)
                        binding.apply {
                            rvTerjual.adapter = adapter
                            rvTerjual.layoutManager = LinearLayoutManager(requireContext())
                        }
                    }
                    else -> {}
                }
            }
        }
    }

