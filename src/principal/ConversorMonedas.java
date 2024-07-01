package principal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import modelos.ConvRateDouble;
import modelos.ConversionRate;


public class ConversorMonedas {
    public static void main(String[] args) throws IOException, InterruptedException {
        int opcion = 0;
        String fromCurrency = "";
        String toCurrency = "";
        Double cotizacion = (double) 0;
        Double monto = (double) 0;
        ConvRateDouble convRateDouble;
        while (opcion != 7) {
            mostrarMensaje();
            Scanner teclado = new Scanner(System.in);
            opcion = teclado.nextInt();
            teclado.nextLine();
            if (opcion != 7){
                System.out.println("Ingresa el monto a convertir: ");
                monto = teclado.nextDouble();
                teclado.nextLine();
            }
            switch (opcion) {
                case 1:
                    fromCurrency = "USD";
                    toCurrency = "ARS";
                    convRateDouble = obtenerConvRateDoble(fromCurrency);
                    cotizacion = convRateDouble.getArs();
                    convertirMonto(fromCurrency, toCurrency, cotizacion, monto);
                    break;
                case 2:
                    fromCurrency = "ARS";
                    toCurrency = "USD";
                    convRateDouble = obtenerConvRateDoble(fromCurrency);
                    cotizacion = convRateDouble.getUsd();
                    convertirMonto(fromCurrency, toCurrency, cotizacion, monto);
                    break;
                case 3:
                    fromCurrency = "USD";
                    toCurrency = "BRL";
                    convRateDouble = obtenerConvRateDoble(fromCurrency);
                    cotizacion = convRateDouble.getBrl();
                    convertirMonto(fromCurrency, toCurrency, cotizacion, monto);
                    break;
                case 4:
                    fromCurrency = "BRL";
                    toCurrency = "USD";
                    convRateDouble = obtenerConvRateDoble(fromCurrency);
                    cotizacion = convRateDouble.getUsd();
                    convertirMonto(fromCurrency, toCurrency, cotizacion, monto);
                    break;
                case 5:
                    fromCurrency = "USD";
                    toCurrency = "MXN";
                    convRateDouble = obtenerConvRateDoble(fromCurrency);
                    cotizacion = convRateDouble.getMxn();
                    convertirMonto(fromCurrency, toCurrency, cotizacion, monto);
                    break;
                case 6:
                    fromCurrency = "MXN";
                    toCurrency = "USD";
                    convRateDouble = obtenerConvRateDoble(fromCurrency);
                    cotizacion = convRateDouble.getUsd();
                    convertirMonto(fromCurrency, toCurrency, cotizacion, monto);
                    break;
                case 7:
                    System.out.println("Saliendo de la aplicación...");
                    break;
                default:
                    System.out.println("La opción elegida es incorrecta.");
            }
        }
    }

    static ConvRateDouble obtenerConvRateDoble(String fromCurrency) throws IOException, InterruptedException {
        String baseUrlStr = "https://v6.exchangerate-api.com/v6/056812f4a11b33a24f0fb51a/latest/";
        String urlStr = baseUrlStr + fromCurrency;
        HttpClient client = HttpClient.newBuilder()
                .version(Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlStr))
                .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
//        System.out.println(response.body());

        JsonElement root = JsonParser.parseString(response.body());
        JsonObject jsonobj = root.getAsJsonObject();

        JsonElement req_cr = jsonobj.get("conversion_rates");
//        System.out.println(req_cr);

        Gson gson = new Gson();
        ConversionRate cr = gson.fromJson(req_cr, ConversionRate.class);
//        System.out.println(cr);

        ConvRateDouble convRateDouble = new ConvRateDouble(cr);
//        System.out.println(convRateDouble);

        return convRateDouble;
    }
    static void mostrarMensaje(){
        String mensaje = """
                ----------------------
                Aplicación CLI Conversor de Moneda
                1) Dolar -> Peso Argentino
                2) Peso Argentino -> Dolar
                3) Dolar -> Real brasileño
                4) Real brasileño -> Dolar
                5) Dolar -> Peso mejicano
                6) Peso mejicano -> Dolar
                7) Salir
                Elija una opción válida:
                """;
        System.out.println(mensaje);
    }
    static void convertirMonto(String fromCurrency, String toCurrency,  Double cotizacion, Double monto){
        Double montoConvertido = monto * cotizacion;
        System.out.println("Convirtiendo " + fromCurrency + " "
                + monto.toString() + " a " + toCurrency);
        System.out.println("Cotización actual: " + cotizacion.toString());
        System.out.println("El monto resultante en " + toCurrency + " es:" + montoConvertido.toString());

    }
}
