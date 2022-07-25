package id.finalproject.binar.secondhand.fragment.sell

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
import id.finalproject.binar.secondhand.adapter.SellListHistoryAdapter
import id.finalproject.binar.secondhand.databinding.FragmentTabHistoryBinding
import id.finalproject.binar.secondhand.model.local.entity.History
import id.finalproject.binar.secondhand.util.Resource
import id.finalproject.binar.secondhand.viewmodel.SellListViewModel

@AndroidEntryPoint
class TabHistoryFragment : Fragment() {
    private var _binding: FragmentTabHistoryBinding? = null
    private val binding get() = _binding!!

    private val sellListViewModel: SellListViewModel by viewModels()

    private lateinit var sellListHistoryAdapter: SellListHistoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeHistory()

    }

    private fun observeHistory() {

        sellListHistoryAdapter = SellListHistoryAdapter { id: Int, history: History ->
            val bundle = Bundle()
            bundle.putInt("id", id)

        }

        binding.apply {
            listHistory.apply {
                adapter = sellListHistoryAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            sellListViewModel.getHistory.observe(viewLifecycleOwner) { result ->
                sellListHistoryAdapter.updateData(result.data!!)
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