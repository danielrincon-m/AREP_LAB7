package edu.eci.arep.temp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.Request;
import spark.Response;

/**
 * Clase que nos permite realizar conversiones de temperatura, por ahora solo están disponibles las
 * conversiones de farenheit a celsius y viceversa
 */
public class Converter {

    /**
     * Convierte grados celsius a farenheit, el valor se obtiene por medio del parámetro "value"
     * obtenido de la url de la petición get realizada
     * 
     * @param req La petición
     * @param res La respuesta
     * @return Una cadena con la respuesta en formato JSON
     */
    public static String celsius2Farenheit(Request req, Response res) {
        printRequest(req);
        double celsiusDegrees, farenheitDegrees;
        try {
            celsiusDegrees = Double.parseDouble(req.queryParams("value"));
        } catch (NullPointerException e) {
            return "No se ha ingresado ningún parámetro";
        } catch (NumberFormatException e) {
            return "La entrada debe ser numérica";
        }
        farenheitDegrees = celsiusDegrees * (9.0 / 5.0) + 32;

        res.type("application/json");
        ResponseData responseObj = new ResponseData(celsiusDegrees, farenheitDegrees);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(responseObj);
    }

    /**
     * Convierte grados farenheit a celsius, el valor se obtiene por medio del parámetro "value"
     * obtenido de la url de la petición get realizada
     * 
     * @param req La petición
     * @param res La respuesta
     * @return Una cadena con la respuesta en formato JSON
     */
    public static String farenheit2Celsius(Request req, Response res) {
        printRequest(req);
        double celsiusDegrees, farenheitDegrees;
        try {
            farenheitDegrees = Double.parseDouble(req.queryParams("value"));
        } catch (NullPointerException e) {
            return "No se ha ingresado ningún parámetro";
        } catch (NumberFormatException e) {
            return "La entrada debe ser numérica";
        }
        celsiusDegrees = (farenheitDegrees - 32) * (5.0 / 9.0);

        res.type("application/json");
        ResponseData responseObj = new ResponseData(celsiusDegrees, farenheitDegrees);
        Gson gson = new Gson();// GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(responseObj);
    }

    /**
     * Imprime información de la solicitud realizada, es una herramienta de DEBUG
     * 
     * @param req La solicitud realizada
     */
    private static void printRequest(Request req) {
        System.out.println("\n\n---------------------------------------------------");
        System.out.println("---------------------------------------------------\n");
        String reqMethod = req.requestMethod();
        System.out.println(reqMethod + " desde la IP: " + req.ip());
        System.out.println(reqMethod + " con URL: " + req.url());
    }
}


/**
 * Clase de datos para organizar la respuesta que se creará en formato JSON
 */
class ResponseData {
    private double farenheitDegrees;
    private double celsiusDegrees;

    public ResponseData(double celsiusDegrees, double farenheitDegrees) {
        this.celsiusDegrees = celsiusDegrees;
        this.farenheitDegrees = farenheitDegrees;
    }

    public double getCelsiusDegrees() {
        return celsiusDegrees;
    }

    public void setCelsiusDegrees(double celsiusDegrees) {
        this.celsiusDegrees = celsiusDegrees;
    }

    public double getFarenheitDegrees() {
        return farenheitDegrees;
    }

    public void setFarenheitDegrees(double farenheitDegrees) {
        this.farenheitDegrees = farenheitDegrees;
    }
}
