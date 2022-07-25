package id.finalproject.binar.secondhand.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.finalproject.binar.secondhand.fragment.sell.TabDiminatiFragment
import id.finalproject.binar.secondhand.fragment.sell.TabHistoryFragment
import id.finalproject.binar.secondhand.fragment.sell.TabProdukFragment
import id.finalproject.binar.secondhand.fragment.sell.TabTerjualFragment

class DaftarJualAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                TabProdukFragment()
            }
            1 -> {
                TabDiminatiFragment()
            }
            2 -> {
                TabTerjualFragment()
            }
            3 -> {
                TabHistoryFragment()
            }
            else -> {
                TabProdukFragment()
            }
        }
    }

}
