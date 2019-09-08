package com.example.hello_f5c;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.developer.filepicker.controller.DialogSelectionListener;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {

    final static String TAG = "F5C-MAIN-ACTIVITY";

    EditText editTextTestDataPath;

    PermissionUtils permissionUtils;                    // An instance of the permissionUtils

    ArrayList<String> permissions = new ArrayList<>();

    ProgressDialog progressDialog;
    // Permission which are needed to get the app working (they are added in the onCreate function)

    private String testDataPath;

    public static native int init(String command);

    public static native int initminimap2(String command);

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the permissions
        permissionUtils = new PermissionUtils(MainActivity.this);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        editTextTestDataPath = findViewById(R.id.edit_text_data_path);
        editTextTestDataPath.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                openFileManager();
                return true;
            }
        });

        findViewById(R.id.btn_run_index).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(testDataPath)) {
                    showProgressWindow();
                    Log.d(TAG, "f5c index started");
//                    int result = init(
//                            "f5c index -d " + testDataPath + "/fast5_files/ " + testDataPath + "/reads.fasta");
                    // index -d /media/sanoj/New\ Volume/chr22_meth_example/fast5_files /media/sanoj/New\ Volume/chr22_meth_example/reads.fastq
                    int result = init(
                            "f5c index -d " + testDataPath + "/fast5_files/ " + testDataPath + "/reads.fasta");
                    Log.d(TAG, "f5c index ended " + result);
                    hideProgressWindow();
                } else {
                    Toast.makeText(MainActivity.this, "Please select a data directory first", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        findViewById(R.id.btn_call_meth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(testDataPath)) {
                    showProgressWindow();
                    Log.d(TAG, "f5c call-methylation started");
//                    int result = init("f5c call-methylation -b " + testDataPath + "/reads.sorted.bam -g " +
//                            testDataPath + "/draft.fa -r " + testDataPath
//                            + "/reads.fasta --secondary=yes --min-mapq=0 -B 2M > "
//                            + testDataPath + "/result.txt");

                    int result = init("f5c call-methylation -b " + testDataPath + "/reads.sorted.bam -g " +
                            testDataPath + "/draft.fa -r " + testDataPath
                            + "/reads.fasta --secondary=yes --min-mapq=0 -B 2M > "
                            + testDataPath + "/result.txt");

//                int result = init("f5c meth-freq -i "+prefix_path+"test/ecoli_2kb_region/result.txt");
                    Log.d(TAG, "f5c call-methylation ended " + result);
                    hideProgressWindow();
                } else {
                    Toast.makeText(MainActivity.this, "Please select a data directory first", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        findViewById(R.id.btn_event_alignment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(testDataPath)) {
                    Log.d(TAG, "f5c eventalign started");
//                    int result = init("f5c eventalign -b " + testDataPath + "/reads.sorted.bam -g " + testDataPath
//                            + "/draft.fa -r " +
//                            testDataPath + "/reads.fasta --secondary=yes --min-mapq=0 -B 2M > "
//                            + testDataPath + "/f5c_event_align.txt");
//                    ./f5c eventalign -b /media/sanoj/New\ Volume/chr22_meth_example/reads.sorted.bam
//                    -g /media/sanoj/New\ Volume/chr22_meth_example/humangenome.fa
//                    -r /media/sanoj/New\ Volume/chr22_meth_example/reads.fastq
//                    --secondary=yes --min-mapq=0 -t 8 -B 2M
//                    > /media/sanoj/New\ Volume/chr22_meth_example/f5c_event_align.txt

                    int result = init("f5c eventalign -b " + testDataPath + "/reads.sorted.bam -g " + testDataPath
                            + "/draft.fa -r " +
                            testDataPath + "/reads.fasta --secondary=yes --min-mapq=0 -B 2M > "
                            + testDataPath + "/5c_event_align.txt");
                    Log.d(TAG, "f5c eventalign ended " + result);
                    //hideProgressWindow();
                } else {
                    Toast.makeText(MainActivity.this, "Please select a data directory first", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        findViewById(R.id.btn_minimap2_index).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!TextUtils.isEmpty(testDataPath)) {
                    Log.d(TAG, "minimap2 index started");
                    int result = initminimap2(
                            "minimap2 -I 8 -d " + testDataPath + "/ref.mmi " + testDataPath + "/humangenome.fa");
                    Log.d(TAG, "minimap2 index ended " + result);
                    //hideProgressWindow();
                } else {
                    Toast.makeText(MainActivity.this, "Please select a data directory first", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        findViewById(R.id.btn_minimap2_alignment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!TextUtils.isEmpty(testDataPath)) {
                    Log.d(TAG, "minimap2 alignment started");
                    int result = initminimap2(
                            "minimap2 -I 8 -a " + testDataPath + "/ref.mmi " + testDataPath
                                    + "/reads.fastq");
                    Log.d(TAG, "minimap2 alignment ended " + result);
                    //hideProgressWindow();
                } else {
                    Toast.makeText(MainActivity.this, "Please select a data directory first", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

    }

    /////////////////////////////
    // Permission functions
    /////////////////////////////

    @Override
    public void onStart() {
        permissionUtils.check_permission(permissions,
                "The app needs storage permission for reading images and camera permission to take photos", 1);
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
        permissionUtils.check_permission(permissions,
                "The app needs storage permission for reading images and camera permission to take photos", 1);
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
        permissionUtils.check_permission(permissions,
                "The app needs storage permission for reading images and camera permission to take photos", 1);
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION", "DENIED");
        permissionUtils.check_permission(permissions,
                "The app needs storage permission for reading images and camera permission to take photos", 1);
    }

    // Callback functions
    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION", "GRANTED");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    private void hideProgressWindow() {
        progressDialog.dismiss();
    }

    private void openFileManager() {

        DialogProperties properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.DIR_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;

        FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);
        dialog.setTitle("Select a Folder");

        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of the paths of files selected by the Application User.
                testDataPath = files[0];
                editTextTestDataPath.setText(files[0]);
            }
        });

        dialog.show();
    }

    private void showProgressWindow() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Running...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

}
