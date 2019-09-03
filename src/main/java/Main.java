
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityDao entityDao = new EntityDao();
        InvoiceDao invoiceDao = new InvoiceDao();
        ContentLoader contentLoader = new ContentLoader();

        String toDo;
        do {
            System.out.println("Welcom to restaurnat.");
            System.out.println("1.  Dodaj rachunek");
            System.out.println("2.  Dodaj produkt do rachunku");
            System.out.println("3.  Ustaw rachunek jako opłacony");
            System.out.println("4.  Sprawdź kwote rachunku o podanym Id");
            System.out.println("5.  Listuj produkty z rachunku");
            System.out.println("6.  Listuj wszystkie rachunki");
            System.out.println("7.  Listuj wszystkie produkty");
            System.out.println("8.  Listuj rachunki nieopłacone");
            System.out.println("9.  Listuj rachunki z ostatniego tygodnia");
            System.out.println("10. Wypisz sume rachunków z dzisiaj");
            System.out.println("11. Wydaj fakture");
            System.out.println("0.  Wyjście...");

            toDo = scanner.nextLine();
            if (toDo.equalsIgnoreCase("1")) { //Dodaj rachunek
                Invoice invoice = contentLoader.createInvoice();
                entityDao.saveOrUpdate(invoice);

            } else if (toDo.equalsIgnoreCase("2")) { //Dodaj produkt do rachunku
                String nextProduct = "tak";
                Long id = contentLoader.getId();

                while (!nextProduct.equalsIgnoreCase("nie")) {
                    Product product = contentLoader.createProduct();

                    Optional<Invoice> optionalInvoice = entityDao.getById(Invoice.class, id);
                    if (optionalInvoice.isPresent()) {
                        product.setInvoice(optionalInvoice.get());
                        entityDao.saveOrUpdate(product);
                    }
                    System.out.println("Dodać kolejny produkt? tak/nie");
                    nextProduct = scanner.nextLine();
                }

            } else if (toDo.equalsIgnoreCase("3")) { //Ustaw rachunek jako opłacony
                Long id = contentLoader.getId();
                Optional<Invoice> optionalInvoice = entityDao.getById(Invoice.class, id);
                if (optionalInvoice.isPresent()) {
                    invoiceDao.setInvoiceAsPaid(optionalInvoice.get());
                    contentLoader.waitForUser();
                } else {
                    System.err.println("Nie znaleziono rachunku o podanym Id.");
                }

            } else if (toDo.equalsIgnoreCase("4")) { //Sprawdź kwote rachunku o podanym Id
                Long id = contentLoader.getId();
                Optional<Invoice> optionalInvoice = entityDao.getById(Invoice.class, id);
                if (optionalInvoice.isPresent()) {
                    invoiceDao.getInvoicePriceById(optionalInvoice.get());
                    contentLoader.waitForUser();
                } else {
                    System.err.println("Nie znaleziono rachunku o podanym Id.");
                }

            } else if (toDo.equalsIgnoreCase("5")) { //Listuj produkty z rachunku
                Long id = contentLoader.getId();
                Optional<Invoice> optionalInvoice = entityDao.getById(Invoice.class, id);
                if (optionalInvoice.isPresent()) {
                    invoiceDao.listProductFromInvoice(optionalInvoice.get());
                    contentLoader.waitForUser();
                } else {
                    System.err.println("Nie znaleziono rachunku o podanym Id.");
                }

            } else if (toDo.equalsIgnoreCase("6")) { //Listuj wszystkie rachunki
                List<Invoice> invoiceList = entityDao.getAll(Invoice.class);
                for (Invoice in : invoiceList) {
                    System.out.println(in);
                }
                contentLoader.waitForUser();
            } else if (toDo.equalsIgnoreCase("7")) { //Listuj wszystkie produkty
                entityDao.getAll(Product.class);
                contentLoader.waitForUser();

            } else if (toDo.equalsIgnoreCase("8")) { //Listuj rachunki nieopłacone
                List<Invoice> list = invoiceDao.listAllUnpaid();
                for (Invoice i : list) {
                    System.out.println(i);
                }
                contentLoader.waitForUser();

            } else if (toDo.equalsIgnoreCase("9")) { //Listuj rachunki z ostatniego tygodnia
                invoiceDao.getAllFromLastWeek();
                contentLoader.waitForUser();

            } else if (toDo.equalsIgnoreCase("10")) { //Wypisz sume rachunków z dzisiaj


            } else if (toDo.equalsIgnoreCase("11")) { //Wydaj fakture
                Long id = contentLoader.getId();
                Optional<Invoice> optionalInvoice = entityDao.getById(Invoice.class, id);
                if (optionalInvoice.isPresent()) {
                    invoiceDao.issueAnInvoice(optionalInvoice.get());
                }
            }
        } while (!toDo.equalsIgnoreCase("0"));
    }
}
