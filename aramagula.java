import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Login {
    private String cashierName;
    private String branchName;
    InputStreamReader r = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(r);
    private void startSystem() throws IOException{



            try {

                System.out.println("Cashier Name: ");
                cashierName = br.readLine();

                System.out.println("Branch: ");

                branchName = br.readLine();

            while (true) {
                boolean exit = enterMenu();
                if(exit)
                    break;
            }
            }catch (IOException ioe){
                ioe.printStackTrace();
                System.exit(1);
            }finally {
                br.close();
                r.close();
            }


}

    private boolean enterMenu() throws IOException{

        String choice = null;
        System.out.println("Super Saver Supermarket");
        System.out.println("1. New Bill");
        System.out.println("2. Pending BIlls");
        System.out.println("3. Exit");
        while (true) {
            System.out.println("Choice: ");



            try {
                choice = br.readLine().replaceAll(" ", "");
            }catch(IOException e){
                e.printStackTrace();
                System.exit(1);
            }

            if(choice.equals("3"))
                return true;
            else if(choice.equals("1")){
                System.out.println("Enter Customer Name: ");
                String customerName = new Scanner(System.in).nextLine();

                if(customerName.isBlank()){
                    customerName = "N/A";
                }else{

                }

                POS biller = new POS(cashierName, branchName, customerName);
                biller.startBillingProcess();

                break;
            }else if(choice.equals("3")){
                break;
            }else
                System.out.println("Invalid Input");

        }

        return false;
    }

    public static void main(String[] args) {
        Groceries.initMap();

        try {
            new Login().startSystem();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

class Bill implements Serializable{
    private String cashier, branch, customer;
    private double totalDiscount = 0.0, totalPrice = 0.0;
    private final ArrayList<GroceryItem> items = new ArrayList<>();

    public Bill(String cashier, String branch, String customer){
        this.customer = customer;
        this.cashier = cashier;
        this.branch = branch;
    }

    public void addToList(GroceryItem item){
        items.add(item);
    }

}


class POS{
    private String cashier, branch, customer;
    private ArrayList<GroceryItem> items = new ArrayList<>();

    InputStreamReader r = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(r);

    public POS(String cashierName, String branchName, String customerName) {
        cashier = cashierName;
        branch = branchName;
        customer = customerName;
    }

    public void startBillingProcess() {
        Bill newBill = new Bill(cashier, branch, customer);
        while(true){
            try {
                GroceryItem nextItem = getItemDetails();
                newBill.addToList(nextItem);
            } catch (IOException e) {

            } catch (ItemNotFoundException e){
                System.out.println("No Item Found!");
            }catch (NumberFormatException nfe){
                System.out.println("Enter a Valid Item Code!");
            }
        }


    }


    private GroceryItem getItemDetails() throws IOException, ItemNotFoundException, NumberFormatException{
        String item_code = br.readLine();
        String detail = Groceries.getStringOfDetails(Integer.parseInt(item_code));

        String[] details = detail.split(",");
        String name = details[0], manufacturer = details[1], mfd = details[3], exp = details[4];

        double unitPrice = Double.parseDouble(details[2]);
        System.out.println("Quantity? ");
        double quantity = Double.parseDouble(br.readLine());
        double price = unitPrice*quantity;

        int discount = createDiscount();

        System.out.printf("Name: %s\n" +
                "Qty: %.2f\n" +
                "Price: Rs %.2f\n" +
                "Manufacturer: %s\n" +
                "MFD: %s\n" +
                "EXP: %s\n" +
                "Discount: %d%%");
        System.out.println();
        GroceryItem item = null;

        return item;
    }

    private int createDiscount() {
        int chance = new Random().nextInt(1, 101);
        if(chance > 70){
            System.out.println("Discount Added!");
            return new Random().nextInt(1, 76);
        }
        return 0;
    }


}
class ItemNotFoundException extends Exception{
    public ItemNotFoundException(){
        super("ITEM NOT FOUND!");
    }
}
class Groceries{
    private static HashMap<Integer, String> groceryDB = new HashMap<>();
    //itemCode, name, manufacturer, size/weight, price, mfd, exp date
    public static void initMap(){
        groceryDB.put(1, "Beans,Alankulama,300.00,2023-03-03,2023-04-03");
        groceryDB.put(2, "Carrots,Alankulama,1000.00,2023-03-03,2023-04-03");
    }

    public static String getStringOfDetails(int code) throws ItemNotFoundException {
        if(groceryDB.containsKey(code))
            return groceryDB.get(code);
        else{
            throw new ItemNotFoundException();
        }
    }

}

class GroceryItem{
    int itemCode;
    double price;
    double weightOrSize;
    String DOM;
    String DOE;
    String manufacturer;
    String name;
    double discount;


    private GroceryItem(int itemCode, double price, double weightOrSize, String DOM, String DOE, String manufacturer, String name, double discount) {
        this.itemCode = itemCode;
        this.price = price;
        this.weightOrSize = weightOrSize;
        this.DOM = DOM;
        this.DOE = DOE;
        this.manufacturer = manufacturer;
        this.name = name;
        this.discount = discount;
    }
}
