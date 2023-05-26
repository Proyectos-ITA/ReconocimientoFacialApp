package edu.ita.facerecognition.data;

import android.graphics.Bitmap;
import org.json.JSONObject;

import edu.ita.facerecognition.util.Utils;

@SuppressWarnings("WeakerAccess")
public class EnrollRequest {
    public String numeroDeControl;
    public String nombre;
    public String apellidoPaterno;
    public String apellidoMaterno;
    public Bitmap imagen;

    public EnrollRequest() {
        this.numeroDeControl = null;
        this.nombre = null;
        this.apellidoPaterno = null;
        this.apellidoMaterno = null;
        this.imagen = null;
    }

    public JSONObject toJSON() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Control", numeroDeControl);
        jsonObject.put("Nombre(s)", nombre);
        jsonObject.put("Apellido Paterno", apellidoPaterno);
        jsonObject.put("Apellido Materno", apellidoMaterno);
        jsonObject.put("Imagen", Utils.encodeBitmap(imagen));
        return jsonObject;
    }
}
