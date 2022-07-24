package id.finalproject.binar.secondhand.fragment.account

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.databinding.FragmentUpdateprofilBinding
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.network.Status
import id.finalproject.binar.secondhand.repository.UserRepository
import id.finalproject.binar.secondhand.repository.viewModelsFactory
import id.finalproject.binar.secondhand.service.ApiClient
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.viewmodel.UpdateViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File

@AndroidEntryPoint
class UpdateProfilFragment : Fragment() {

    private var _binding: FragmentUpdateprofilBinding? = null
    private val binding get() = _binding!!

    private var path: String = ""

    private val updateViewModel: UpdateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateprofilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = updateViewModel.token.toString()
        observeData(token)
        binding.apply {
            textUbahAkun.setOnClickListener { back() }
        }
        ivProfile()
        btnUpdate()
    }

    private fun btnUpdate() {
        binding.apply {
            btnEdit.setOnClickListener {
                val token = updateViewModel.token
                val email = updateViewModel.email
                val imageFile1 = File(path)
                val nama = etNama.text.toString()
                val kota = etKota.text.toString()
                val alamat = etAlamat.text.toString()
                val phone = etPhone.text.toString()

                when {
                    nama.isEmpty() -> {
                        etNama.error = "Masukkan Nama Anda!"
                    }
                    kota.isEmpty() -> {
                        etKota.error = "Masukkan Kota Anda!"
                    }
                    alamat.isEmpty() -> {
                        etAlamat.error = "Masukkan Alamat Anda!"
                    }
                    phone.isEmpty() -> {
                        etPhone.error = "Masukkan Nomor Telepon Anda!"
                    }
                    else -> {
                        if (token != null) {
                            update(
                                token = token,
                                nama = nama,
                                email = email!!,
                                phoneNumber = phone,
                                address = alamat,
                                imageFile = imageFile1,
                                city = kota
                            )
                        }
                        etNama.requestFocus()
                    }
                }
            }
        }
    }

    private fun update(
        token: String,
        nama: String,
        email: String,
        phoneNumber: String,
        address: String,
        imageFile: File,
        city: String
    ) {
        val reqFile = imageFile.asRequestBody("image/jpg".toMediaTypeOrNull())
        val nama1 = nama.toRequestBody("text/plain".toMediaType())
        val email1 = email.toRequestBody("text/plain".toMediaType())
        val phoneNumber1 = phoneNumber.toRequestBody("text/plain".toMediaType())
        val address1 = address.toRequestBody("text/plain".toMediaType())
        val image1 = MultipartBody.Part.createFormData("image", imageFile.name, reqFile)
        val city1 = city.toRequestBody("text/plain".toMediaType())

        updateViewModel.putUpdateUser(
            token,
            nama1,
            email1,
            phoneNumber1,
            address1,
            image1,
            city1
        ).observe(viewLifecycleOwner) {
            when(it!!.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Data Diperbarui.", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    it.message.let { it1 ->
                        Toast.makeText(requireContext(), it1, Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {}
            }
        }
    }

    private fun observeData(access_token: String) {
        updateViewModel.getUser(access_token).observe(viewLifecycleOwner) {
            when (it!!.status) {
                Status.SUCCESS -> {
                    binding.apply {
                        Glide.with(requireContext())
                            .load(it.data!!.imageUrl)
                            .into(imageView2)
                        etNama.setText(it.data.fullName)
                        etKota.setText(it.data.city)
                        etAlamat.setText(it.data.address)
                        etPhone.setText(it.data.phoneNumber)
                    }
                }
                Status.ERROR -> {
                    it.message.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {}
            }
        }
    }

    private fun back() {
        activity?.onBackPressed()
    }

    private fun ivProfile() {
        binding.apply {
            imageView2.setOnClickListener {
                cekPermission()
            }
        }
    }

    private fun cekPermission() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,
            )
        ) {
            pilihImageDialog()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            }
            else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

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

    private fun pilihImageDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Ganti Profil")
            .setPositiveButton("Camera") {_, _ -> openCamera()}
            .setNegativeButton("Galeri") {_, _ -> openGallery()}
            .show()
    }

    private fun openGallery() {
        requireActivity().intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            Glide.with(requireContext())
                .load(result)
                .into(binding.imageView2)

//            binding.imageView2.setImageURI(result)
            path = getRealPathFromURI(requireContext(), result!!)!!
//            binding.imageView2.background = null
        }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val bitmap = result.data!!.extras?.get("data") as Bitmap

                Glide.with(requireContext())
                    .load(bitmap)
                    .into(binding.imageView2)

                val tempUri = getImageUriFromBitmap(requireContext(), bitmap)
                path = getRealPathFromURI(requireContext(), tempUri)!!
//
//                binding.imageView2.setImageBitmap(bitmap)
//                binding.imageView2.background = null
            }
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

    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
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

    companion object {
        const val REQUEST_CODE_PERMISSION = 100
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }
}