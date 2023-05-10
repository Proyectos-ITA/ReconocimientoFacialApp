package edu.ita.facerecognition.domain;

import android.graphics.Bitmap;
import org.json.JSONObject;
import edu.ita.facerecognition.util.Utils;

@SuppressWarnings("WeakerAccess")
public class RecognizeRequest {
    public Bitmap image;

    public RecognizeRequest(Bitmap image) {
        this.image = image;
    }

    public JSONObject toJSON() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Image", Utils.encodeBitmap(image));
        return jsonObject;
    }
}
