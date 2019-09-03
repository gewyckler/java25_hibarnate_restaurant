import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ContentLoader {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Product createProduct() {
        Product product = new Product();
        product.setId(null);
        product.setName(getProductName());
        product.setPrice(getProductPrice());
        product.setQuantity(getProductQuantity());
        return product;
    }

    public int getProductQuantity() {
        System.out.println("Podaj ilość produktu");
        return Integer.parseInt(scanner.nextLine());
    }

    public String getProductName() {
        System.out.println("Podaj nazwe produktu");
        return scanner.nextLine();
    }

    public double getProductPrice() {
        System.out.println("Podaj cene produtu");
        return Double.parseDouble(scanner.nextLine());
    }

    public String getDateAdding() {
        System.out.println("Podaj date dodania rachunku. (dd-MM-yyyy)");
        return scanner.nextLine();
    }

    public String getClientName() {
        System.out.println("Podaj imie i nazwisko klienta");
        return scanner.nextLine();
    }

    public Invoice createInvoice() {
        Invoice invoice = new Invoice();
        invoice.setId(null);

        String data = getDateAdding();
        LocalDate localDate = LocalDate.parse(data, dateTimeFormatter);

        invoice.setDateAdded(localDate);
        invoice.setClientName(getClientName());
        return invoice;
    }

    public Long getId() {
        System.out.println("Podaj Id faktury:");
        return Long.valueOf(scanner.nextLine());
    }

    public String waitForUser() {
        System.out.println("Click 'ENTER' to continue...");
        return scanner.nextLine();
    }
}
