
public class main {
    public static void main(String[] args) {
        // Car car = new Car();

//         car.setMake("BMW");
//         car.setColor("Blue");
//         car.setConvertable(true);
//         car.setMake("Modern");
//         car.setModel("Maes");
//         System.out.println(" make " + car.getMake());
//         System.out.println(" model " + car.getModel());
//         car.describe();
         BankAccount myAccount = new BankAccount();
           myAccount.setEmail("hwllo@small");
           myAccount.setCustomer_name("Sophia");
           myAccount.setAccountNumber(3456);
           myAccount.deposit(80.000);
           myAccount.withdraw(90.000);
           //System.out.println(myAccount.getBalance());
    }
}
//
public class BankAccount {
    private int accountNumber;
    private double balance;
    private String customer_name;
    private String email;
    private String phone;

    public void setAccountNumber (int number){
        this.accountNumber = number;
    }
    public void setBalance ( double balance) {
        this.balance = balance;
    }
    public void setCustomer_name( String name) {
        this.customer_name = name;
    }
    public void setEmail ( String email) {
        this.email = email;
    }
    public void  setPhone( String phone) {
        this.phone = phone;
    }
    public int getAccountNumber() {
        return  this.accountNumber;
    }
    public double getBalance(){
        return this.balance;
    }
    public String getCustomer_name(){
        return this.customer_name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPhone(){
        return this.phone;
    }
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit of $" + amount + " made. New balance is $" + balance);
    }
    public void withdraw(double amount) {
       if(this.balance - amount < 0) {
            System.out.println("Insufficient balance");
        } else {
           balance -= amount;
           System.out.println("Remaining balance  = $ " + balance);
        }
    }
}
//
public class Car {
    private String make = "Tesla";
    private String model = "Mobel";
    private String color = "Yellow";
    private int doors = 2;
    private boolean convertable = true;

    public void describe() {
        System.out.println(doors + "-Door "+
                 color + " " +
                make + " " +
                model + " " +
                (convertable ? "Convertable ": " " ));
    }
    public String getMake(){
        return make;
    }

    public String getColor() {
        return color;
    }

    public int getDoors() {
        return doors;
    }

    public boolean isConvertable() {
        return convertable;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if(make == null) make = "Unknown";
        String lowercaseMake = make.toLowerCase();
        switch (lowercaseMake) {
            case "holden", "flowe", "tesla" -> this.make = make;
            default -> { this.make = "Unsupported"; }
        }
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public void setConvertable(boolean convertable) {
        this.convertable = convertable;
    }

    public void setMake(String make) {
        this.make = make;
    }
}
//

public class person {
    private String firstName;
    private String lastName;
    private int age;

    public String getFirstName() {
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public int getAge(){
        return age;
    }
    public void setFirstName(String name){
        this.firstName = name;
    }
    public void  setLastName(String name){
        this.lastName = name;
    }
    public void setAge(int age) {
        if(age < 0 || age > 100){
            this.age = 0;
        }
        this.age = age;
    }
    public boolean isTeen (){
        if(age < 20 && age > 12) {
            return true;
        }
        return false;
    }
    public String getFullName() {
        if (firstName.isEmpty() && lastName.isEmpty()) {
            return "";
        } else if (this.firstName.isEmpty()) {
            return this.lastName;
        } else if (this.lastName.isEmpty()) {
            return this.firstName;
        } else {
            return this.firstName + " " + this.lastName;
        }
    }

}

