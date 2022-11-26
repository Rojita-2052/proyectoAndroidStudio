package com.example.informacindelvehculo

import android.annotation.SuppressLint
import android.content.Intent
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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*

class QR : AppCompatActivity() {
    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private lateinit var binding: ActivityQrBinding
    val db: DbHandler = DbHandler(this)
    var CORREO_INSPECTOR: String = "";


    val ruta: String = "https://fer-sepulveda.cl/API_PRUEBA2/api-service.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrBinding.inflate(layoutInflater)
        val view = binding.root
        CORREO_INSPECTOR = db.userIsLogged()
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
                        AsistenciaAlmacenar(scannedValue);
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
    fun AsistenciaAlmacenar(scannedValue: String)
    {
        println("TPE: ENTRA ASISTENCIA ALMACENAR ->" + scannedValue);
        val client = OkHttpClient()
        val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
        var parts = scannedValue.split("|")
        var mensajeEntrada1 = parts[0]
        var mensajeEntrada2 = parts[1]

        val json = "{\"nombreFuncion\": \"AsistenciaAlmacenar\",\"parametros\": [\" "+ CORREO_INSPECTOR +"\", \" " + mensajeEntrada1+"\"]}"
        val body: RequestBody = RequestBody.create(mediaType, json)
        val request: Request =  Request.Builder().url(ruta).post(body).build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("TPE: La petición fallo")
            }

            override fun onResponse(call: Call, response: Response) {
                println("TPE: La petición funciono con éxito")
                val jsonData = response.body()!!.string()
                println("TPE: JSON" + jsonData);

                val obj = Json.decodeFromString<RespuestaInspecciones>(jsonData.toString())
                if (obj.result[0].RESPUESTA == "OK") {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Bienvenido tu ingreso ha sido con fecha: " + mensajeEntrada2,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    val intent = Intent(applicationContext, Opciones::class.java)
                    startActivity(intent)
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Error QR ya utilizado con fecha: " + mensajeEntrada2, Toast.LENGTH_LONG
                        ).show()
                    }
                    val intent = Intent(applicationContext, Opciones::class.java)
                    startActivity(intent)
                }
            }

        })
    }
}
