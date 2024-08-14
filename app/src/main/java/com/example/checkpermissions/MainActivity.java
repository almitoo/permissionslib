package com.example.checkpermissions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.permissionslib.PermissionUtils;
import com.example.permissionslib.PermissionsManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PermissionsManager.PermissionChangeListener,RecyclerViewInterface {
    ArrayList<PermissionsModel>PermissionsModelList=new ArrayList<>();
    private static final int PERMISSION_REQUEST_CODE = 1;
    private Adapter adapter;
    int[]PermissionsModelImage={R.drawable.internet,R.drawable.call_phone,
            R.drawable.vibration,R.drawable.record_audio,R.drawable.location,
            R.drawable.fine_location_24,R.drawable.sms,R.drawable.contact_phone,
            R.drawable.camera,R.drawable.read_calendar,R.drawable.body_sensor,R.drawable.bluetooth};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView=findViewById(R.id.mRecyclerView);
        setUpPermissionsModel();
         adapter = new Adapter(this, PermissionsModelList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PermissionsManager.getInstance().registerListener(this);

        requestAllPermissions();


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PermissionsManager.getInstance().unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();

        updatePermissionsStatus();
    }

    private void setUpPermissionsModel() {

        String[] PermissionsModelName = getResources().getStringArray(R.array.app_permissions_txt);
        for (int i = 0; i < PermissionsModelName.length; i++) {
            PermissionsModelList.add(new PermissionsModel(PermissionsModelName[i], PermissionsModelImage[i]));
        }
    }
    private void requestAllPermissions() {
        String[] permissions = PermissionsManager.getInstance().getDeclaredPermissions(this);

        if (!PermissionsManager.getInstance().hasPermissions(this, permissions)) {
            PermissionsManager.getInstance().requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }


    private void updatePermissionsStatus() {
            for (PermissionsModel model : PermissionsModelList) {
                if(PermissionsManager.getInstance().isPermissionGranted(this, "android.permission."+model.getPermissionName())) {
                    model.setGranted(true);
                } else {
                    model.setGranted(false);
                }
            }
            adapter.notifyDataSetChanged();
        }

        public void onRequestPermissionsResult(int requestCode,  @NonNull String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);


            updatePermissionsStatus();
            PermissionsManager.getInstance().notifyPermissionChanged();

        }
    public void onPermissionChanged() {
        updatePermissionsStatus();
    }

    @Override
    public void onItemClick(int position) {
        PermissionsModel model = PermissionsModelList.get(position);
        String permissionName = "android.permission." + model.getPermissionName();

        if (PermissionsManager.getInstance().isPermissionGranted(this, permissionName)) {
            // if granted
            showToast("Permission " + model.getPermissionName() + "  is granted");
        } else {

                // requestPermission
                showToast("Permission " + model.getPermissionName() + " is not granted");
                PermissionUtils.handlePermissionRequest(this, permissionName,PERMISSION_REQUEST_CODE);
            }

    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

