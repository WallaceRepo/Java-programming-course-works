public class Main extends Object {

    public static void main(String[] args) {
        Employee tim = new Employee("Tim", "11/11/122", "12,12.3234");
        System.out.println(tim);
        System.out.println(("Age = " + tim.getAge()));
        System.out.println("pay = " + tim.collectPay());

        SalariedEmployee joe = new SalariedEmployee("Joe", "wew/323/32", "08/23/31", 11000);
        System.out.println(joe);
        System.out.println("Joe's Paycheck = $" + joe.collectPay());

        joe.retire();
        System.out.println("Joe's Pention check = $" + joe.collectPay());

        HourlyEmployee mary = new HourlyEmployee("Mary", "05/05/1990", "22/32/12", 65);
        System.out.println(mary);
        System.out.println("Mary's Paycheck = $" + mary.collectPay());
        System.out.println("Mary's Holiday = $ " + mary.getDoublePay());
    }
}
    class Worker {
        private String name;
        private String birthDate;

        protected String endDate;
        public Worker (){

        }
        public Worker(String name, String birthDate) {
            this.name = name;
            this.birthDate = birthDate;
        }

        public int getAge(){
            int currentYear = 2023;
            int birthYear = Integer.parseInt(birthDate.substring(6));
            return (currentYear - birthYear);
        }
        public double collectPay(){
             return 0.0;
        }
        public void terminate(String endDate){
           this.endDate = endDate;
        }

        @Override
        public String toString() {
            return "Worker{" +
                    "name='" + name + '\'' +
                    ", birthDate='" + birthDate + '\'' +
                    ", endDate='" + endDate + '\'' +
                    '}';
        }
    }
class Employee extends Worker {
    private long employeeId;
    private String hireDate;

    private static int employeeNo = 1;

    public Employee(String name, String birthDate, String hireDate) {
        super(name, birthDate);
        this.employeeId = Employee.employeeNo++;
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", hireDate='" + hireDate + '\'' +
                "} " + super.toString();
    }
}
class SalariedEmployee extends Employee {
    private double annualSalary;
    private boolean isRetired;

    public SalariedEmployee(String name, String birthDate, String hireDate, double annualSalary) {
        super(name, birthDate, hireDate);
        this.annualSalary = annualSalary;
    }
    @Override
    public double collectPay() {
       // return (int ) annualSalary / 26;
        double paycheck = annualSalary / 26;
        double adjustedPay = (isRetired) ? 0.9 * paycheck : paycheck;

        return (int) adjustedPay;
    }

    public void retire(){
        terminate("23/23/12");
        isRetired = true;
    }
}
class HourlyEmployee extends Employee {
    private double hourlyPayRate;

    public HourlyEmployee(String name, String birthDate, String hireDate, double hourlyPayRate) {
        super(name, birthDate, hireDate);
        this.hourlyPayRate = hourlyPayRate;
    }
    @Override
    public double collectPay(){
        return 40 * hourlyPayRate;
    }
    public double getDoublePay(){
       return 2 * collectPay();
    }
}
