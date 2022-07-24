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
import id.finalproject.binar.secondhand.model.local.entity.Notification
import id.finalproject.binar.secondhand.model.local.entity.ProductSeller
import id.finalproject.binar.secondhand.util.Resource
import id.finalproject.binar.secondhand.viewmodel.NotificationViewModel

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val notificationViewModel: NotificationViewModel by viewModels()
    private lateinit var notificationAdapter: NotificationAdapter

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
                val listnotification: MutableList<Notification> = mutableListOf()
                for (i in result.data!!) {
                    listnotification.add(i)
                }
                notificationAdapter.updateData(listnotification)

                pbNotification.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                nothing.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                nothing.text = result.error?.localizedMessage
            }
        }
    }

}