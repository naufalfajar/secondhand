package id.finalproject.binar.secondhand.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.finalproject.binar.secondhand.databinding.FragmentBidderInfoBinding

class BidderInfoFragment : Fragment() {
    private var _binding: FragmentBidderInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBidderInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }

}