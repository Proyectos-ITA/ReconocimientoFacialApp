package edu.ita.facerecognition.ui.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import edu.ita.facerecognition.R;
import edu.ita.facerecognition.domain.FaceRecognition;
import edu.ita.facerecognition.domain.RecognizeRequest;
import edu.ita.facerecognition.domain.RecognizeResponse;
import edu.ita.facerecognition.ui.adapters.CameraManager;
import edu.ita.facerecognition.ui.fragments.CommonFragment;
import edu.ita.facerecognition.ui.fragments.EnrollFragment;
import edu.ita.facerecognition.ui.fragments.MainFragment;
import edu.ita.facerecognition.ui.fragments.RecognizeFragment;
import edu.ita.facerecognition.ui.fragments.SettingsFragment;
import edu.ita.facerecognition.util.Enums;
import edu.ita.facerecognition.util.Utils;

public class MainActivity extends CommonActivity implements MainFragment.OnMainFragmentListener, EnrollFragment.OnEnrollFragmentListener, RecognizeFragment.OnRecognizeFragmentListener, SettingsFragment.OnSettingsFragmentListener {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showMainFragment();
    }

    @Override
    public void onMainFragmentEnroll() {
        showEnrollFragment();
    }

    @Override
    public void onMainFragmentRecognize() {
        onRecognizeRequest();
    }

    @Override
    public void onMainFragmentSettings() {
        showSettingsFragment();
    }

    @Override
    public void onEnrollFragmentClose() {
        showMainFragment();
    }

    @Override
    public void onRecognizeFragmentClose() {
        showMainFragment();
    }

    @Override
    public void onSettingsFragmentClose() {
        showMainFragment();
    }

    @Override
    public void onBackPressed() {
        if (tellFragments()) {
            super.onBackPressed();
        }
    }

    private boolean tellFragments() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() == 0) {
            return true;
        }

        for (Fragment f : fragments){
            if (f instanceof CommonFragment) {
                ((CommonFragment) f).onBackPressed();
            }
        }
        return false;
    }

    private void showMainFragment() {
        showFragment(MainFragment.newInstance());
    }

    private void showEnrollFragment() {
        showFragment(EnrollFragment.newInstance());
    }

    private void showRecognizeFragment(RecognizeResponse response) {
        RecognizeFragment.setResponse(response);
        showFragment(RecognizeFragment.newInstance());
    }

    private void showSettingsFragment() {
        showFragment(SettingsFragment.newInstance());
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment);
        transaction.commit();
    }

    private void onRecognizeRequest() {
        CameraManager.showCamera(this, new CameraManager.OnCameraManagerListener() {
            @Override
            public void onCameraManagerCancel() {
                Log.w(TAG, "onCameraManagerCancel");
            }

            @Override
            public void onCameraManagerError(String error) {
                Utils.showAlert(MainActivity.this, getString(R.string.common_title_error_camera), error);
            }

            @Override
            public void onCameraManagerCapture(Bitmap bitmap) {
                RecognizeRequest request = new RecognizeRequest(bitmap);
                showProgressDialog(getString(R.string.main_activity_title_recognize), getString(R.string.common_message_wait));
                FaceRecognition.recognize(MainActivity.this, request, response -> {
                    hideProgressDialog();
                    onRecognizeResponse(response);
                });
            }
        });
    }

    private void onRecognizeResponse(RecognizeResponse response) {
        if (response.codigo == Enums.FR_OK) {
            showRecognizeFragment(response);
        }
        else {
            String message = response.mensaje;
            if (response.codigo == Enums.FR_USER_NOT_FOUND) {
                message += ("\nScore: " + response.marcador);
            }
            Utils.showAlert(this, getString(R.string.common_title_error), message);
        }
    }
}
