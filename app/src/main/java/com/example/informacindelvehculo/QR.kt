package com.example.informacindelvehculo

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.informacindelvehculo.databinding.ActivityQrBinding
import java.io.IOException
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.gms.vision.Detector.Detections
import okhttp3.*

class QR : AppCompatActivity() {
    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private lateinit var binding: ActivityQrBinding

    val ruta: String = "https://fer-sepulveda.cl/API_PRUEBA2/api-service.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (ContextCompat.checkSelfPermission(
                this@QR, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setupControls()
        }

        val aniSlide: Animation =
            AnimationUtils.loadAnimation(this@QR, R.anim.scanner_animation)
        binding.barcodeLine.startAnimation(aniSlide)
    }


    private fun setupControls() {
        barcodeDetector =
            BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()

        binding.cameraSurfaceView.getHolder().addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    //Start preview after 1s delay
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })


        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(applicationContext, "Scanner has been closed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun receiveDetections(detections: Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue


                    //Don't forget to add this line printing value or finishing activity must run on main thread
                    runOnUiThread {
                        cameraSource.stop()
                        Toast.makeText(this@QR, "value- $scannedValue", Toast.LENGTH_SHORT).show()
                        AsistenciaAlmacenar()
                        //finish()
                    }
                }else
                {
                        Toast.makeText(this@QR, "value- else", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this@QR,
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }
    fun AsistenciaAlmacenar()
    {
        val mensajeEntrada= findViewById<TextView>(R.id.txt_viewQR).text.toString()
        val correo  = findViewById<EditText>(R.id.txt_correo_registro).text.toString()
        val client = OkHttpClient()
        val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
        var parts = mensajeEntrada.split("|")
        var mensajeEntrada1 = parts[0]
        var mensajeEntrada2 = parts[1]

        val json = "{\"nombreFuncion\": \"AsistenciaAlmacenar\",\"parametros\": [\" "+ correo +"\", \" " + mensajeEntrada1+"\"]}"
        val body: RequestBody = RequestBody.create(mediaType, json)
        val request: Request =  Request.Builder().url(ruta).post(body).build()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("TPE: La petición fallo")
            }

            override fun onResponse(call: Call, response: Response) {
                println("TPE: La petición funciono con éxito")
            }

        })
        println("Entraste")
        runOnUiThread{
            (Toast.makeText(applicationContext,
                "Bienvenido tu ingreso ha sido con fecha: " + mensajeEntrada2,Toast.LENGTH_LONG).show())
        }

    }
}
