package id.finalproject.binar.secondhand.fragment.sell

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.BuyerActivity
import id.finalproject.binar.secondhand.SellActivity
import id.finalproject.binar.secondhand.adapter.SellListProductAdapter
import id.finalproject.binar.secondhand.databinding.FragmentTabProdukBinding
import id.finalproject.binar.secondhand.model.local.entity.ProductSeller
import id.finalproject.binar.secondhand.util.Resource
import id.finalproject.binar.secondhand.viewmodel.SellListViewModel

@AndroidEntryPoint
class TabProdukFragment : Fragment() {
    private var _binding: FragmentTabProdukBinding? = null
    private val binding get() = _binding!!

    private val sellListViewModel: SellListViewModel by viewModels()

    private lateinit var sellListProductAdapter: SellListProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabProdukBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProduct()

    }

    private fun observeProduct() {

        sellListProductAdapter = SellListProductAdapter { id: Int, product: ProductSeller ->
            val bundle = Bundle()
            bundle.putInt("id", id)

            if (id == -1) {
                val intent =
                    Intent(this@TabProdukFragment.requireContext(), SellActivity::class.java)
                startActivity(intent)
            } else {
                val intent =
                    Intent(this@TabProdukFragment.requireContext(), BuyerActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent, bundle)
            }

        }

        binding.apply {
            listProduct.apply {
                adapter = sellListProductAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }

            sellListViewModel.getProduct.observe(viewLifecycleOwner) { result ->

                val listproduct: MutableList<ProductSeller> = mutableListOf()
                listproduct.add(ProductSeller(-1, 0, Categories = listOf()))
                for (i in result.data!!) {
                    listproduct.add(i)
                }

                sellListProductAdapter.updateData(listproduct)

                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()

                if (result is Resource.Error && result.data.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        result.error?.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}