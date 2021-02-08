public class MainTest {
    static class Student {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        Student student = new Student();
        student.name = new String("zhangrui");
        String name = "zhangrui";
        System.out.println(student.getName() == name);
        System.out.println(student.getName().equals(name));
    }
}
