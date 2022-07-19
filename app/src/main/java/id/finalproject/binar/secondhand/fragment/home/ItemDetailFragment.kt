package id.finalproject.binar.secondhand.fragment.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.AuthActivity
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentItemDetailBinding
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.model.network.response.GetUserItem
import id.finalproject.binar.secondhand.repository.*
import id.finalproject.binar.secondhand.viewmodel.BuyerViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    private val buyerViewModel: BuyerViewModel by viewModels()
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var accessToken: String

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
        sharedPrefs = SharedPreferences(requireContext())

        val productId = arguments!!.getInt("id")
        getData(productId)
        backArrow()
        bottomSheetDialog(productId)
    }

    private fun backArrow(){
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun getData(id: Int){
        buyerViewModel.getProductDetail(id).observe(viewLifecycleOwner){
            it.data.let { data ->
                var categoryString = ""
                data?.Categories?.forEachIndexed { index, category ->
                    if(index == data?.Categories.size - 1){
                        categoryString += category.name
                    } else {
                        categoryString += category.name + ", "
                    }
                }
                binding.apply {
                    Glide.with(requireContext()).load(data?.image_url).into(vpImage)

                    tvItemNama.text = data?.name
                    tvItemCategory.text = categoryString
                    tvItemHarga.text = data?.base_price!!.toRp()
                    tvDeskripsi.text = data.description
                }
            }
        }
    }

    private fun setSellerInfo(sellerInfo: GetUserItem){
        Glide.with(this).load(sellerInfo.imageUrl).into(binding.ivSellerInfo)
        binding.tvSellerNama.text = sellerInfo.fullName
        binding.tvSellerCity.text = sellerInfo.city
    }


    private fun bottomSheetDialog(id: Int){
        val btnSend = binding.fabItemDetail
        btnSend.setOnClickListener {
            if(sharedPrefs.getLogin()){
                accessToken = sharedPrefs.getToken().toString()
                val bsdView = LayoutInflater.from(requireContext()).inflate(R.layout.bottomsheet_bid, null)
                val dialog = BottomSheetDialog(requireContext())
                dialog.setContentView(bsdView)
                getBidData(id, bsdView)

                val btnBid = bsdView.findViewById<Button>(R.id.btn_item_bid)
                btnBid.setOnClickListener {
                    val bidPrice = bsdView.findViewById<EditText>(R.id.et_bid_price).text.toString()
                    processData(accessToken, id.toString(), bidPrice)
                    dialog.dismiss()
                }
                dialog.show()
            } else {
                binding.apply {
                    flItemDetailContent.isVisible = false
                    ifnotlogin.isVisible = true

                    btnLogin.setOnClickListener {
                        val intent =
                            Intent(this@ItemDetailFragment.requireContext(), AuthActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun getBidData(id: Int, view: View){
        buyerViewModel.getProductDetail(id).observe(viewLifecycleOwner){
            it.data.let { data ->
                binding.apply {
                    val bidImg = view.findViewById<ImageView>(R.id.iv_item_bid)
                    val bidName = view.findViewById<TextView>(R.id.tv_name_item_bid)
                    val bidPrice = view.findViewById<TextView>(R.id.tv_price_item_bid)

                    Glide.with(requireContext()).load(data?.image_url).into(bidImg)

                    bidName.text = data?.name
                    bidPrice.text = data?.base_price!!.toRp()
                }
            }
        }
    }

    private fun processData(accessToken: String, id: String, bidPrice: String){
        val productIdBody = id.toRequestBody("text/plain".toMediaType())
        val bidPriceBody = bidPrice.toRequestBody("text/plain".toMediaType())

        bidProduct(accessToken, productIdBody, bidPriceBody)
    }

    private fun bidProduct(accessToken: String, id: RequestBody, bidPrice: RequestBody){
        buyerViewModel.postOrderBuy(
            accessToken, id, bidPrice).observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    showSnackbar()
                    binding.fabItemDetail.text = "Menunggu Respon Penjual"
                    binding.fabItemDetail.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey_etborder))
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun showSnackbar(){
        val itemDetailPage = binding.flItemDetailPage
        val position = binding.myCoordinatorLayout

        val customView = LayoutInflater.from(requireContext()).inflate(R.layout.snackbar_custom, null)
        val snackbar = Snackbar.make(itemDetailPage, "", Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        snackbarLayout.setPadding(0, 0, 0, 0)

        customView.findViewById<ImageButton>(R.id.btn_snackbar_close).setOnClickListener {
            snackbar.dismiss()
        }
        snackbarLayout.addView(customView)
        snackbar.setAnchorView(position).show()
    }
}