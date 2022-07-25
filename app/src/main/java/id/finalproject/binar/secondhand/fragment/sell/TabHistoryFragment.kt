package id.finalproject.binar.secondhand.fragment.sell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.adapter.SellListHistoryAdapter
import id.finalproject.binar.secondhand.adapter.SellerOrderAdapter
import id.finalproject.binar.secondhand.databinding.FragmentTabHistoryBinding
import id.finalproject.binar.secondhand.fragment.notification.BidderInfoFragment
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.model.network.response.GetHistoryItem
import id.finalproject.binar.secondhand.model.network.response.seller.GetSellerOrderItem
import id.finalproject.binar.secondhand.repository.HistoryRepository
import id.finalproject.binar.secondhand.repository.network.SellerOrderRepository
import id.finalproject.binar.secondhand.repository.viewModelsFactory
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.viewmodel.HistoryViewModel
import id.finalproject.binar.secondhand.viewmodel.SellerOrderViewModel


class TabHistoryFragment : Fragment() {
    private var _binding: FragmentTabHistoryBinding? = null
    private val binding get() = _binding!!

    private val apiService: ApiService by lazy { ApiClient.instance }
    private lateinit var sharedPref: SharedPreferences

    private val HistoryRepository: HistoryRepository by lazy {
        HistoryRepository(
            apiService
        )
    }

    private val HistoryViewModel: HistoryViewModel by viewModelsFactory {
        HistoryViewModel(
            HistoryRepository
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreferences(requireContext())
//        observeHistory()

        getDataHistory()

    }

//    private fun observeHistory() {
//
//        sellListHistoryAdapter = SellListHistoryAdapter { id: Int, history: History ->
//            val bundle = Bundle()
//            bundle.putInt("id", id)
//
//        }
//
//        binding.apply {
//            listHistory.apply {
//                adapter = sellListHistoryAdapter
//                layoutManager = LinearLayoutManager(requireContext())
//            }
//
//            sellListViewModel.getHistory.observe(viewLifecycleOwner) { result ->
//                sellListHistoryAdapter.updateData(result.data!!)
//                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
//                if (result is Resource.Error && result.data.isNullOrEmpty()) {
//                    Toast.makeText(
//                        requireContext(),
//                        result.error?.localizedMessage,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//    }

    private fun getDataHistory(){
            val token = sharedPref.getToken()
            HistoryViewModel.getHistory(token!!).observe(viewLifecycleOwner) { it ->
                when (it.status) {
                    Status.SUCCESS -> {
                        val data = it.data

                        val adapter = SellListHistoryAdapter()
                        val list = mutableListOf<GetHistoryItem>()
                        for (i in data!!) {
                            list.add(i)
                        }


                        adapter.updateData(list)
                        binding.apply {
                            listHistory.adapter = adapter
                            listHistory.layoutManager = LinearLayoutManager(requireContext())
                            progressBar.isVisible = false
                        }
                    }


                    else -> {}
                }
            }
        }
    }

