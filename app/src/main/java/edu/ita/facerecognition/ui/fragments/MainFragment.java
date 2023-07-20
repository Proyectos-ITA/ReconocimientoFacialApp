package edu.ita.facerecognition.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.ita.facerecognition.R;

public class MainFragment extends CommonFragment implements View.OnClickListener {
    public interface OnMainFragmentListener {
        void onMainFragmentEnroll();
        void onMainFragmentRecognize();
        void onMainFragmentSettings();
    }

    private OnMainFragmentListener mListener;

    @SuppressWarnings("WeakerAccess")
    public MainFragment() { }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.mainEnrollButton).setOnClickListener(this);
        view.findViewById(R.id.mainRecognizeButton).setOnClickListener(this);
        view.findViewById(R.id.mainSettingsButton).setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainFragmentListener) {
            mListener = (OnMainFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnMainFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (validateLastClick()) {
            if (v.getId() == R.id.mainEnrollButton) {
                mListener.onMainFragmentEnroll();
            } else if (v.getId() == R.id.mainRecognizeButton) {
                mListener.onMainFragmentRecognize();
            } else if (v.getId() == R.id.mainSettingsButton) {
                mListener.onMainFragmentSettings();
            }
        }
    }

}
