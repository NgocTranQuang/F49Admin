package com.app.f49.bottomsheet.imageaction

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.FileProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.f49.BuildConfig
import com.app.f49.R
import com.app.f49.utils.FileCompressor
import com.app.f49.utils.ImageUtil
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.bootom_sheet_take_image.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class BottomSheetGetImageFragment : BottomSheetDialogFragment() {
    internal val REQUEST_TAKE_PHOTO = 1
    internal val REQUEST_GALLERY_PHOTO = 2
    internal val PIC_CROP = 3
    internal var mPhotoFile: File? = null
    internal var mCompressor: FileCompressor? = null
    internal var result: ((MutableList<String>, MutableList<String>) -> Unit)? = null
    internal var typePickImage: TypePickImage = TypePickImage.SINGLE_PICK

    companion object {
        fun show(
            fm: FragmentManager,
            typePickImage: TypePickImage = TypePickImage.SINGLE_PICK,
            result: ((MutableList<String>, MutableList<String>) -> Unit)? = null
        ) {
            val bottomSheetFragment = BottomSheetGetImageFragment()
            bottomSheetFragment.show(fm, bottomSheetFragment.tag)
            bottomSheetFragment.result = result
            bottomSheetFragment.typePickImage = typePickImage
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bootom_sheet_take_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCompressor = FileCompressor(context).setMaxHeight(500).setMaxWidth(500)
        setEventClick()
    }

    private fun setEventClick() {
        vTakeCamera.setOnSingleClickListener {
            requestStoragePermission(true)
        }
        vGetImage.setOnSingleClickListener {
            requestStoragePermission(false)
        }
        vCancel.setOnSingleClickListener {
            dismiss()
        }
    }

    /**
     * Capture image from camera
     */
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(context?.packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
                // Error occurred while creating the File
            }

            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    context ?: return,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile
                )

                mPhotoFile = photoFile
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)

            }
        }
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val mFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(mFileName, ".jpg", storageDir)
    }

    /**
     * Select image fro gallery
     */
    private fun dispatchGalleryIntent() {
        var pickPhoto: Intent? = null
        if (typePickImage == TypePickImage.SINGLE_PICK) {
            pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        } else {
            pickPhoto = Intent(
                Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            pickPhoto.type ="image/*"
            pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

        }
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO)
    }

    private fun requestStoragePermission(isCamera: Boolean) {
        Dexter.withActivity(context as Activity).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                // check if all permissions are granted
                if (report.areAllPermissionsGranted()) {
                    if (isCamera) {
                        dispatchTakePictureIntent()
                    } else {
                        dispatchGalleryIntent()
                    }
                }
                // check for permanent denial of any permission
                if (report.isAnyPermissionPermanentlyDenied) {
                    // show alert dialog navigating to Settings
                    showSettingsDialog()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: List<PermissionRequest>,
                token: PermissionToken
            ) {
                token.continuePermissionRequest()
            }
        }).withErrorListener { error ->
            Toast.makeText(
                context,
                "Error occurred! ",
                Toast.LENGTH_SHORT
            ).show()
        }
            .onSameThread()
            .check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_TAKE_PHOTO -> {
                    try {
                        mPhotoFile?.let {
                            mPhotoFile = mCompressor?.compressToFile(it)
                            handleReturnOneImage()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                REQUEST_GALLERY_PHOTO -> {
                    if (typePickImage == TypePickImage.SINGLE_PICK) {
                        val selectedImage = data?.data
                        try {
                            mPhotoFile = mCompressor?.compressToFile(File(getRealPathFromUri(selectedImage)))
                            handleReturnOneImage()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    } else {
                        data?.data?.let {
                            getFilePathMultifile(it)?.let {
                                mPhotoFile = mCompressor?.compressToFile(File(it))
                                handleReturnOneImage()
                            }
                        }
                        data?.clipData?.let {
                            handleReturnListImage(it)
                        }
                    }
                }
                PIC_CROP -> {

                }

            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            when (requestCode) {
                REQUEST_TAKE_PHOTO -> {
                    val fDelete = File(mPhotoFile?.path)
                    if (fDelete.exists()) {
                        fDelete.delete()
                    }
                }
            }
        }
    }

    fun handleCropPicture(picUri: Uri) {
        //call the standard crop action intent (the user device may not support it)
        var cropIntent = Intent("com.android.camera.action.CROP");
        //indicate image type and Uri
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, PIC_CROP);
    }

    fun handleReturnOneImage() {
        mPhotoFile?.let {
            var base64 = ImageUtil.convertImageFileToBase64(it)
            result?.invoke(mutableListOf(it.path), mutableListOf(base64))
        }
        dismiss()
    }

    fun handleReturnListImage(clipData: ClipData) {
        var listFile = mutableListOf<String>()
        var listBase64 = mutableListOf<String>()
        for (i in 0..(clipData.itemCount - 1)) {
            getFilePathMultifile(clipData.getItemAt(i).uri)?.let {
                mCompressor?.compressToFile(File(it))?.let {
                    listFile.add(it.path)
                    listBase64.add(ImageUtil.convertImageFileToBase64(it))
                }
            }
        }
        result?.invoke(listFile, listBase64)
        dismiss()
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private fun showSettingsDialog() {
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, which ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()

    }

    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context?.packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    fun getRealPathFromUri(contentUri: Uri?): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context?.contentResolver?.query(contentUri, proj, null, null, null)
            assert(cursor != null)
            val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            return cursor?.getString(column_index ?: -1) ?: ""
        } finally {
            cursor?.close()
        }
    }

    fun getFilePathMultifile(uri: Uri): String? {
        try {
            var file = File(uri.path)
            var filePath = file.path.split(":")
            var image_id = filePath[filePath.size - 1]

            var cursor = context?.contentResolver?.query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                MediaStore.Images.Media._ID + " = ? ",
                arrayOf(image_id),
                null
            )
            if (cursor != null) {
                cursor.moveToFirst()
                var imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                return imagePath
                cursor.close()
            }
        } catch (e: Exception) {
            return null
        }
        return null
    }

    enum class TypePickImage(var value: Int) {
        SINGLE_PICK(0), MULTI_PICK(1)
    }
}