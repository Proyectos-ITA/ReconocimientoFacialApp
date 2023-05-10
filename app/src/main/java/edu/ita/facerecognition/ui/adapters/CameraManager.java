package edu.ita.facerecognition.ui.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.fragment.app.FragmentActivity;

import edu.ita.facerecognition.ui.activities.CameraActivity;

@SuppressWarnings("SameReturnValue")
public class CameraManager {
    public interface OnCameraManagerListener {
        void onCameraManagerCancel();
        void onCameraManagerError(String error);
        void onCameraManagerCapture(Bitmap bitmap);
    }

    private OnCameraManagerListener mListener = null;
    private static final CameraManager mInstance = new CameraManager();

    private CameraManager() { }

    private static CameraManager getInstance() {
        return mInstance;
    }

    private void setInnerListener(OnCameraManagerListener listener) {
        mListener = listener;
    }

    private OnCameraManagerListener getInnerListener() {
        return mListener;
    }

    public static void showCamera(FragmentActivity activity, OnCameraManagerListener listener) {
        CameraManager.getInstance().setInnerListener(listener);
        Intent intent = new Intent(activity, CameraActivity.class);
        activity.startActivity(intent);
    }

    public static OnCameraManagerListener getListener() {
        return CameraManager.getInstance().getInnerListener();
    }
}
