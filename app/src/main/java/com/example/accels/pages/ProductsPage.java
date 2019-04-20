package com.example.accels.pages;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accels.Item.Products;
import com.example.accels.Item.Test;
import com.example.accels.R;
import com.example.accels.adapter.GridAdapter;
import com.example.accels.conditions.Controller;
import com.example.accels.retrofitapi.APIClient;
import com.example.accels.retrofitapi.APIInterface;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsPage extends AppCompatActivity implements View.OnClickListener {

    AppCompatButton selectBtn, upBtn;
    TextView title;
    ImageView image;
    Bitmap bitmap;
    List<Products>products;
    List<Test> demoproducts;
    Dialog dialog;
    Button camera;
    Intent intent;
    LinearLayout imageLayout;
    ImageView searchedImage;
    GridView gridView;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_page);

        verifyStoragePermissions(this);
        gridView = findViewById(R.id.gridview);
        imageLayout = findViewById(R.id.imageLayout);
        searchedImage = findViewById(R.id.searchImage);
        if(bitmap != null){
            imageLayout.setVisibility(View.VISIBLE);
            searchedImage.setImageBitmap(bitmap);
        }else{
            imageLayout.setVisibility(View.GONE);
        }

        dialog = new Dialog(ProductsPage.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.upload_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        selectBtn = dialog.findViewById(R.id.selectdBtn);
        upBtn = dialog.findViewById(R.id.uploadBtn);
        image = dialog.findViewById(R.id.image);

        selectBtn.setOnClickListener(this);
        upBtn.setOnClickListener(this);

        if(Controller.UPLOAD_IMAGE_FLAG==1){
            dialog.show();
            Controller.UPLOAD_IMAGE_FLAG = 0;
        }
           // dialog.show();

        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.selectdBtn:
                selectImage();
                break;

            case R.id.uploadBtn:
                uploadImage();
                break;

            default:
                break;
        }
    }

    private void uploadImage() {

        Toast.makeText(this, "Ready to upload", Toast.LENGTH_SHORT).show();

        String Title = "testImage";
        String Image = imagetTOString();

        APIInterface apiInterface = APIClient.getApiClient().create(APIInterface.class);

        //Call<List<Products>> call = apiInterface.uploadImage(Title,Image);
        Call<List<Test>> demos = apiInterface.demoData();

//        call.enqueue(new Callback<List<Products>>() {
////            @Override
////            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
////                Toast.makeText(ProductsPage.this, "response: "+response, Toast.LENGTH_SHORT).show();
////                products = response.body();
////                try {
////                    dialog.dismiss();
////                    searchedImage.setImageBitmap(bitmap);
////                }catch (Exception e){
////                        e.printStackTrace();
////                }
////            }
////
////            @Override
////            public void onFailure(Call<List<Products>> call, Throwable t) {
////                Toast.makeText(ProductsPage.this, "Faild", Toast.LENGTH_SHORT).show();
////                try {
////                    dialog.dismiss();
////                    searchedImage.setImageBitmap(bitmap);
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
////            }
////        });

        demos.enqueue(new Callback<List<Test>>() {
            @Override
            public void onResponse(Call<List<Test>> call, Response<List<Test>> response) {
                if(response.isSuccessful()){

                    demoproducts = response.body();
                    GridAdapter adapter = new GridAdapter(ProductsPage.this,demoproducts);
                    gridView.setAdapter(adapter);
                    dialog.dismiss();
                    searchedImage.setImageBitmap(bitmap);

                }
            }

            @Override
            public void onFailure(Call<List<Test>> call, Throwable t) {

            }
        });
    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("iamge/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,777);

    }

//    private void selectImage() {
//        final CharSequence[] items = {"Take Photo","Choose From Gallery", "Cancle"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(ProductsPage.this);
//        builder.setTitle("Add Photo");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                String selectedItem = items[item].toString();
//                if (selectedItem.equals("Take Photo")) {
//                        cameraIntent();
//                } else if (selectedItem.equals("Choose From Gallery")) {
//                        imagePickerIntent();
//                } else if (selectedItem.equals("Cancle")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }

    private void cameraIntent() {
        CropImage.activity().start(ProductsPage.this);
    }
    private void imagePickerIntent() {
        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 777);
    }


    private String imagetTOString() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageByte,Base64.DEFAULT);

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 777 && resultCode == RESULT_OK && data != null){

            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageLayout.setVisibility(View.VISIBLE);
                image.setImageBitmap(bitmap);
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                try {
                    dialog.show();
                }catch (Exception e){

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
