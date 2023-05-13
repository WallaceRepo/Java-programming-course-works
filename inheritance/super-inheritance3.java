public class Main extends Object {

    public static void main(String[] args) {
           Student max = new Student("Max", 21);
          // System.out.println(max.toString());
        System.out.println(max); // it calls toString() method implicitly

        PrimarySchoolStudent David = new PrimarySchoolStudent("David Adam", 8, "Wallace");
        PrimarySchoolStudent George = new PrimarySchoolStudent("George Scott", 8, "Wallace");

        System.out.println(David);
        System.out.println(George);
    }
}

class Student {
    private String name;
    private int age;

    Student (String name, int age) {
        this.name = name;
        this.age = age;
    }

 //   @Override
//    public String toString() {
//        return super.toString();
//    }

    @Override
    public String toString() {
           return  name + " is "  + age;
    }
}

class PrimarySchoolStudent extends Student {
    private String parentName;
    PrimarySchoolStudent(String name, int age, String parentName) {
        super(name, age);
        this.parentName = parentName;
    }
    public String toString() {
        return parentName + "'s kid, " + super.toString();
    }
}
