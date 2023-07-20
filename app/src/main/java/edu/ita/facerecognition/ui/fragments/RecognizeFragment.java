package edu.ita.facerecognition.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

import edu.ita.facerecognition.R;
import edu.ita.facerecognition.domain.RecognizeResponse;
import edu.ita.facerecognition.util.Utils;

public class RecognizeFragment extends CommonFragment {
    public interface OnRecognizeFragmentListener {
        void onRecognizeFragmentClose();
    }

    private static WeakReference<RecognizeResponse> mResponse;

    private OnRecognizeFragmentListener mListener;

    @SuppressWarnings("WeakerAccess")
    public RecognizeFragment() { }

    public static RecognizeFragment newInstance() {
        return new RecognizeFragment();
    }

    public static void setResponse(RecognizeResponse response) {
        mResponse = response != null ? new WeakReference<>(response) : null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recognize, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.recognizeOkButton).setOnClickListener(v -> onCloseFragment());

        RecognizeResponse response = mResponse == null ? null : mResponse.get();
        if (response != null) {
            ((TextView) view.findViewById(R.id.recognizeNombreTextView)).setText(getString(R.string.recognize_fragment_prefix_nomre) + response.nombre);
            ((TextView) view.findViewById(R.id.recognizePaternoTextView)).setText(getString(R.string.recognize_fragment_prefix_paterno) + response.apellidoPaterno);
            ((TextView) view.findViewById(R.id.recognizeMaternoTextView)).setText(getString(R.string.recognize_fragment_prefix_materno) + response.apellidoMaterno);
            ((TextView) view.findViewById(R.id.recognizeControlTextView)).setText(getString(R.string.recognize_fragment_prefix_control) + response.numeroDeControl);
            ((TextView) view.findViewById(R.id.recognizeScoreTextView)).setText(getString(R.string.recognize_fragment_prefix_score) + response.marcador);

            if (response.miniatura != null) {
                ((ImageView) view.findViewById(R.id.recognizeFotoImageView)).setImageBitmap(response.miniatura);
            }
            else {
                Utils.showAlert(getActivity(), getString(R.string.common_title_attention), getString(R.string.common_message_error_bitmap_decode));
            }
        }
        else {
            Utils.showAlert(getActivity(), getString(R.string.common_title_error), getString(R.string.recognize_fragment_message_error_response), (dialog, which) -> {
                dialog.dismiss();
                onCloseFragment();
            });
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnRecognizeFragmentListener) {
            mListener = (OnRecognizeFragmentListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnRecognizeFragmentListener");
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

    private void onCloseFragment() {
        mListener.onRecognizeFragmentClose();
    }
}
