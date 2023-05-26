package edu.ita.facerecognition.domain;

@SuppressWarnings("WeakerAccess")
public class ServerAddress {
    public String direccionIP;
    public String puerto;

    public ServerAddress(String direccionIP, String puerto) {
        this.direccionIP = direccionIP;
        this.puerto = puerto;
    }
}
