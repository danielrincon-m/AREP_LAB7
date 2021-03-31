package edu.eci.arep.temp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.Request;
import spark.Response;

public class Converter {

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

    private static void printRequest(Request req) {
        System.out.println("\n\n---------------------------------------------------");
        System.out.println("---------------------------------------------------\n");
        String reqMethod = req.requestMethod();
        System.out.println(reqMethod + " desde la IP: " + req.ip());
        System.out.println(reqMethod + " con URL: " + req.url());
    }
}

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
