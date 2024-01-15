package com.example.galleryapp.ui.screens.upload

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors
import androidx.camera.core.ImageCapture


@SuppressLint("ClickableViewAccessibility")
@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onImageCaptured: (Uri) -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val executor = remember { ContextCompat.getMainExecutor(context) }

    var imageCapture: ImageCapture? = null

    AndroidView(
        factory = { context ->
            val view = PreviewView(context)
            val cameraExecutor = Executors.newSingleThreadExecutor()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val cameraProvider = cameraProviderFuture.get()
//            val preview = Preview.Builder().build().also {
//                Url.setSurfaceProvider(view.surfaceProvider)
//            }

//            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)

            imageCapture = ImageCapture.Builder().build()

            return@AndroidView view
        },
        update = { view ->
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            // Set up image capture
            imageCapture?.let { capture ->
                // Handle capture button click
                view.setOnTouchListener { _, event ->
                    if (event.action == MotionEvent.ACTION_UP) {
                        val imageFileName = "${System.currentTimeMillis()}_captured_image.jpg"
                        val contentValues = ContentValues().apply {
                            put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
                            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                        }

                        val contentResolver = context.contentResolver
                        val outputUri = contentResolver.insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            contentValues
                        )

                        val outputOptions = ImageCapture.OutputFileOptions.Builder(
                            contentResolver,
                            outputUri!!,
                            contentValues
                        ).build()

                        capture.takePicture(
                            outputOptions, executor,
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onError(
                                    error: ImageCaptureException
                                ) {
                                    // Handle error
                                }

                                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                    onImageCaptured(outputUri)
                                }
                            })
                    }
                    true
                }
            }
        }
    )
}

@Preview
@Composable
fun CameraPreviewPreview(){
    CameraPreview(onImageCaptured = {})
}