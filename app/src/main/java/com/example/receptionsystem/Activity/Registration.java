package com.example.receptionsystem.Activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import com.example.receptionsystem.Adaptor.AutoCompleteTxtListAdapter;
import com.example.receptionsystem.ApiCalling;
import com.example.receptionsystem.Model.ImageFile;
import com.example.receptionsystem.Model.AutoCompleteTxtModel;
import com.example.receptionsystem.Model.RegistrationModel;
import com.example.receptionsystem.Model.RegistrationResponseModel;
import com.example.receptionsystem.Model.StatusResp;
import com.example.receptionsystem.R;
import com.example.receptionsystem.SharedPrefManager;
import com.example.receptionsystem.databinding.RegistrationBinding;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    List<RegistrationResponseModel> registrationResponseModels = new ArrayList<>();
    private static final String TAG = "Registration";
    RegistrationBinding registrationBinding;
    String uuidForPersonalPhoto, date;
    byte[] byteArray;
    List<AutoCompleteTxtModel> list=new ArrayList<>();
    List<String> mStringList;
    Integer PersonPhotoLength, idPhotoLength;
    private static final int pic_id = 123;
    private static final int picture_id = 111;
    File personalPhotos, IdPhotos;
    private MaterialAutoCompleteTextView autoCompleteTextView;
    AutoCompleteTxtListAdapter myListAdapter;
    private SharedPrefManager sharedPrefManager;
    private String currentPhotoPath;
    public Boolean photoTaken = false;
    private Uri mImageCaptureUri;
    public File photoFile = null, photoPath = null;
    private String userId, fileName, imgName, accessToken;
    private String finalFileName;
    private String name, mobileNo, address, visit, idNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrationBinding = RegistrationBinding.inflate(getLayoutInflater());
        setContentView(registrationBinding.getRoot());


        sharedPrefManager = new SharedPrefManager(this);
        userId = sharedPrefManager.getStringValue("username");
        accessToken = "Bearer " + sharedPrefManager.getStringValue("accessToken");


        registrationBinding.personPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePhoto();
            }
        });
        registrationBinding.idPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
        /*registrationBinding.idPhoto.setOnClickListener(v -> {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, picture_id);
        });*/
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, 1);
            this.autoCompleteTextView = (MaterialAutoCompleteTextView) registrationBinding.To;
            this.list.add(new AutoCompleteTxtModel("Ranajeet", "4984989849"));
            this.list.add(new AutoCompleteTxtModel("Balaram Jena", "5649845425"));
            this.list.add(new AutoCompleteTxtModel("Niranjan", "6549849841"));
            this.list.add(new AutoCompleteTxtModel("Rana", "85598946184"));
            this.list.add(new AutoCompleteTxtModel("Sakti", "84665498484"));
            this.list.add(new AutoCompleteTxtModel("Abhisek", "8466549885"));
            this.list.add(new AutoCompleteTxtModel("Abhi", "8466549785"));
            this.list.add(new AutoCompleteTxtModel("Sagen", "8469549785"));
            this.myListAdapter = new AutoCompleteTxtListAdapter(this, R.layout.item_auto_complete_txt, this.list);
            this.autoCompleteTextView.setThreshold(1);
            this.autoCompleteTextView.setAdapter(this.myListAdapter);


        registrationBinding.registration.setOnClickListener(v -> {
            if (TextUtils.isEmpty(registrationBinding.userName.getText().toString())) {
                Toast.makeText(Registration.this, "Username Required", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(registrationBinding.userMobileNumber.getText().toString()) && (registrationBinding.userMobileNumber.getText().toString()).length() != 10) {
                Toast.makeText(Registration.this, "Mobile Number required", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(registrationBinding.From.getText().toString())) {
                Toast.makeText(Registration.this, "Where from is Required", Toast.LENGTH_SHORT).show();
                // login();
            } else if (TextUtils.isEmpty(registrationBinding.To.getText().toString())) {
                Toast.makeText(Registration.this, "Whom To Visited Is Required", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(registrationBinding.IdNumber.getText().toString())) {
                Toast.makeText(Registration.this, "Your Id Number Is required", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(registrationBinding.personPhoto.toString())) {
                Toast.makeText(Registration.this, "Your Personal Photo Is Required", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(registrationBinding.idPhoto.toString())) {
                Toast.makeText(Registration.this, "Your Id Photo Is required", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("photoPath", "onCreate: Image file is   " + photoPath);
                Log.d("photoFile", "onCreate:  Image2 path is " + photoFile);
                Log.d("UserId", "onCreate: user ID is " + userId);
                Log.d("FileName", "onCreate: " + fileName);
                Log.d("PersonPhotoLength", "onCreate: " + PersonPhotoLength);
                name = registrationBinding.userName.getText().toString();
                mobileNo = registrationBinding.userMobileNumber.getText().toString();
                address = registrationBinding.From.getText().toString();
                visit = registrationBinding.To.getText().toString();
                idNumber = registrationBinding.IdNumber.getText().toString();
                registration();
            }
        });
    }

    public int gen() {
        Random r = new Random(System.currentTimeMillis());
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            photoPath = createImagePath();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (photoPath != null) {
            mImageCaptureUri = FileProvider.getUriForFile(
                    Registration.this, "com.example.receptionsystem.fileprovider", photoPath);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            someActivityResultLauncher1.launch(takePictureIntent);
        }
    }

    private File createImagePath() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        fileName = userId + "_" + timeStamp;
        File storageDir = Registration.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                fileName,
                /* prefix */
                ".jpg",   /* suffix */
                storageDir /* directory */

        );
        photoTaken = true;
        Log.d("fileName", "createImagePath: " + fileName);
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        // Log.d("currentPhotoPath", "createImageFile: Current Photo Path is  "+currentPhotoPath);
        return image;
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    File imgFile = new File(currentPhotoPath);
                    Log.d("imageFile", ": Image File Is  " + imgFile);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteArray = stream.toByteArray();
                        PersonPhotoLength = byteArray.length;
                        registrationBinding.idPhoto.setImageBitmap(myBitmap);
                    }
                }
            });


    private void capturePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (photoFile != null) {
            mImageCaptureUri = FileProvider.getUriForFile(
                    Registration.this, "com.example.receptionsystem.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            someActivityResultLauncher.launch(takePictureIntent);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imgName = userId + "_" + timeStamp;
        File storageDir = Registration.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imgName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir /* directory */
        );
        photoTaken = true;
        // Save a file: path for use with ACTION_VIEW intents
        Log.d("ImageName", "createImageFile: " + imgName);
        currentPhotoPath = image.getAbsolutePath();
        // Log.d("currentPhotoPath", "createImageFile: Current Photo Path is  "+currentPhotoPath);
        return image;
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    File imgFile = new File(currentPhotoPath);
                    Log.d("imageFile", ": Image File Is  " + imgFile);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteArray = stream.toByteArray();
                        PersonPhotoLength = byteArray.length;
                        registrationBinding.personPhoto.setImageBitmap(myBitmap);
                    }
                }
            });

   /* public static String getFileNameFromURL(URL url) {
        String fileName = url.getFile();
        return fileName.substring(fileName.lastIndexOf('/') + 1);
    }*/

    public String CurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        return sdf.format(new Date());
    }
  /*  public static String getSystemName() {
        return "android" + android.os.Build.VERSION.SDK;
    }*/

    public String getPhoneName() {
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
        }
        String deviceName = myDevice.getName();
        return deviceName;
    }

    public void uploadImage() {
        File mFile = new File(currentPhotoPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", finalFileName, requestFile);
        Log.d(TAG, "uploadImage:  File Name " + finalFileName);
        ApiCalling.getService().getImage(accessToken, body).enqueue(new Callback<StatusResp>() {
            @Override
            public void onResponse(Call<StatusResp> call, Response<StatusResp> response) {
                if (response.isSuccessful() & response.code() == 201) {
                    String res = response.body().getStatusDesc();
                    Log.d("Response", "onResponse: Response Body is " + res);
                    Toast.makeText(Registration.this, "Successful Stored data ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registration.this, RegistrationActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<StatusResp> call, Throwable t) {
                t.printStackTrace();
                Log.e("TAG", t.getMessage());
            }
        });
    }

    public void registration() {

        RegistrationModel registrationPayload = new RegistrationModel();
        registrationPayload.setName(name);
        registrationPayload.setMobileNo(mobileNo);
        registrationPayload.setAddress(address);
        registrationPayload.setUserId(userId);
        registrationPayload.setWhomToVisit(visit);
        registrationPayload.setIdNumber(idNumber);

        List<ImageFile> imgData = new ArrayList<>();
        ImageFile image = new ImageFile();
        image.setUserId(userId);
        image.setImgType("personPhoto");
        finalFileName = imgName + "_" + mobileNo + ".jpg";
        image.setFileName(finalFileName);
        image.setFileType("jpg");
        image.setSize(PersonPhotoLength);
//        image.setUuid(uuidForPersonalPhoto);
//        image.setSystemName(getPhoneName());
        //image.setData(list);
        image.setVisitorRegDto(CurrentDateTime());
        imgData.add(image);
        registrationPayload.setImages(imgData);


        Log.d("Value ", " Token value is  " + registrationPayload.toString());

        ApiCalling.getService().getRegistration(accessToken, registrationPayload).enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(Call<RegistrationResponseModel> call, Response<RegistrationResponseModel> response) {
                if (response.isSuccessful() & response.code() == 200) {
                    Log.d("Upload", "onResponse: Goes on Upload Image ");
                    uploadImage();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponseModel> call, Throwable t) {
                Toast.makeText(Registration.this, "Something Went TO Wrong ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registration.this, Registration.class);
                startActivity(intent);
            }
        });

    /*    ApiCalling.getService().getRegistration(accessToken, registrationPayload).enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(Call<RegistrationResponseModel> call, Response<RegistrationResponseModel> response) {
                if (response.isSuccessful() & response.code() == 200) {
                    List<RegistrationResponseModel> registrationResponseModels = new ArrayList<>();
                    //registrationResponseModels= response.body();
                    uploadImage();
//                    Toast.makeText(Registration.this, "Successful Stored data ", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Registration.this, RegistrationActivity.class);
//                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponseModel> call, Throwable t) {
                Toast.makeText(Registration.this, "Something Went TO Wrong ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registration.this, Registration.class);
                startActivity(intent);
            }
        });*/
    }
}