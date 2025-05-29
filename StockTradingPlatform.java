import java.util.*;

class Stock {
    String symbol;
    double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

class Holding {
    String symbol;
    int quantity;
    double averagePrice;

    public Holding(String symbol, int quantity, double averagePrice) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
    }
}

public class StockTradingPlatform {

    static Map<String, Stock> market = new HashMap<>();
    static Map<String, Holding> portfolio = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);
    static double cash = 10000.0;

    public static void main(String[] args) {
        initializeMarket();

        while (true) {
            System.out.println("\n=== Stock Trading Platform ===");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> viewMarket();
                case 2 -> buyStock();
                case 3 -> sellStock();
                case 4 -> viewPortfolio();
                case 5 -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void initializeMarket() {
        market.put("AAPL", new Stock("AAPL", 150.00));
        market.put("GOOG", new Stock("GOOG", 2800.00));
        market.put("TSLA", new Stock("TSLA", 700.00));
        market.put("AMZN", new Stock("AMZN", 3300.00));
    }

    static void viewMarket() {
        System.out.println("\n--- Market Data ---");
        for (Stock stock : market.values()) {
            stock.price = stock.price * (0.95 + Math.random() * 0.1); // simulate price change
            System.out.printf("%s: $%.2f%n", stock.symbol, stock.price);
        }
    }

    static void buyStock() {
        System.out.print("Enter stock symbol to buy: ");
        String symbol = scanner.next().toUpperCase();
        if (!market.containsKey(symbol)) {
            System.out.println("Stock not found.");
            return;
        }

        System.out.print("Enter quantity to buy: ");
        int quantity = scanner.nextInt();
        double price = market.get(symbol).price;
        double cost = quantity * price;

        if (cash < cost) {
            System.out.println("Not enough cash.");
            return;
        }

        Holding holding = portfolio.getOrDefault(symbol, new Holding(symbol, 0, 0));
        double totalCost = holding.averagePrice * holding.quantity + cost;
        int totalQuantity = holding.quantity + quantity;

        holding.quantity = totalQuantity;
        holding.averagePrice = totalCost / totalQuantity;
        portfolio.put(symbol, holding);
        cash -= cost;

        System.out.printf("Bought %d shares of %s at $%.2f each. Remaining cash: $%.2f%n", quantity, symbol, price, cash);
    }

    static void sellStock() {
        System.out.print("Enter stock symbol to sell: ");
        String symbol = scanner.next().toUpperCase();
        if (!portfolio.containsKey(symbol)) {
            System.out.println("You don't own this stock.");
            return;
        }

        Holding holding = portfolio.get(symbol);
        System.out.printf("You own %d shares of %s%n", holding.quantity, symbol);
        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();

        if (quantity > holding.quantity) {
            System.out.println("Not enough shares.");
            return;
        }

        double price = market.get(symbol).price;
        double revenue = quantity * price;
        holding.quantity -= quantity;

        if (holding.quantity == 0) {
            portfolio.remove(symbol);
        }

        cash += revenue;
        System.out.printf("Sold %d shares of %s at $%.2f each. New cash balance: $%.2f%n", quantity, symbol, price, cash);
    }

    static void viewPortfolio() {
        System.out.println("\n--- Portfolio ---");
        if (portfolio.isEmpty()) {
            System.out.println("Your portfolio is empty.");
        } else {
            double totalValue = cash;
            for (Holding h : portfolio.values()) {
                double currentPrice = market.get(h.symbol).price;
                double value = h.quantity * currentPrice;
                totalValue += value;
                System.out.printf("%s: %d shares, Avg Price: $%.2f, Current Price: $%.2f, Value: $%.2f%n",
                        h.symbol, h.quantity, h.averagePrice, currentPrice, value);
            }
            System.out.printf("Cash: $%.2f%n", cash);
            System.out.printf("Total Portfolio Value: $%.2f%n", totalValue);
        }
    }
}
