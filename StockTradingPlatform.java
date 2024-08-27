import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Class to represent a Stock
class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

// Class to represent a Stock Trading Platform
public class StockTradingPlatform {
    private Map<String, Stock> market;
    private Map<String, Integer> portfolio;
    private double cash;

    public StockTradingPlatform() {
        market = new HashMap<>();
        portfolio = new HashMap<>();
        cash = 10000.0;  // Starting cash

        // Initialize market with some stocks
        market.put("AAPL", new Stock("AAPL", 150.0));
        market.put("GOOGL", new Stock("GOOGL", 2800.0));
        market.put("MSFT", new Stock("MSFT", 300.0));
        market.put("TSLA", new Stock("TSLA", 700.0));
        market.put("AMZN", new Stock("AMZN", 3400.0));
    }

    public void displayMarket() {
        System.out.println("\n--- Market Data ---");
        for (Stock stock : market.values()) {
            System.out.println(stock.getSymbol() + ": $" + stock.getPrice());
        }
    }

    public void buyStock(String symbol, int quantity) {
        if (!market.containsKey(symbol)) {
            System.out.println("Stock symbol not found.");
            return;
        }

        Stock stock = market.get(symbol);
        double cost = stock.getPrice() * quantity;

        if (cost > cash) {
            System.out.println("Not enough cash to complete the transaction.");
        } else {
            cash -= cost;
            portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + quantity);
            System.out.println("Bought " + quantity + " shares of " + symbol + " for $" + cost);
        }
    }

    public void sellStock(String symbol, int quantity) {
        if (!portfolio.containsKey(symbol) || portfolio.get(symbol) < quantity) {
            System.out.println("Not enough shares to sell.");
            return;
        }

        Stock stock = market.get(symbol);
        double proceeds = stock.getPrice() * quantity;

        cash += proceeds;
        portfolio.put(symbol, portfolio.get(symbol) - quantity);

        if (portfolio.get(symbol) == 0) {
            portfolio.remove(symbol);
        }

        System.out.println("Sold " + quantity + " shares of " + symbol + " for $" + proceeds);
    }

    public void displayPortfolio() {
        System.out.println("\n--- Portfolio ---");
        System.out.println("Cash: $" + cash);
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            Stock stock = market.get(entry.getKey());
            int quantity = entry.getValue();
            double value = stock.getPrice() * quantity;
            System.out.println(entry.getKey() + ": " + quantity + " shares, Value: $" + value);
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the Simulated Stock Trading Platform!");
        
        while (true) {
            System.out.println("\nEnter a command (market, buy, sell, portfolio, exit): ");
            command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "market":
                    displayMarket();
                    break;
                case "buy":
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.nextLine().toUpperCase();
                    System.out.print("Enter quantity to buy: ");
                    int buyQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    buyStock(buySymbol, buyQuantity);
                    break;
                case "sell":
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.nextLine().toUpperCase();
                    System.out.print("Enter quantity to sell: ");
                    int sellQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    sellStock(sellSymbol, sellQuantity);
                    break;
                case "portfolio":
                    displayPortfolio();
                    break;
                case "exit":
                    System.out.println("Exiting the platform. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        StockTradingPlatform platform = new StockTradingPlatform();
        platform.run();
    }
}
