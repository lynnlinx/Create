package com.example.linxing.create;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.List;

/**
 * Created by linxing on 3/19/18.
 */

public class ScannerActivity extends AppCompatActivity implements IngredientUPCJsonData.OnDataAvailable {

    private static final String TAG = "ScannerActivity";
    private static final int PERMISSION_REQUEST = 1;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private TextView barcodeValue;
    private SurfaceView cameraView;
    private String upc;
    private AlertDialog mAlertDialog;
    private AlertDialog.Builder normalDialog;
    private Button returnButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ScannerActivity.this, IngredientActivity.class));
            }
        });
        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        barcodeValue = (TextView) findViewById(R.id.barcode_value);
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        if(!barcodeDetector.isOperational()){
            Log.w(TAG, "Detector dependencies are not yet available.");
            return;
        }

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();


        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ContextCompat.checkSelfPermission(ScannerActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannerActivity.this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    upc = barcodes.valueAt(0).displayValue;
                    showMultiBtnDialog();
                }
            }
        });
    }


    @Override
    public void onDataAvailable(List<Ingredient> data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {

            Log.d(TAG, "onDataAvailable: data is" + data);
        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable: failed with status" + status );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.release();
        barcodeDetector.release();
    }



    private void showMultiBtnDialog(){
        normalDialog =
                new AlertDialog.Builder(ScannerActivity.this);
        //normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("UPC detected").setMessage("The UPC for current ingredient is: " + upc);
        normalDialog.setPositiveButton("Back to ingredient list",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(new Intent(ScannerActivity.this, IngredientActivity.class));
                    }
                });
        normalDialog.setNeutralButton("Rescan",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (ContextCompat.checkSelfPermission(ScannerActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                cameraSource.start(cameraView.getHolder());
                            } else {
                                ActivityCompat.requestPermissions(ScannerActivity.this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        normalDialog.setNegativeButton("Add to list", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadUPC(upc);
                finish();
                startActivity(new Intent(ScannerActivity.this, IngredientActivity.class));
            }
        });


        ScannerActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                }
                cameraSource.stop();
                mAlertDialog = normalDialog.create();
                mAlertDialog.show();
            }
        });

    }

    private void loadUPC(String s) {
        IngredientUPCJsonData ingredientUPCJsonData = new IngredientUPCJsonData(this, "https://trackapi.nutritionix.com/v2/search/item", true);
        ingredientUPCJsonData.execute(s);
    }
}
