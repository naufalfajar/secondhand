package id.finalproject.binar.secondhand.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import id.finalproject.binar.secondhand.adapter.ItemProductHomeAdapter
import id.finalproject.binar.secondhand.databinding.FragmentHomeBinding
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.model.network.response.buyer.GetBuyerProductItem
import id.finalproject.binar.secondhand.repository.network.BuyerProductRepository
import id.finalproject.binar.secondhand.repository.viewModelsFactory
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemProductHomeAdapter: ItemProductHomeAdapter

    private val apiService: ApiService by lazy { ApiClient.instance }

    private val buyerProductRepository: BuyerProductRepository by lazy {
        BuyerProductRepository(
            apiService
        )
    }

    private val homeViewModel: HomeViewModel by viewModelsFactory {
        HomeViewModel(
            buyerProductRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeHome()

    }

    private fun initRecyclerView() {
        itemProductHomeAdapter = ItemProductHomeAdapter { id: Int, product: GetBuyerProductItem ->
            val bundle = Bundle()
            bundle.putInt("id", id)
//            notificationViewModel.patchNotifcationById(id).observe()
//            findNavController().navigate(R.id.action_notificationFragment_to_bidderInfoFragment, bundle)
        }
        binding.listProduct.apply {
            adapter = itemProductHomeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun observeHome() {
        homeViewModel.getProductBuyer().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    itemProductHomeAdapter.updateData(it.data)
                    binding.progressBar.isVisible = false
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

    }

}

