package id.finalproject.binar.secondhand.fragment.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.adapter.NotificationAdapter
import id.finalproject.binar.secondhand.databinding.FragmentNotificationBinding
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.model.network.response.GetNotificationItem
import id.finalproject.binar.secondhand.repository.network.NotificationRepository
import id.finalproject.binar.secondhand.repository.viewModelsFactory
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.viewmodel.NotificationViewModel

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var notificationAdapter: NotificationAdapter

    private val apiService: ApiService by lazy { ApiClient.instance }

    private val notificationRepository: NotificationRepository by lazy {
        NotificationRepository(
            apiService
        )
    }
    private val notificationViewModel: NotificationViewModel by viewModelsFactory {
        NotificationViewModel(
            notificationRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeNotification()
        refreshLayout()

    }

    private fun refreshLayout() {
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.fragment_notification, null)
        val swipe: SwipeRefreshLayout = view.findViewById(R.id.refresh_layout)

        swipe.setOnRefreshListener {
            initRecyclerView()
            observeNotification()
            swipe.isRefreshing = false
        }

    }

    private fun initRecyclerView() {
        notificationAdapter = NotificationAdapter { id: Int, notification: GetNotificationItem ->
            val bundle = Bundle()
            bundle.putInt("id", id)
//            notificationViewModel.patchNotifcationById(id).observe()
//            findNavController().navigate(R.id.action_notificationFragment_to_bidderInfoFragment, bundle)

//            val fragment: Fragment = BidderInfoFragment()
//            val transaction = childFragmentManager.beginTransaction()
//            fragment.arguments = bundle
//            transaction.replace(R.id.fragmentContainerView, fragment)
//            transaction.commit()
        }
        binding.rvData.apply {
            adapter = notificationAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeNotification() {
        notificationViewModel.getNotification("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTQ5MjcxODZ9.fghFryd8OPEHztZlrN50PtZj0EC7NWFVj2iPPN9xi1M")
            .observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        notificationAdapter.updateData(it.data)
                        binding.pbMovie.isVisible = false
                        if (it.data!!.isEmpty()) {
                            binding.nothing.isVisible = true
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

    }

}