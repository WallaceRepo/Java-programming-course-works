import java.util.ArrayList;

public class Autoboxing {
    public static void main(String[] args) {
        Bank bank = new Bank("National Australia Bank");

        bank.addBranch("Adelaide");

        bank.addCustomer("Adelaide", "Tim", 50.05);
        bank.addCustomer("Adelaide", "Mike", 175.34);
        bank.addCustomer("Adelaide", "Percy", 220.12);

        bank.addCustomerTransaction("Adelaide", "Tim", 44.22);
        bank.addCustomerTransaction("Adelaide", "Tim", 12.44);
        bank.addCustomerTransaction("Adelaide", "Mike", 1.65);

        bank.listCustomers("Adelaide", true);
    }
}
class Bank {
    private String name;
    private ArrayList<Branch> branches;

    public Bank(String name) {
        this.name = name;
        this.branches = new ArrayList<Branch>();
    }
    public boolean addBranch(String branchName){
         if(findBranch(branchName) == null) {
             Branch branch = new Branch(branchName);
             branches.add(branch);
             return true;
         }
         return false;
    }
    public boolean addCustomer(String branchName, String customerName, double initialTransaction) {
          Branch bran = findBranch(branchName);
          if( bran == null) { return false; }

          ArrayList<Customer> allCustomers = bran.getCustomers();
          for ( var customer : allCustomers) {
              if(customer.getName().equalsIgnoreCase(customerName)){
                  return false;
              }
          }
        bran.newCustomer(customerName, initialTransaction);
        System.out.println("New Customer added to : " + branchName + " " + customerName);
        return true;
    }
    public boolean addCustomerTransaction(String branchName, String customerName, double transaction){
        Branch bran = findBranch(branchName);
        if( bran == null) { return false; }

        ArrayList<Customer> allCustomers = bran.getCustomers();
        for ( var customer : allCustomers) {
            if(customer.getName().equalsIgnoreCase(customerName)){
                customer.addTransaction(transaction);
                return true;
            }
        }
       return false;
    }
    private Branch findBranch(String branchName){
         for (var branch : branches ) {
             if(branch.getName().equalsIgnoreCase(branchName)) {
                 return branch;
             }
         }
        return null;
    }
//    public boolean listCustomers(String branchName, boolean printTransactions){
//        Branch bran = findBranch(branchName);
//        if( bran == null) { return false; };
//        ArrayList<Customer> allCustomers = bran.getCustomers();
//        System.out.println("Customer details for branch " + branchName);
//
//        for (var customer : allCustomers) {
//           System.out.println("customer: " + customer.getName() + " " + customer.getTransactions());
//        }
//        return true;
//    }
public boolean listCustomers(String branchName, boolean printTransactions) {
    Branch bran = findBranch(branchName);
    if (bran == null) {
        return false;
    }

    ArrayList<Customer> allCustomers = bran.getCustomers();
    System.out.println("Customer details for branch " + branchName);

    int i = 0; // Initialize the counter outside the loop

    for (var customer : allCustomers) {
        i++; // Increment the counter for each customer
        System.out.print("Customer: " + customer.getName());
        if (printTransactions) {
            ArrayList<Double> allTransactions = customer.getTransactions();
            System.out.println(" [" + i + "] Transactions:");
            int j = 0;
            for (var transac : allTransactions) {
                j++; // Increment the transaction counter for each transaction
                System.out.println("[" + j + "] Amount " + transac);
            }
        } else {
            System.out.println(" [" + i + "]");
        }
    }
    return true;
}
class Branch {
    private String name;
    private ArrayList<Customer> customers;

    public Branch(String name) {
        this.name = name;
        this.customers = new ArrayList<Customer>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    public boolean newCustomer(String name, double initialTransaction){
          if(findCustomer(name) == null) {
              customers.add( new Customer(name, initialTransaction)); // autoBoxing to Double from double;
              return true;
          }
          return false;
    }
    public boolean addCustomerTransaction(String customerName, double transaction){
        Customer customer = findCustomer(customerName);
        if( customer!= null) {
            customer.addTransaction(transaction);
            return true;
        }
        return false;
    }
    private Customer findCustomer(String customerName){
        for ( var customer : customers) {
            if(customer.getName().equalsIgnoreCase(customerName)) {
                return customer;
            }
       }
        return null;
    }
}
class Customer {
    private String name;
    private ArrayList<Double> transactions;

    public Customer(String name, double initialTransaction) {
        this.name = name;
        this.transactions = new ArrayList<Double>();
        addTransaction(initialTransaction);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Double> getTransactions() {
        return transactions;
    }

    public void addTransaction(double transaction) {
        transactions.add(transaction);
    }
  }
}
/* 
New Customer added to : Adelaide Tim
New Customer added to : Adelaide Mike
New Customer added to : Adelaide Percy
Customer details for branch Adelaide
Customer: Tim [1] Transactions:
[1] Amount 50.05
[2] Amount 44.22
[3] Amount 12.44
Customer: Mike [2] Transactions:
[1] Amount 175.34
[2] Amount 1.65
Customer: Percy [3] Transactions:
[1] Amount 220.12

 */
