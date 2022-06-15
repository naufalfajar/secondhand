package id.finalproject.binar.secondhand

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import id.finalproject.binar.secondhand.databinding.ActivityMainBinding
import id.finalproject.binar.secondhand.databinding.FragmentItemDetailBinding
import me.relex.circleindicator.CircleIndicator3

class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    private var imageList = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageList.add(R.drawable.person_watch)
        imageList.add(R.drawable.watch)
        imageList.add(R.drawable.great_watch)

        binding.vpImage.adapter = SwipeImageAdapter(imageList)
        binding.vpImage.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator = binding.circleIndicator
        indicator.setViewPager(binding.vpImage)
    }
}