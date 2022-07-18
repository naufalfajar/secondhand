package id.finalproject.binar.secondhand.fragment.sell

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import id.finalproject.binar.secondhand.AuthActivity
import id.finalproject.binar.secondhand.MainActivity
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.FragmentFormJualBinding
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.repository.CategoryRepository
import id.finalproject.binar.secondhand.repository.SellerAddProductRepository
import id.finalproject.binar.secondhand.repository.viewModelsFactory
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.viewmodel.SellerProductViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*


class FormJualFragment : Fragment() {
    private var _binding: FragmentFormJualBinding? = null
    private val binding get() = _binding!!

    private val apiService: ApiService by lazy { ApiClient.instance }
    private val sellerAddProductRepository: SellerAddProductRepository by lazy {SellerAddProductRepository(apiService)}
    private val sellerProductViewModel: SellerProductViewModel by viewModelsFactory {SellerProductViewModel(sellerAddProductRepository)}
    private lateinit var sharedPref: SharedPreferences

    private lateinit var accessToken: String
    private lateinit var body: PostProductRequestBody
    private var path: String = ""
    private val arrayCategoryId: ArrayList<Int> = ArrayList()
    private val arrayCategoryName: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormJualBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

        val kategori = resources.getStringArray(R.array.kategori)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, kategori)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreferences(requireContext())
        if (sharedPref.getLogin()) {
            accessToken = sharedPref.getToken().toString()
            toPreviewPage()
            toDaftarJualPage()

            if(path.isNotEmpty()){
                binding.imageView2.setImageURI(path.toUri())
            }

            binding.imageView2.setOnClickListener {
                checkingPermissions()
            }
            binding.arrowBack.setOnClickListener {
                findNavController().popBackStack()
            }
        } else {
            binding.apply {
                clContent.visibility = View.INVISIBLE
                ifnotlogin.isVisible = true

                btnLogin.setOnClickListener {
                    val intent =
                        Intent(this@FormJualFragment.requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }

    private fun toPreviewPage() {
        binding.btnPreview.setOnClickListener{
            previewProcessData()
        }
    }

    private fun toDaftarJualPage() {
        binding.btnTerbitkan.setOnClickListener{
            processData()
        }
    }

    private fun createChips(idCategory: Int, categoryName: String) {
        val chip = Chip(requireContext())
        chip.apply {
            text = categoryName
            isCloseIconVisible = true
            isClickable = true
            isCheckable = false
            binding.chipGroup.addView(chip as View)
            setOnCloseIconClickListener {
                // Hapus view chips
                binding.chipGroup.removeView(chip as View)
                // Hapus element pada ArrayList
                arrayCategoryId.remove(idCategory)
                arrayCategoryName.remove(categoryName)
            }
        }
    }

    private fun processData(){
        binding.apply {
            val productName = etNamaProduk.text.toString()
            val productPrice = etHargaProduk.text.toString()
            val productDescription = etDeskripsiProduk.text.toString()
//            val productCategory = autoCompleteTextView.text.toString()
            val productCategory = "39"
            val location = etLocation.text.toString()

            if(checkField(productName,productPrice,productDescription,productCategory, location)){
                val imageFile = File(path)
                addProduct(productName, productPrice,productDescription, productCategory, location, imageFile)
                observePost()
            }
        }
    }

    private fun previewProcessData(){
        binding.apply {
            val productName = etNamaProduk.text.toString()
            val productPrice = etHargaProduk.text.toString()
            val productDescription = etDeskripsiProduk.text.toString()
//            val productCategory = autoCompleteTextView.text.toString()
            val productCategory = "39"
            val location = etLocation.text.toString()

            if(checkField(productName,productPrice,productDescription,productCategory, location)){
                bundlingPreview(productName,productPrice,productDescription,productCategory,location,path)
                findNavController().navigate(FormJualFragmentDirections.actionFormJualFragmentToPreviewFragment())
            }
        }
    }

    private fun bundlingPreview(
        name: String, price: String, description: String, category: String, location: String, path: String
    ){
        val bundle = Bundle()
        bundle.putString("name", name)
        bundle.putString("price", price)
        bundle.putString("description", description)
        bundle.putString("category", category)
        bundle.putString("location", location)
        bundle.putString("path", path)

        setFragmentResult("requestKey", bundleOf("bundleKey" to bundle))
    }

    private fun checkField(productName: String, productPrice: String, productDescription: String, productCategory: String, productLocation: String): Boolean {
        if (productName.isEmpty()) {
            binding.etNamaProduk.error = "Silahkan masukkan nama produk!"
            return false
        }
        if (productPrice.isEmpty()) {
            binding.etHargaProduk.error = "Silahkan masukkan harga produk!"
            return false
        }
        if (productCategory.isEmpty()) {
            binding.autoCompleteTextView.error = "Silahkan pilih kategori!"
            return false
        }
        if (productDescription.isEmpty()) {
            binding.etDeskripsiProduk.error = "Silahkan masukkan deskripsi produk!"
            return false
        }
        if (productLocation.isEmpty()) {
            binding.etDeskripsiProduk.error = "Silahkan masukkan lokasi!"
            return false
        }
        if (path.isEmpty()) {
            binding.textView6.error = "Silahkan upload Gambar"
            return false
        }
        return true
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
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFzZGFzZEBnbWFpbC5jb20iLCJpYXQiOjE2NTY0OTgyMjh9.l25knICph9-8ZBanO08PHTMhzMr4kJGabGekEvx2Djw",
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

                    val intent = Intent(this@FormJualFragment.requireContext(), MainActivity::class.java)
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

    private fun checkingPermissions() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                1,
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun isGranted(activity: Activity, permission: String, permissions: Array<String>, request: Int): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    //------------------------------------------------------------------------
    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.imageView2.setImageURI(result)

            path = getRealPathFromURI(requireContext(), result!!)!!
        }

    private fun openGallery() {
        requireActivity().intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val bitmap = result.data!!.extras?.get("data") as Bitmap

                val tempUri = getImageUriFromBitmap(requireContext(), bitmap)
                path = getRealPathFromURI(requireContext(), tempUri)!!

                binding.imageView2.setImageBitmap(bitmap)
            }
        }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }
    private fun getRealPathFromURI(context: Context, uri: Uri): String? {
        when {
            // DocumentProvider
            DocumentsContract.isDocumentUri(context, uri) -> {
                when {
                    // ExternalStorageProvider
                    isExternalStorageDocument(uri) -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":").toTypedArray()
                        val type = split[0]
                        // This is for checking Main Memory
                        return if ("primary".equals(type, ignoreCase = true)) {
                            if (split.size > 1) {
                                Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                            } else {
                                Environment.getExternalStorageDirectory().toString() + "/"
                            }
                            // This is for checking SD Card
                        } else {
                            "storage" + "/" + docId.replace(":", "/")
                        }
                    }
                    isDownloadsDocument(uri) -> {
                        val fileName = getFilePath(context, uri)
                        if (fileName != null) {
                            return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
                        }
                        var id = DocumentsContract.getDocumentId(uri)
                        if (id.startsWith("raw:")) {
                            id = id.replaceFirst("raw:".toRegex(), "")
                            val file = File(id)
                            if (file.exists()) return id
                        }
                        val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                        return getDataColumn(context, contentUri, null, null)
                    }
                    isMediaDocument(uri) -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":").toTypedArray()
                        val type = split[0]
                        var contentUri: Uri? = null
                        when (type) {
                            "image" -> {
                                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            }
                            "video" -> {
                                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            }
                            "audio" -> {
                                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            }
                        }
                        val selection = "_id=?"
                        val selectionArgs = arrayOf(split[1])
                        return getDataColumn(context, contentUri, selection, selectionArgs)
                    }
                }
            }
            "content".equals(uri.scheme, ignoreCase = true) -> {
                // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
            }
            "file".equals(uri.scheme, ignoreCase = true) -> {
                return uri.path
            }
        }
        return null
    }

    private fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                              selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            if (uri == null) return null
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs,
                null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }


    private fun getFilePath(context: Context, uri: Uri?): String? {
        var cursor: Cursor? = null
        val projection = arrayOf(
            MediaStore.MediaColumns.DISPLAY_NAME
        )
        try {
            if (uri == null) return null
            cursor = context.contentResolver.query(uri, projection, null, null,
                null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

}