package edu.ita.facerecognition.domain;

import android.content.Context;
import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import edu.ita.facerecognition.data.EnrollRequest;
import edu.ita.facerecognition.data.EnrollResponse;
import edu.ita.facerecognition.util.Enums;
import edu.ita.facerecognition.util.Utils;

@SuppressWarnings("WeakerAccess")
public class FaceRecognition {
    public interface OnEnrollResponseListener {
        void onResponse(EnrollResponse response);
    }

    public interface OnRecognizeResponseListener {
        void onResponse(RecognizeResponse response);
    }

    private static final String TAG = FaceRecognition.class.getName();

    public static void enroll(Context context, EnrollRequest request, final OnEnrollResponseListener listener) {
        JSONObject jsonObject = null;
        try {
            jsonObject = request.toJSON();
        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
            listener.onResponse(new EnrollResponse(Enums.FR_UNEXPECTED_ERROR, ex.toString()));
            return;
        }

        String url = getUrl(context, Enums.FR_SERVER_ENROLL_METHOD_KEY);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listener.onResponse(EnrollResponse.fromJSON(response));
                } catch (Exception ex) {
                    Log.e(TAG, Log.getStackTraceString(ex));
                    listener.onResponse(new EnrollResponse(Enums.FR_UNEXPECTED_ERROR, ex.toString()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, Log.getStackTraceString(error));
                listener.onResponse(new EnrollResponse(Enums.FR_UNEXPECTED_ERROR, error.toString()));
            }
        });

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(Enums.FR_SERVER_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static void recognize(Context context, RecognizeRequest request, final OnRecognizeResponseListener listener) {
        JSONObject jsonObject = null;
        try {
            jsonObject = request.toJSON();
        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
            listener.onResponse(new RecognizeResponse(Enums.FR_UNEXPECTED_ERROR, ex.toString()));
            return;
        }

        String url = getUrl(context, Enums.FR_SERVER_RECOGNIZE_METHOD_KEY);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listener.onResponse(RecognizeResponse.fromJSON(response));
                } catch (Exception ex) {
                    Log.e(TAG, Log.getStackTraceString(ex));
                    listener.onResponse(new RecognizeResponse(Enums.FR_UNEXPECTED_ERROR, ex.toString()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, Log.getStackTraceString(error));
                listener.onResponse(new RecognizeResponse(Enums.FR_UNEXPECTED_ERROR, error.toString()));
            }
        });

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(Enums.FR_SERVER_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    private static String getUrl(Context context, String method) {
        ServerAddress address = Utils.getServerAddress(context);
        return "http://" + address.direccionIP + ":" + address.puerto + Enums.FR_SERVER_URL_SUFFIX_KEY + method;
    }
}
