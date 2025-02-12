package com.zephgv.mad.fitzee;

import android.os.Bundle;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

public class Calorie<PreviewView> extends AppCompatActivity {

    private PreviewView previewView;

    @Override
    protected <ProcessCameraProvider> void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);

        previewView = findViewById(R.id.previewView);

        // Initialize camera provider
        ProcessCameraProvider cameraProvider = ProcessCameraProvider.getInstance(this);

        cameraProvider.addListener(() -> {
            CameraSelector cameraSelector = new CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build();

            Preview preview = new Preview.Builder().build();
            preview.setSurfaceProvider(previewView.getSurfaceProvider());

            Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview);
        }, ContextCompat.getMainExecutor(this));

    }


    ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this),new ImageAnalysis.Analyzer()

    {
        @Override
        public void analyze (@NonNull ImageProxy image){
        // Process the captured image (e.g., convert to Bitmap)
        Bitmap bitmap = imageToBitmap(image);

        // Perform inference with TensorFlow using the captured image
        runInferenceWithTensorFlow(bitmap);

        image.close();
    }
    });

    private Bitmap imageToBitmap(ImageProxy image) {
        Image mediaImage = image.getImage();
        if (mediaImage != null) {
            return ImageUtils.imageToBitmap(mediaImage);
        }
        return null;
    }

    private void runInferenceWithTensorFlow(Bitmap bitmap) {
        try (SavedModelBundle model = SavedModelBundle.load(modelDir, "serve")) {
            try (TensorFlow<TFloat32> tf = model.session()) {
                // Preprocess the bitmap (e.g., resize, normalize)
                float[][] inputData = preprocessBitmap(bitmap);

                // Create input tensor
                try (Tensor<TFloat32> inputTensor = TFloat32.tensorOf(inputData)) {
                    // Run inference
                    Tensor<TFloat32> outputTensor = tf.runner()
                            .feed("input", inputTensor)
                            .fetch("output")
                            .run()
                            .get(0)
                            .expect(TFloat32.DTYPE);

                    // Process the output tensor (e.g., get predictions)
                    float[][] outputData = outputTensor.getData();

                    // Handle the output (e.g., update UI with predictions)
                    handleModelOutput(outputData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}