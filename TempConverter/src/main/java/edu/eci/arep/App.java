package edu.eci.arep;

import static spark.Spark.*;

import edu.eci.arep.temp.Converter;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        port(getPort());
        get("convert/farenheit/celsius", Converter::farenheit2Celsius);
    }

    public static void stopServer() {
        stop();
    }

    /**
     * Busca el pueto de funcionamiento en las variables de entorno del sistema, y si no lo
     * encuentra utiliza el puerto por defecto.
     * 
     * @return El puerto sobre el cuál correrá el servidor.
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; // returns default port if heroku-port isn't set
    }
}
