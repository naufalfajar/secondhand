package id.finalproject.binar.secondhand.fragment.notification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.AuthActivity
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.adapter.NotificationAdapter
import id.finalproject.binar.secondhand.databinding.FragmentNotificationBinding
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.local.entity.Notification
import id.finalproject.binar.secondhand.util.Resource
import id.finalproject.binar.secondhand.viewmodel.NotificationViewModel

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val notificationViewModel: NotificationViewModel by viewModels()

    private lateinit var notificationAdapter: NotificationAdapter

    private lateinit var sharedPref: SharedPreferences

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

        if (notificationViewModel.isLogin) {
            observeNotification()
            refreshLayout()
        } else {
            binding.apply {
                notificationArea.isVisible = false
                ifnotlogin.isVisible = true

                btnLogin.setOnClickListener {
                    val intent =
                        Intent(this@NotificationFragment.requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                }
            }
        }

//        initRecyclerView()


    }

    private fun refreshLayout() {
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.fragment_notification, null)
        val swipe: SwipeRefreshLayout = view.findViewById(R.id.refresh_layout)

        swipe.setOnRefreshListener {
//            initRecyclerView()
            observeNotification()
            swipe.isRefreshing = false
        }

    }

//    private fun initRecyclerView() {
//        notificationAdapter = NotificationAdapter { id: Int, notification: GetNotificationItem ->
//            val bundle = Bundle()
//            bundle.putInt("id", id)
////            notificationViewModel.patchNotifcationById(id).observe()
////            findNavController().navigate(R.id.action_notificationFragment_to_bidderInfoFragment, bundle)
//
////            val fragment: Fragment = BidderInfoFragment()
////            val transaction = childFragmentManager.beginTransaction()
////            fragment.arguments = bundle
////            transaction.replace(R.id.fragmentContainerView, fragment)
////            transaction.commit()
//        }
//        binding.rvData.apply {
//            adapter = notificationAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }
//    }

    private fun observeNotification() {
        notificationAdapter = NotificationAdapter { id: Int, notification: Notification ->
            val bundle = Bundle()
            bundle.putInt("id", id)

        }

        binding.apply {
            rvData.apply {
                adapter = notificationAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            notificationViewModel.notification.observe(viewLifecycleOwner) { result ->
                pbNotification.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
//                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
//                textViewError.text = result.error?.localizedMessage
            }
        }
    }

//    private fun observeNotification() {
//        notificationViewModel.getNotification("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTQ5MjcxODZ9.fghFryd8OPEHztZlrN50PtZj0EC7NWFVj2iPPN9xi1M")
//            .observe(viewLifecycleOwner) {
//                when (it.status) {
//                    Status.SUCCESS -> {
//                        notificationAdapter.updateData(it.data)
//                        binding.pbMovie.isVisible = false
//                        if (it.data!!.isEmpty()) {
//                            binding.nothing.isVisible = true
//                        }
//                    }
//                    Status.ERROR -> {
//                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//                }
//                else -> {}
//            }
//        }
//
//    }

}