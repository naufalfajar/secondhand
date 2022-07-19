package id.finalproject.binar.secondhand.fragment.sell

import android.bluetooth.BluetoothStatusCodes.SUCCESS
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentTabTerjualBinding

class TabTerjualFragment : Fragment() {

    private var _binding: FragmentTabTerjualBinding? = null
    private val binding get() = _binding!!

    private val apiViewModel: ApiViewModel by hiltNavGraphViewModels(R.id.nav_main)
    private val userViewModel: UserViewModel by hiltNavGraphViewModels(R.id.nav_main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabTerjualBinding.inflate(inflater, container, false)
        getDataTerjual()
        return binding.root
    }

    private fun getDataTerjual() {
        userViewModel.getToken().observe(viewLifecycleOwner) { token ->
            apiViewModel.getSellerOrder(token, "accepted").observe(viewLifecycleOwner){
                when(it.status){
                    SUC CESS -> {
                        val data = it.data!!

                        val adapter = TerjualAdapter(){

                        }
                        adapter.submitData(data)
                        binding.apply {
                            rvTerjual.adapter = adapter
                            rvTerjual.layoutManager = LinearLayoutManager(requireContext())
                        }
                    }
                }
            }
        }
    }

}