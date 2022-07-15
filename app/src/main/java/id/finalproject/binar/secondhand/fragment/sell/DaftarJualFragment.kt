package id.finalproject.binar.secondhand.fragment.sell

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.adapter.DaftarJualAdapter
import id.finalproject.binar.secondhand.databinding.FragmentDaftarJualBinding
import id.finalproject.binar.secondhand.databinding.FragmentItemDetailBinding

class DaftarJualFragment : Fragment() {

    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDaftarJualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DaftarJualAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
            tab.setIcon(IMAGE_LIST[position])
        }.attach()

        val tabs = binding.tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabs.childCount ) {
            val tab = tabs.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.marginEnd = 16
            layoutParams.marginStart = 16
            tab.layoutParams = layoutParams
            binding.tabLayout.requestLayout()
        }

    }

    companion object{
        private val TAB_TITLES = mutableListOf<String>(
            "Produk",
            "Diminati",
            "Terjual"
        )

        private val IMAGE_LIST = mutableListOf<Int>(
            R.drawable.ic_box_grey,
            R.drawable.ic_fav_grey,
            R.drawable.ic_dollar_grey
        )
    }

}
