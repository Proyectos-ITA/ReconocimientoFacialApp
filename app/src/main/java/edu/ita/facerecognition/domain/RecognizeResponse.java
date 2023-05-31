package edu.ita.facerecognition.domain;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

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

    public RecognizeResponse(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.marcador = 0.f;
        this.numeroDeControl = null;
        this.nombre = null;
        this.apellidoPaterno = null;
        this.apellidoMaterno = null;
        this.miniatura = null;
    }

    @NonNull
    public static RecognizeResponse fromJSON(@NonNull JSONObject json) throws Exception {
        RecognizeResponse respuesta = new RecognizeResponse();
        respuesta.codigo = json.getInt("Codigo");
        respuesta.mensaje = json.getString("Mensaje");
        respuesta.marcador = Float.parseFloat(json.getString("Marcador"));
        respuesta.numeroDeControl = json.getString("Numero de Control");
        respuesta.nombre = json.getString("Nombre(s)");
        respuesta.apellidoPaterno = json.getString("Apellido Paterno");
        respuesta.apellidoMaterno = json.getString("Apellido Materno");
        respuesta.miniatura = Utils.decodeBitmap(json.getString("Miniatura"));
        return respuesta;
    }
}
