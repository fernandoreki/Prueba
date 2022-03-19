package com.example.myfirstapp;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRScanner extends AppCompatActivity {
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    SurfaceView cameraView;
    TextView qrResult;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_qr);

        //Texto con el resultado del qr
        qrResult = (TextView) findViewById(R.id.resultado_qr);
        //Vista de la cámara
        cameraView = (SurfaceView) findViewById(R.id.camera_view);

        //Crea el lector de qr
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        //Crea la camara
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .build();

        // Prepara el lector de qr
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                //Verifica si el usuario ha dado permiso para la camara
                if (ContextCompat.checkSelfPermission(getBaseContext(),  android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        //Establece la función al escanear un código
        barcodeDetector.setProcessor(new Detector.Processor() {
            @Override
            public void release() { }

            @Override
            public void receiveDetections(Detector.Detections detections) {
                final SparseArray barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    //Establece el valor del qr en el textview
                    qrResult.post(new Runnable() {
                        public void run() {
                            qrResult.setText(barcodes.valueAt(0).toString());
                        }
                    });
                    //Cierra el detector de códigos
                    barcodeDetector.release();
                }
            }
        });
    }
}
