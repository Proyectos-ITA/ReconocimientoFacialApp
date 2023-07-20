package edu.ita.facerecognition.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

import edu.ita.facerecognition.R;
import edu.ita.facerecognition.data.EnrollRequest;
import edu.ita.facerecognition.data.EnrollResponse;
import edu.ita.facerecognition.domain.FaceRecognition;
import edu.ita.facerecognition.ui.adapters.CameraManager;
import edu.ita.facerecognition.util.Enums;
import edu.ita.facerecognition.util.Utils;

public class EnrollFragment extends CommonFragment implements View.OnClickListener {
    public interface OnEnrollFragmentListener {
        void onEnrollFragmentClose();
    }

    private static final String TAG = EnrollFragment.class.getName();

    private Bitmap mBitmap = null;
    private TextInputLayout mNombreTextInputLayout;
    private TextInputLayout mPaternoTextInputLayout;
    private TextInputLayout mMaternoTextInputLayout;
    private TextInputLayout mControlTextInputLayout;
    private ImageView mFotoImageView;
    private OnEnrollFragmentListener mListener;

    @SuppressWarnings("WeakerAccess")
    public EnrollFragment() {
    }

    public static EnrollFragment newInstance() {
        return new EnrollFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enroll, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.enrollOkButton).setOnClickListener(this);
        view.findViewById(R.id.enrollCancelButton).setOnClickListener(this);
        mNombreTextInputLayout = view.findViewById(R.id.enrollNombreTextInputLayout);
        mPaternoTextInputLayout = view.findViewById(R.id.enrollPaternoTextInputLayout);
        mMaternoTextInputLayout = view.findViewById(R.id.enrollMaternoTextInputLayout);
        mControlTextInputLayout = view.findViewById(R.id.enrollControlTextInputLayout);
        mFotoImageView = view.findViewById(R.id.enrollFotoImageView);
        mFotoImageView.setOnClickListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnEnrollFragmentListener) {
            mListener = (OnEnrollFragmentListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnEnrollFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onBackPressed() {
        onCloseFragment();
    }

    @SuppressLint("NonConstantResourceId")

    @Override
    public void onClick(View v) {
        if (!validateLastClick()) return;

        if (v.getId() == R.id.enrollOkButton) {
            onEnrollRequest();
        } else if (v.getId() == R.id.enrollCancelButton) {
            onCloseFragment();
        } else if (v.getId() == R.id.enrollFotoImageView) {
            onShowCamera();
        }
    }


    private void onCloseFragment() {
        mListener.onEnrollFragmentClose();
    }

    private void onShowCamera() {
        CameraManager.showCamera(getActivity(), new CameraManager.OnCameraManagerListener() {
            @Override
            public void onCameraManagerCancel() {
                Log.w(TAG, "onCameraManagerCancel");
            }

            @Override
            public void onCameraManagerError(String error) {
                Utils.showAlert(getActivity(), getString(R.string.common_title_error_camera), error);
            }

            @Override
            public void onCameraManagerCapture(Bitmap bitmap) {
                if (mBitmap != null) {
                    mBitmap.recycle();
                    mBitmap = null;
                }

                mBitmap = bitmap;
                mFotoImageView.setImageDrawable(new BitmapDrawable(getResources(), mBitmap));
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void onEnrollRequest() {
        if (mBitmap == null) {
            Utils.showAlert(getActivity(), getString(R.string.common_title_attention), getString(R.string.enroll_fragment_error_foto));
            return;
        }

        String firstName = mNombreTextInputLayout.getEditText().getText().toString();
        if (!Utils.validateText(firstName, mNombreTextInputLayout, getString(R.string.enroll_fragment_error_nombre))) {
            return;
        }

        String lastName = mPaternoTextInputLayout.getEditText().getText().toString();
        if (!Utils.validateText(lastName, mPaternoTextInputLayout, getString(R.string.enroll_fragment_error_apellido))) {
            return;
        }

        String secondLastName = mMaternoTextInputLayout.getEditText().getText().toString();
        if (!Utils.validateText(secondLastName, mMaternoTextInputLayout, getString(R.string.enroll_fragment_error_apellido))) {
            return;
        }

        String control = mControlTextInputLayout.getEditText().getText().toString();
        if (!Utils.validateText(control, mControlTextInputLayout, getString(R.string.enroll_fragment_error_control))) {
            return;
        }

        EnrollRequest request = new EnrollRequest();
        request.nombre = firstName;
        request.apellidoPaterno = lastName;
        request.apellidoMaterno = secondLastName;
        request.numeroDeControl = control;
        request.imagen = mBitmap;

        showProgressDialog(getString(R.string.enroll_fragment_title_enroll), getString(R.string.common_message_wait));
        FaceRecognition.enroll(getActivity(), request, response -> {
            hideProgressDialog();
            onEnrollResponse(response);
        });
    }

    private void onEnrollResponse(EnrollResponse response) {
        if (response.codigo == Enums.FR_OK) {
            Utils.showAlert(getActivity(), getString(R.string.common_title_attention), Enums.FR_SERVER_ENROLL_SUCCESS_KEY, (dialog, which) -> {
                dialog.dismiss();
                onCloseFragment();
            });
        } else {
            Utils.showAlert(getActivity(), getString(R.string.common_title_error), response.mensaje);
        }
    }
}
