package id.finalproject.binar.secondhand.fragment.sell

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.AuthActivity
import id.finalproject.binar.secondhand.BuyerActivity
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.adapter.DaftarJualAdapter
import id.finalproject.binar.secondhand.adapter.HomeProductAdapter
import id.finalproject.binar.secondhand.databinding.FragmentDaftarJualBinding
import id.finalproject.binar.secondhand.model.local.entity.Category
import id.finalproject.binar.secondhand.model.local.entity.Product
import id.finalproject.binar.secondhand.util.Resource
import id.finalproject.binar.secondhand.viewmodel.SellListViewModel

@AndroidEntryPoint
class DaftarJualFragment : Fragment() {

    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding!!

    private val sellListViewModel: SellListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDaftarJualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sellListViewModel.isLogin) {
//            observeUser()
            val adapter = DaftarJualAdapter(this)
            binding.viewPager.adapter = adapter
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = TAB_TITLES[position]
                tab.setIcon(IMAGE_LIST[position])
            }.attach()

            val tabs = binding.tabLayout.getChildAt(0) as ViewGroup
            for (i in 0 until tabs.childCount) {
                val tab = tabs.getChildAt(i)
                val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
                layoutParams.marginEnd = 16
                layoutParams.marginStart = 16
                tab.layoutParams = layoutParams
                binding.tabLayout.requestLayout()
            }
        } else {
            binding.apply {
                sellListArea.isVisible = false
                ifnotlogin.isVisible = true

                btnLogin.setOnClickListener {
                    val intent =
                        Intent(this@DaftarJualFragment.requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }

    private fun observeUser() {
        sellListViewModel.getUser.observe(viewLifecycleOwner) { result ->
            binding.apply {
                val data = result.data
                if (data != null){
                    Glide.with(requireContext())
                        .load(data.image_url)
                        .into(imageView3)
                    tvNama.text = data.full_name
                    tvCity.text = data.city
                }
            }
        }
    }

    companion object{
        private val TAB_TITLES = mutableListOf<String>(
            "Produk",
            "Diminati",
            "Terjual",
            "History"
        )

        private val IMAGE_LIST = mutableListOf<Int>(
            R.drawable.ic_box_grey,
            R.drawable.ic_fav_grey,
            R.drawable.ic_dollar_grey,
            R.drawable.ic_history

        )
    }

}
