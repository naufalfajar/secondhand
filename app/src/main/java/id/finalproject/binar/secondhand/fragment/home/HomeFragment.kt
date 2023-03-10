package id.finalproject.binar.secondhand.fragment.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.adapter.BannerAdapter
import id.finalproject.binar.secondhand.adapter.HomeBannerAdapter
import id.finalproject.binar.secondhand.adapter.HomeCategoryAdapter
import id.finalproject.binar.secondhand.adapter.HomeProductAdapter
import id.finalproject.binar.secondhand.databinding.FragmentHomeBinding
import id.finalproject.binar.secondhand.util.Resource
import id.finalproject.binar.secondhand.viewmodel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: BannerAdapter
    lateinit var imageList: List<Int>

    private val homeViewModel: HomeViewModel by viewModels()

    //    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var handler: Handler

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

        val productHomeAdapter = HomeProductAdapter()
        val categoryHomeAdapter = HomeCategoryAdapter()
        val bannerHomeAdapter = HomeBannerAdapter()

        viewPager = view.findViewById(R.id.vp_banner)

        imageList = ArrayList<Int>()
        imageList = imageList + R.drawable.great_watch
        imageList = imageList + R.drawable.watch

        viewPagerAdapter = BannerAdapter(requireContext(), imageList)

        viewPager.adapter = viewPagerAdapter

//        binding.vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                handler.removeCallbacks(runnable)
//                handler.postDelayed(runnable, 2000)
//            }
//        })

        observeProduct(productHomeAdapter)
        observeCategory(categoryHomeAdapter)
//        observeBanner(bannerHomeAdapter)
//        initBanner()

    }

//    override fun onPause() {
//        super.onPause()
//
//        handler.removeCallbacks(runnable)
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        handler.postDelayed(runnable, 2000)
//    }
//
//    private val runnable = Runnable {
//        binding.vpBanner.currentItem = binding.vpBanner.currentItem + 1
//    }

//    private fun initBanner() {
//        bannerAdapter = BannerAdapter(binding.vpBanner)
//        handler = Handler(Looper.myLooper()!!)
//        binding.vpBanner.apply {
//            adapter = bannerAdapter
//            offscreenPageLimit = 3
//            clipToPadding = false
//            clipChildren = false
//            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//        }
//    }

    private fun observeProduct(homeProductAdapter: HomeProductAdapter) {
        binding.apply {
            listProduct.apply {
                adapter = homeProductAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }

            homeViewModel.product.observe(viewLifecycleOwner) { result ->
                homeProductAdapter.submitList(result.data)

                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textViewError.text = result.error?.localizedMessage
            }
        }
    }

    private fun observeCategory(homeCategoryAdapter: HomeCategoryAdapter) {
        binding.apply {
            listCategory.apply {
                adapter = homeCategoryAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }

            homeViewModel.category.observe(viewLifecycleOwner) { result ->
                homeCategoryAdapter.submitList(result.data)

                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textViewError.text = result.error?.localizedMessage
            }
        }
    }

//    private fun observeBanner(homeBannerAdapter: HomeBannerAdapter){
//        binding.apply {
//            vpBanner.apply {
//                adapter = homeBannerAdapter
//            }
//        }
//
//        homeViewModel.banner.observe(viewLifecycleOwner) { result ->
//            homeBannerAdapter.submitList(result.data)
//        }
//    }


//    private fun observeBanner() {
//        homeViewModel.getBanner().observe(viewLifecycleOwner) {
//            when (it.status) {
//                Status.SUCCESS -> {
//                    bannerAdapter.updateData(it.data)
//                    binding.progressBar.isVisible = false
//                }
//                Status.ERROR -> {
//                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//                }
//                else -> {}
//            }
//        }
//    }
}

