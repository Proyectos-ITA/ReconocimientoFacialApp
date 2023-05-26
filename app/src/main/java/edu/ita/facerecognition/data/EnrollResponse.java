package edu.ita.facerecognition.data;

import org.json.JSONObject;
import edu.ita.facerecognition.util.Enums;

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class EnrollResponse {
    public int codigo;
    public String mensaje;

    public EnrollResponse() {
        this.codigo = Enums.FR_UNEXPECTED_ERROR;
        this.mensaje = null;
    }

    public EnrollResponse(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public static EnrollResponse fromJSON(JSONObject json) throws Exception {
        EnrollResponse response = new EnrollResponse();
        response.codigo = json.getInt("Codigo");
        response.mensaje = json.getString("Mensaje");
        return response;
    }
}
