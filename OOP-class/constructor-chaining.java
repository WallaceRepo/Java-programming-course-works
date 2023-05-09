public class Customer {
    private String name;
    private double creditLimit;
    private String email;

    public double getCreditLimit() {
        return creditLimit;
    }

    public String getEmail() {
        return email;
    }

    // getters
    public String getName(){
        return name;
    }
    Customer(String name, double creditLimit, String email){
        this.name = name;
        this.creditLimit = creditLimit;
        this.email = email;
    }
    Customer(){
        this("Jane","sf@gmail.com");
    }
    Customer(String name, String email){
        this(name, 230, email);

    }


}
public class main {
    public static void main(String[] args) {
            Customer customer = new Customer("Moya", 32783, "asdf@email");
            System.out.println(customer.getName());
            System.out.println(customer.getCreditLimit());
            System.out.println(customer.getEmail());

            Customer secondCustomer = new Customer(); //Default customer constructor won't be created if we have any constructor already.
        System.out.println(secondCustomer.getName());
        System.out.println(secondCustomer.getCreditLimit());
        System.out.println(secondCustomer.getEmail());
    }

}

// Moya
// 32783.0
// asdf@email
// Jane
// 230.0
// sf@gmail.com
