public class main {
    public static void main(String[] args) {
        // POJO -Plain old java object, also called: DTO - data transfer object, Entity, javabean, or bean

        for(int i = 1; i <= 5; i++){
            Student s = new Student( "S56" + i,

                    switch(i){
                case 1-> "mary";
                case 2-> "Carol";
                case 3-> "harry";
                default -> "Anonimous";
                   },
                  "05/22/34",
                    "java masterclass"
                   );
            System.out.println(s);
        }
        for(int i = 1; i <= 5; i++){
            LPstudent lp = new LPstudent( "S56" + i,

                    switch(i){
                        case 1-> "mary";
                        case 2-> "Carol";
                        case 3-> "harry";
                        default -> "Anonimous";
                    },
                    "05/22/34",
                    "java masterclass"
            );
            System.out.println(lp);
        }

        Student pojo = new Student("S08", "Ann", "329", "Java MasteClass");

        LPstudent recordStudent = new LPstudent("dsfka", "bil", "dka", "asdhf");

        System.out.println(pojo);
        System.out.println(recordStudent);

        System.out.println(pojo.getName() + " is taking" + pojo.getClassList());
         // in record accessor methods: Java's implicitly created code does not include prefix. Just use the same name as field name
        System.out.println(recordStudent.name() + " is taking " + recordStudent.classList());

        // Record meant to be immutable, so it does not have setter implicit accessor methods;
        pojo.setClassList(pojo.getClassList() + "Java Pojo object lesson");
       // recordStudent.setClassList(recordStudent.classList() + "Java record lessson");
    }
}
/////////////
public record LPstudent(String id, String name, String dateOfBirth, String classList) {

}
///////////////

public class Student {
    private String id;
    private String name;
    private String dateOfBirth;
    private String ClassList;

    @Override   // anottation is kind of similar to comment but gives info to a compiler // Overridden method is not the same thing as an overloaded method.
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", ClassList='" + ClassList + '\'' +
                '}';
    }

    public Student(String id, String name, String dateOfBirth, String classList) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        ClassList = classList;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getClassList() {
        return ClassList;
    }

    public void setClassList(String classList) {
        ClassList = classList;
    }
}
