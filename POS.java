import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Login {
    private String cashierName;
    private String branchName;
    InputStreamReader r = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(r);
    //starts everything
    private void startSystem() throws IOException{
        //get cashier name and branch name
            try {
                System.out.println("Cashier Name: ");
                cashierName = br.readLine();

                System.out.println("Branch: ");

                branchName = br.readLine();

                //while loop is used to keep coming back to main menu
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
        //main menu
        String choice = null;
        System.out.println("Super Saver Supermarket");
        System.out.println("1. New Bill");
        System.out.println("2. Pending BIlls");
        System.out.println("3. Exit");
        //while loop used to keep looping until valid input is given
        while (true) {
            System.out.println("Choice: ");
            //We will keep using replace all to ensure no spaces
            try {
                choice = br.readLine().replaceAll(" ", "");
            }catch(IOException e){
                e.printStackTrace();
                System.exit(1);
            }

            if(choice.equals("3"))
                return true;
            else if(choice.equals("1")){
                //leaving this blank means not registered. 
                System.out.println("Enter Customer Name (Leave Blank if Needed): ");
                String customerName = new Scanner(System.in).nextLine();


                if(customerName.isBlank()){
                    customerName = "N/A";
                }
                
                //POS class called with a new bill to issue
                POS biller = new POS();
                biller.startBillingProcess(new Bill(cashierName, branchName, customerName));

                break;
            }else if(choice.equals("2")){
                //this loads a bill using serialization.
                System.out.println("Enter ID of Bill to Load: ");
                String id = br.readLine();
                BillSaver.loadAndContinueBill(id);
                break;
            }else
                System.out.println("Invalid Input");

        }

        return false;
    }

    public static void main(String[] args) {
        //initialize the grocery data
        Groceries.initMap();

        try {
            new Login().startSystem();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

//instances of this class will become the bills of customers.
class Bill implements Serializable{
    private String cashier, branch, customer;
    //the items will be stored inside an array list of GroceryItems
    private final ArrayList<GroceryItem> items = new ArrayList<>();

    public Bill(String cashier, String branch, String customer){
        this.customer = customer;
        this.cashier = cashier;
        this.branch = branch;
    }

    public void addToList(GroceryItem item){
        items.add(item);
    }
    
    //we optionally added a remove from list items in case the cashier made a mistake
    public void removeFromList(){
        if(items.isEmpty()){
            System.out.println("No Items to Remove!");
        }else{
            int last = items.size() -1;
            GroceryItem itemToRemove = items.get(last);
            items.remove(last);
            System.out.println("Successfully Removed the Item: "+itemToRemove.getName());
        }
    }
    
    //this will print the bill in a table, doing necessary calculations for items of customer
    public void printBill(){
        double netTotal = 0.00;
        double unitPriceTotal = 0.00;


        System.out.println("    Cashiers Name: "+ cashier);
        System.out.println("    Branch Name  : "+ branch);
        System.out.println("    Customer Name: "+ customer);
        System.out.println();

        System.out.println("    ╔════════════════╦═══════════════╦══════════╦═════════════╦══════════════╗");
        System.out.println("    ║   Item Name    ║Unit Price (Rs)║   Qty.   ║Discount (Rs)║Net Price (Rs)║");
        System.out.println("    ╠════════════════╬═══════════════╬══════════╬═════════════╬══════════════╣");

        
        for (GroceryItem item: items) {

            double netPrice = item.getPrice()*item.getWeightOrSize()*(1-item.getDiscount());
            netTotal += netPrice;
            unitPriceTotal += (item.getPrice() * item.getWeightOrSize());

            System.out.printf("    ║%16s║%15.2f║%10.2f║%13.2f║%14.2f║%n",
                    item.getName(),
                    item.getPrice(),
                    item.getWeightOrSize(),
                    item.getDiscount()*item.getPrice()*item.getWeightOrSize(),
                    netPrice
            );
        }
        System.out.println("    ╚════════════════╩═══════════════╩═════╦════╩═════════════╬══════════════╣");
        System.out.println("                                           ║Tot. Discount (Rs)║Tot. Price(Rs)║");
        System.out.println("                                           ╠══════════════════╬══════════════╣");

        System.out.printf( "                                           ║%18.2f║%14.2f║%n",
                (unitPriceTotal-netTotal),
                netTotal
        );
        System.out.println("                                           ╚══════════════════╩══════════════╝");
        System.out.println();

        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDateTime = currentDateTime.format(formatter);

        System.out.println("Issued Date and Time: " + formattedDateTime);
        System.out.println();
        System.out.println("Come Back Again!");
        System.out.println();
        System.out.println();
    }

}

//this class deals with retrieving items and adding  them to the bill, removing items from bill and saving a bill for later
//or exiting to print the bill
class POS{

    InputStreamReader r = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(r);

    public void startBillingProcess(Bill bill) {
        //here we give a choice to checkout, pause or remove last item
        System.out.println("Tip: Enter E to Checkout, P to Pause Temporarily or R to Remove Last Item");
        while(true){
            try {
                System.out.println("Enter Item Code: ");
                String choice = br.readLine().replaceAll(" ", "");
                if(choice.equals("E")){
                    bill.printBill();
                    break;
                } else if (choice.equals("P")) {
                    //the bill will be saved in a serialized file with a file name chosen by the cashier
                    System.out.println("Enter an ID to Save Bill as (REMEMBER THIS): ");
                    String id = br.readLine();
                    int x = BillSaver.saveBill(bill, id);
                    if(x == -1)
                        continue;
                    System.out.println("Saved Successfully with ID "+id);
                    break;
                } else  if(choice.equals("R")){
                    bill.removeFromList();
                }else {
                    GroceryItem nextItem = getItemDetails(choice);
                    bill.addToList(nextItem);
                }
            } catch (IOException e) {
                System.out.println("An Unexpected Error Occured: "+e.getMessage());
            } catch (ItemNotFoundException e){//created and handled the ItemNotFoundException as asked
                System.out.println("No Item Found!");
            }catch (NumberFormatException nfe){
                System.out.println("Enter a Valid Item Code!");
            }
        }


    }

    //modified getItemDetails function. it gets the details of an item from the Groceries class as a whole string and splits it and processes it accordingly.
    private GroceryItem getItemDetails(String item_code) throws IOException, ItemNotFoundException, NumberFormatException{
        int itemCode = Integer.parseInt(item_code);
        String detail = Groceries.getStringOfDetails(itemCode);

        String[] details = detail.split(",");
        String name = details[0], manufacturer = details[1], mfd = details[3], exp = details[4];

        double unitPrice = Double.parseDouble(details[2]);
        System.out.println("Quantity? ");
        double quantity = Double.parseDouble(br.readLine());
        double price = unitPrice;

        int discount = createDiscount();

        System.out.printf  ("Name:......... %s\n" +
                            "Qty:.......... %.2f\n" +
                            "Price:........ Rs %.2f\n" +
                            "Manufacturer:. %s\n" +
                            "MFD:.......... %s\n" +
                            "EXP:.......... %s\n" +
                            "Discount:..... %d%%",name,quantity,price,manufacturer,mfd, exp,discount);
        System.out.println();
        //creates and returns a grocery item.
        GroceryItem item = new GroceryItem(itemCode, price, quantity, mfd, exp, manufacturer, name, discount/100.0);

        return item;
    }
    
    //created a function that gives a 50% chance of having a discount between 1% and 75% 
    private int createDiscount() {
        int chance = new Random().nextInt(1, 101);
        int discountChance = 50;
        if(chance > 100 - discountChance){
            System.out.println("Discount Given!");
            return new Random().nextInt(1, 76);
        }
        return 0;
    }


}

//the class for the ItemNotFoundException
class ItemNotFoundException extends Exception{
    public ItemNotFoundException(){
        super("ITEM NOT FOUND!");
    }
}

//this class contains the hardcoded details of the groceries
class Groceries{
    private static HashMap<Integer, String> groceryDB = new HashMap<>();
    //itemCode, name, manufacturer, size/weight, price, mfd, exp date
    //hardcoded items
    public static void initMap(){
        groceryDB.put(1, "Beans,Alankulama,300.00,2023-03-03,2023-04-03");
        groceryDB.put(2, "Carrots,Alankulama,1000.00,2023-03-03,2023-04-03");
    }
    
    //return the huge string of details to be processed.
    public static String getStringOfDetails(int code) throws ItemNotFoundException {
        if(groceryDB.containsKey(code))
            return groceryDB.get(code);
        else{
            throw new ItemNotFoundException();
        }
    }

}

//this class allows instances of grocery items.
class GroceryItem implements Serializable{
    private int itemCode;
    private double price;

    public double getPrice() {
        return price;
    }

    public double getWeightOrSize() {
        return weightOrSize;
    }

    public String getName() {
        return name;
    }

    public double getDiscount() {
        return discount;
    }

    private double weightOrSize;
    private String DOM;
    private String DOE;
    private String manufacturer;
    private String name;
    private double discount;


    public GroceryItem(int itemCode, double price, double weightOrSize, String DOM, String DOE, String manufacturer, String name, double discount) {
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


//the class that saves/loads bills from .ser files.
class BillSaver{
    //method to save bill
    public static int saveBill(Bill bill, String id){
        String path = id+".ser";

        try {
            //we make sure that a bill has a unique id before saving.
            File file = new File(path);
            if(file.exists()){
                System.out.println("ID TAKEN!");
                return -1;
            }
            new File(path);


            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream os = new ObjectOutputStream(fos);

            os.writeObject(bill);

            os.close();

        }catch(IOException ex) {
            throw new RuntimeException(ex);
        }
        return 0;
    }
    
    //method to load bill
    public static void loadAndContinueBill(String id) {
        String path = id+".ser";

        Bill billToLoad = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream os = new ObjectInputStream(fis);

            Object gotIt = os.readObject();

            billToLoad = (Bill) gotIt;

        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(billToLoad == null){
            System.out.println("No such Bill Found!");
        }else{
            System.out.println("Successfully Found Bill!");
            POS biller = new POS();
            biller.startBillingProcess(billToLoad);
            
            //makes sure to delete the serialized files after we are done with it. 
            File file = new File(path);
            file.delete();
        }
    }
}
