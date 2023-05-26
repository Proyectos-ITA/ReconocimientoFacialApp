package edu.ita.facerecognition.domain;

import android.graphics.Bitmap;
import org.json.JSONObject;
import edu.ita.facerecognition.util.Enums;
import edu.ita.facerecognition.util.Utils;

@SuppressWarnings("WeakerAccess")
public class RecognizeResponse {
    public int codigo;
    public String mensaje;
    public float marcador;
    public String numeroDeControl;
    public String nombre;
    public String apellidoPaterno;
    public String apellidoMaterno;
    public Bitmap miniatura;

    public RecognizeResponse() {
        this.codigo = Enums.FR_UNEXPECTED_ERROR;
        this.mensaje = null;
        this.marcador = 0.f;
        this.numeroDeControl = null;
        this.nombre = null;
        this.apellidoPaterno = null;
        this.apellidoMaterno = null;
        this.miniatura = null;
    }

    public RecognizeResponse(int code, String message) {
        this.codigo = code;
        this.mensaje = message;
        this.marcador = 0.f;
        this.numeroDeControl = null;
        this.nombre = null;
        this.apellidoPaterno = null;
        this.apellidoMaterno = null;
        this.miniatura = null;
    }

    public static RecognizeResponse fromJSON(JSONObject json) throws Exception {
        RecognizeResponse response = new RecognizeResponse();
        response.codigo = json.getInt("Codigo");
        response.mensaje = json.getString("Mensaje");
        response.marcador = Float.parseFloat(json.getString("Marcador"));
        response.numeroDeControl = json.getString("Numero de Control");
        response.nombre = json.getString("Nombre(s)");
        response.apellidoPaterno = json.getString("Apellido Paterno");
        response.apellidoMaterno = json.getString("Apellido Materno");
        response.miniatura = Utils.decodeBitmap(json.getString("Miniatura"));
        return response;
    }
}
