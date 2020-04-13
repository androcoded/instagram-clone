package com.example.instagramclone.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instagramclone.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SharePicTap extends Fragment implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1000;
    private ImageView imgSharedPicture;
    private EditText edtSharedPictureDes;
    private Button btnSharePic;
    private Bitmap receivedImage;

    public SharePicTap() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_pic_tap, container, false);
        imgSharedPicture = view.findViewById(R.id.imgSharedPic);
        edtSharedPictureDes = view.findViewById(R.id.edtSharedPicDes);
        btnSharePic = view.findViewById(R.id.btnSharePic);
        imgSharedPicture.setOnClickListener(SharePicTap.this);
        btnSharePic.setOnClickListener(SharePicTap.this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSharedPic:
                isReadStoragePermissionGranted();
                break;
            case R.id.btnSharePic:
                if (receivedImage!=null){
                    if (edtSharedPictureDes.getText().toString().equals("")){

                        FancyToast.makeText(getContext(),"You must enter image description first!",
                                Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();

                    }else {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImage.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        ParseFile imageFile = new ParseFile("image.png",byteArray);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture",imageFile);
                        parseObject.put("imageDes",edtSharedPictureDes.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                        ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Loading...");
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e==null){
                                    FancyToast.makeText(getContext(),"Successfully updated the description with the image",
                                            Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                }else {
                                    FancyToast.makeText(getContext(), " "+e.getMessage(),
                                            Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                                }
                                progressDialog.dismiss();
                            }
                        });

                    }
                }else {
                    FancyToast.makeText(getContext(),"You must select image first!",
                            Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                }
                break;

        }

    }

    //Granting External Storage Permission

    private void isReadStoragePermissionGranted() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                getChosenImage();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();

            }
        };
        TedPermission.with(getContext())
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }


    //Getting image to the imageView using getChosenImage() and onActivityResult() methods

    private void getChosenImage() {

        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {

            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContext().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                receivedImage = BitmapFactory.decodeFile(picturePath);
                imgSharedPicture.setImageBitmap(receivedImage);
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), "Try Again!!", Toast.LENGTH_SHORT)
                    .show();
        }

    }

}
