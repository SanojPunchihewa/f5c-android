package com.example.hello_f5c;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,PermissionResultCallback{

    public Context context;
    ArrayList<String> permissions = new ArrayList<>();  // Permission which are needed to get the app working (they are added in the onCreate function)
    PermissionUtils permissionUtils;                    // An instance of the permissionUtils

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public void onStart()
    {
        permissionUtils.check_permission(permissions,"The app needs storage permission for reading images and camera permission to take photos",1);
        super.onStart();
    }

    /////////////////////////////
    // Permission functions
    /////////////////////////////

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    // Callback functions
    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION","GRANTED");
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY","GRANTED");
        permissionUtils.check_permission(permissions,"The app needs storage permission for reading images and camera permission to take photos",1);
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION","DENIED");
        permissionUtils.check_permission(permissions,"The app needs storage permission for reading images and camera permission to take photos",1);
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION","NEVER ASK AGAIN");
        permissionUtils.check_permission(permissions,"The app needs storage permission for reading images and camera permission to take photos",1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        // Setup the permissions
        permissionUtils = new PermissionUtils(MainActivity.this);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);


        findViewById(R.id.button_runf5c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prefix_path = "/storage/emulated/0/f5c/";
                // Code here executes on main thread after user presses button
                Log.d("main","f5c running ");
//                int result = init("f5c");
//                int result = init("f5c index");
//                int result = init("f5c index -d "+prefix_path+"test/ecoli_2kb_region/fast5_files/ "+prefix_path+"test/ecoli_2kb_region/reads.fasta");
//                int result = init("f5c call-methylation -b "+prefix_path+"test/ecoli_2kb_region/reads.sorted.bam -g "+prefix_path+"test/ecoli_2kb_region/draft.fa -r "+prefix_path+"test/ecoli_2kb_region/reads.fasta --secondary=yes --min-mapq=0 -B 2M > "+prefix_path+"test/ecoli_2kb_region/result.txt");
                int result = init("f5c eventalign -b "+prefix_path+"test/ecoli_2kb_region/reads.sorted.bam -g "+prefix_path+"test/ecoli_2kb_region/draft.fa -r "+prefix_path+"test/ecoli_2kb_region/reads.fasta --secondary=yes --min-mapq=0 -B 2M > "+prefix_path+"test/ecoli_2kb_region/f5c_event_align.txt");
//                int result = init("f5c meth-freq -i "+prefix_path+"test/ecoli_2kb_region/result.txt");
                Log.d("main","f5c ran " + result);
            }
        });
//        int result = init("f5c");
//        Log.d("main","f5c ran " + result);

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public static native int init(String command);

}
