package id.finalproject.binar.secondhand.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.BuyerActivity
import id.finalproject.binar.secondhand.adapter.SearchAdapter
import id.finalproject.binar.secondhand.databinding.FragmentSearchBinding
import id.finalproject.binar.secondhand.model.local.entity.Product
import id.finalproject.binar.secondhand.util.Resource
import id.finalproject.binar.secondhand.viewmodel.HomeViewModel

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            ivSearch.setOnClickListener {
                val search = etSearch.text.toString()
                progressBar.isVisible = true
                observeProduct(search)
            }
        }

    }

    private fun observeProduct(search: String) {

        searchAdapter = SearchAdapter { id: Int, product: Product ->
            val bundle = Bundle()
            bundle.putInt("id", id)

            val intent = Intent(this@SearchFragment.requireContext(), BuyerActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent, bundle)

        }

        binding.apply {
            searchBar.apply {
                adapter = searchAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            homeViewModel.getProduct.observe(viewLifecycleOwner) { result ->
                val filter = result.data!!.filter {
                    it.name!!.contains(search, ignoreCase = true)
                }
                searchAdapter.updateData(filter)

                binding.nothing.isVisible = filter.isEmpty()

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