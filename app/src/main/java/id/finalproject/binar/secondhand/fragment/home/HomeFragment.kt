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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.BuyerActivity
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.SearchActivity
import id.finalproject.binar.secondhand.adapter.BannerAdapter
import id.finalproject.binar.secondhand.adapter.HomeCategoryAdapter
import id.finalproject.binar.secondhand.adapter.HomeProductAdapter
import id.finalproject.binar.secondhand.databinding.FragmentHomeBinding
import id.finalproject.binar.secondhand.model.local.entity.Category
import id.finalproject.binar.secondhand.model.local.entity.Product
import id.finalproject.binar.secondhand.util.Resource
import id.finalproject.binar.secondhand.viewmodel.HomeViewModel
import me.relex.circleindicator.CircleIndicator

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var homeProductAdapter: HomeProductAdapter
    private lateinit var homeCategoryAdapter: HomeCategoryAdapter
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var indicators: CircleIndicator

    private var selectedCategoryId: Int? = -1

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

        observeProduct(null, null)
        observeCategory()
        observeBanner()

        binding.apply {
            etSearch.setOnClickListener {
                val intent = Intent(this@HomeFragment.requireContext(), SearchActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun observeProduct(categoryId: Int?, category: Category?) {

        homeProductAdapter = HomeProductAdapter { id: Int, product: Product ->
            val bundle = Bundle()
            bundle.putInt("id", id)

            val intent = Intent(this@HomeFragment.requireContext(), BuyerActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent, bundle)

        }

        binding.apply {
            listProduct.apply {
                adapter = homeProductAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }

            homeViewModel.getProduct.observe(viewLifecycleOwner) { result ->
                if (categoryId == -1 || categoryId == null) {
                    homeProductAdapter.updateData(result.data!!)
                } else {
                    homeProductAdapter.updateData(result.data!!.filter {
                        it.Categories!!.contains(category)
                    })
                }

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

    private fun observeCategory() {
        homeCategoryAdapter = HomeCategoryAdapter(requireContext()) { id: Int, category: Category ->
            selectedCategoryId = id

            observeProduct(id, category)

        }

        binding.apply {
            listCategory.apply {
                adapter = homeCategoryAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }

            homeViewModel.getCategory.observe(viewLifecycleOwner) { result ->
                val listcategory: MutableList<Category> = mutableListOf()
                listcategory.add(Category(-1, "Semua"))
                for (i in result.data!!) {
                    listcategory.add(i)
                }

                homeCategoryAdapter.updateData(listcategory)

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


    private fun observeBanner() {
        binding.apply {
            homeViewModel.getBanner.observe(viewLifecycleOwner) { result ->
                val listbanner: MutableList<String> = mutableListOf()
                for (i in result.data!!) {
                    listbanner.add(i.image_url!!)
                }

                bannerAdapter = BannerAdapter(requireContext(), listbanner)
                vpBanner.adapter = bannerAdapter

                indicators = requireView().findViewById(R.id.indicator) as CircleIndicator
                indicators.setViewPager(vpBanner)


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

