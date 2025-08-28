package Demo;

import java.util.Scanner;
import java.net.http.*;
import java.net.URI;
import org.json.JSONObject;  // Ensure org.json jar is added to your project

public class CurrencyConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Welcome to Currency Converter!");

            // Currency codes input
            System.out.print("Enter base currency code (e.g., USD, INR): ");
            String baseCurrency = scanner.next().toUpperCase();

            System.out.print("Enter target currency code (e.g., EUR, GBP): ");
            String targetCurrency = scanner.next().toUpperCase();

            // Amount input
            System.out.print("Enter amount to convert: ");
            double amount = scanner.nextDouble();

            // Construct API URL
            String apiUrl = "https://api.exchangerate.host/convert?from=" + baseCurrency + "&to=" + targetCurrency;

            // HTTP Request to API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON response
            JSONObject jsonObject = new JSONObject(response.body());

            if (jsonObject.has("result") && !jsonObject.isNull("result")) {
                double rate = jsonObject.getDouble("result");
                double convertedAmount = amount * rate;

                // Display result
                System.out.printf("\n%.2f %s = %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);
            } else {
                System.out.println("Error: Unable to fetch exchange rate. Please check currency codes.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
