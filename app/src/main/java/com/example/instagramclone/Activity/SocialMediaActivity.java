package com.example.instagramclone.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.instagramclone.Adapter.TabAdapter;
import com.example.instagramclone.R;
import com.google.android.material.tabs.TabLayout;
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


public class SocialMediaActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 2000;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabAdapter mTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        mToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(mToolbar);
        mViewPager = findViewById(R.id.viewPager);
        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabAdapter);
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager, false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.mntSharePic:
                isReadingStoragePermissionForSharePic();
                break;
            case R.id.mntLogout:
                ParseUser.getCurrentUser().logOut();
                finish();
                Intent intent = new Intent(this,LogInActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);

    }


    private void isReadingStoragePermissionForSharePic() {


        PermissionListener permissionListener = new PermissionListener() {

            @Override
            public void onPermissionGranted() {
                getChosenImage();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };


        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();

    }


    private void getChosenImage() {

        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {

            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                Bitmap receivedImage = BitmapFactory.decodeFile(picturePath);
                cursor.close();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                receivedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                ParseFile imageFile = new ParseFile("image.png", byteArray);
                ParseObject parseObject = new ParseObject("Photo");
                parseObject.put("picture", imageFile);
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
                progressDialog.setMessage("Loading...");
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(getApplicationContext(), "Successfully updated the description with the image",
                                    Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        } else {
                            FancyToast.makeText(getApplicationContext(), " " + e.getMessage(),
                                    Toast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(getApplicationContext(), "Try Again!!", Toast.LENGTH_SHORT).show();
        }


    }
}
