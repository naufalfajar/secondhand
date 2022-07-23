package id.finalproject.binar.secondhand.fragment.sell

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.MainActivity
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentPreviewBinding
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.model.network.response.GetUserItem
import id.finalproject.binar.secondhand.repository.toRp
import id.finalproject.binar.secondhand.viewmodel.SellerProductViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
@AndroidEntryPoint
class PreviewFragment : Fragment() {
    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!

    private val sellerProductViewModel: SellerProductViewModel by viewModels()

    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var accessToken: String
    private lateinit var body: PostProductRequestBody
    private lateinit var formBundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getProductData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPrefs = SharedPreferences(requireContext())
        if(sharedPrefs.getLogin()){
            accessToken = sharedPrefs.getToken().toString()
            postProduct()
        }
        toFormJualPage()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            setFragmentResult("backFromPreview", bundleOf("bundleKey" to formBundle))
            findNavController().popBackStack()
        }
    }

    private fun toFormJualPage() {
        binding.btnBack.setOnClickListener {
            setFragmentResult("backFromPreview", bundleOf("bundleKey" to formBundle))
            it.findNavController().popBackStack()
        }
    }

    private fun postProduct() {
        binding.btnTerbitkan2.setOnClickListener {
            observePost()
        }
    }



    private fun getProductData(){
        setFragmentResultListener("requestKey") { _, bundle ->
            formBundle = bundle.getBundle("bundleKey")!!
            val name = formBundle.getString("name").toString()
            val price = formBundle.getString("price").toString()
            val description = formBundle.getString("description").toString()
            val categoryId = formBundle.getIntegerArrayList("categoryId")!!.joinToString()
            val categoryName = formBundle.getStringArrayList("categoryName")!!.joinToString()
            var location = formBundle.getString("location").toString()
            val path = formBundle.getString("path").toString()
            val imageFile = File(path)

            if (location.isEmpty()) location = "Unknown"

            binding.apply {
                tvDeskripsi.text = description
                tvItemNama.text = name
                tvItemCategory.text = categoryName
                tvItemHarga.text = price.toInt().toRp()
                vpImage.setImageURI(path.toUri())
            }
            addProduct(name,price,description,categoryId,location,imageFile)
            observeSellerInfo()
        }
    }

    private fun addProduct(name: String, price: String, description: String, category: String, location: String,
                           imageFile: File
    ) {
        val namebody = name.toRequestBody("text/plain".toMediaType())
        val priceBody = price.toRequestBody("text/plain".toMediaType())
        val descriptionBody = description.toRequestBody("text/plain".toMediaType())
        val categoryBody = category.toRequestBody("text/plain".toMediaType())
        val locationBody = location.toRequestBody("text/plain".toMediaType())

        val imageBody = imageFile.asRequestBody("image/png".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData("image", imageFile.name, imageBody)

        body = PostProductRequestBody(namebody, priceBody, descriptionBody, categoryBody, locationBody, image)
    }

    private fun observePost(
    ){
        sellerProductViewModel.postProduct(
            accessToken,
            body.nameBody,
            body.descriptionBody,
            body.priceBody,
            body.categoryBody,
            body.locationBody,
            body.image
        ).observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    val bundle = Bundle()
                    bundle.putBoolean("addProduct", true)

                    val intent = Intent(this@PreviewFragment.requireContext(), MainActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent, bundle)
                    requireActivity().finish()
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun observeSellerInfo(){
        sellerProductViewModel.getUser(
        accessToken
        ).observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { it1 -> setSellerInfo(it1) }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun setSellerInfo(sellerInfo: GetUserItem){
        if(sellerInfo.imageUrl.isNullOrEmpty()){
            Glide.with(this).load(R.drawable.profile_picture).into(binding.ivSellerInfo)
        }else
            Glide.with(this).load(sellerInfo.imageUrl).into(binding.ivSellerInfo)
        if(sellerInfo.city.isEmpty()){
            binding.tvSellerCity.text = "Unknown"
        }else
            binding.tvSellerCity.text = sellerInfo.city
        binding.tvSellerNama.text = sellerInfo.fullName
    }
}