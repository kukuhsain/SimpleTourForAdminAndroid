package com.kukuhsain.simpletour.host.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kukuhsain.simpletour.host.R;
import com.kukuhsain.simpletour.host.model.remote.SimpleTourApi;
import com.kukuhsain.simpletour.host.utility.FileUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kukuh on 20/10/16.
 */

public class AddDestinationActivity extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 1;

    @BindView(R.id.iv_preview_image) ImageView ivPreviewImage;
    @BindView(R.id.et_title) EditText etTitle;
    @BindView(R.id.et_content) EditText etContent;
    @BindView(R.id.et_location) EditText etLocation;

    private String imagePath;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    @OnClick(R.id.btn_add)
    public void addDestination() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String location = etLocation.getText().toString();

        if (title.isEmpty()) {
            etTitle.setError("You must input the name");
            etTitle.requestFocus();
        } else if (content.isEmpty()) {
            etContent.setError("You must input the description");
            etContent.requestFocus();
        } else if (location.isEmpty()) {
            etLocation.setError("You must input the location");
            etLocation.requestFocus();
        } else {
            progressDialog.show();
            SimpleTourApi.getInstance()
                    .addDestination(title, content, location, imagePath)
                    .subscribeOn(Schedulers.io())
                    .subscribe(destination -> {
                        runOnUiThread(() -> {
                            if (progressDialog!=null) {
                                progressDialog.hide();
                            }
                            Toast.makeText(this, "Successful...", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }, throwable -> {
                        throwable.printStackTrace();
                        runOnUiThread(() -> {
                            if (progressDialog!=null) {
                                progressDialog.hide();
                            }
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    });
        }
    }

    @OnClick(R.id.btn_insert_image)
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d("onActivityResult...");
        if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            Uri uri = data.getData();
            imagePath = FileUtils.getRealPathFromUri(this, uri);
            Timber.d("imagePath... "+imagePath);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ivPreviewImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Timber.d("onPermissionResult...");
        Timber.d(permissions[0]);
    }
}
