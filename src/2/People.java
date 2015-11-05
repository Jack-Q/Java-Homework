import java.util.Arrays;

public class People {
	public static void main(String[] args){
		Person A = new Person("ERIN", "XJTU", "888-88888888", "***@***.com");
		Person[] persons = new Person[2];
		persons[0] = A;
		persons[1] = new Person("ALIE", "XJTU", "888-88888888", "***@***.com");
		System.out.println(Arrays.toString(persons));
		Arrays.sort(persons);
		System.out.println(Arrays.toString(persons));
		
		Student[] students = new Student[3];
		students[0] = new Student("aRIN", "XJTU", "888-88888888", "***@***.com","Freshman");
		students[1] = new Student("ZRIN", "XJTU", "888-88888888", "***@***.com","Freshman");
		students[2] = new Student("ARIN", "XJTU", "888-88888888", "***@***.com","pppp");
		System.out.println(Arrays.toString(students));
		Arrays.sort(students);
		System.out.println(Arrays.toString(students));
		
		Employee[]employees = new Employee[3];
		employees[0] = new Employee("ERIN", "XJTU", "888-88888888", "***@***.com","1-106",8924, new MyDate(1996, 9, 1));
		employees[1] = new Employee("ZRIN", "XJTU", "888-88888888", "***@***.com","1-106",5154, new MyDate(1996, 9, 1));
		employees[2] = new Employee("ARIN", "XJTU", "888-88888888", "***@***.com","1-106",8924, new MyDate(1996, 9, 1));
		System.out.println(Arrays.toString(employees));
		Arrays.sort(employees);
		System.out.println(Arrays.toString(employees));
		
		Faculty[] faculties = new Faculty[2];
		faculties[0] = new Faculty("ERIN", "XJTU", "888-88888888", "***@***.com", "1-106",8924, new MyDate(1996, 9, 1), 35, "high");
		faculties[1] = new Faculty("ARIN", "XJTU", "888-88888888", "***@***.com", "1-106",8924, new MyDate(1996, 9, 1), 35, "middle");
		System.out.println(Arrays.toString(faculties));
		Arrays.sort(faculties);
		System.out.println(Arrays.toString(faculties));
		
		Staff[] staff = new Staff[2];
		staff[0] = new Staff("ERIN", "XJTU", "888-88888888", "***@***.com", "1-106",8924, new MyDate(1996, 9, 1), "manager");
		staff[1] = new Staff("ARIN", "XJTU", "888-88888888", "***@***.com", "1-106",8924, new MyDate(1996, 9, 1), "clerk");
		System.out.println(Arrays.toString(staff));
		Arrays.sort(staff);
		System.out.println(Arrays.toString(staff));
	}
}


class Person implements Comparable<Person>{
	private String Name;
	private String address;
	private String phoneNumber;
	private String emailAddress;
	public Person(String n, String Adr, String Pho, String Ema){
		this.Name = n;
		this.address = Adr;
		this.phoneNumber = Pho;
		this.emailAddress = Ema;
	}
	public Person(){
		this("", "", "", "");
	}
	public String getName(){
		return this.Name;
	}
	public String toString(){
		return "This person named " + Name;
	}
	public int compareTo(Person other){
		return getName().compareTo(other.getName());
	}
}


class Student extends Person{
	public Student(String n, String Adr, String Pho, String Ema, String Status){
		super(n, Adr, Pho, Ema);
		 
		if(Status.equals("Freshman")||Status.equals("Sophomore")||Status.equals("Junior")||Status.equals("Senior"))
			this.Status = Status;
		else{
			System.out.println("illegal Status");
			this.Status = "Unknown";
		}
	}
	public Student(){
		this("", "", "", "","");
	}
	public String toString(){
		return "This student named " + getName();
	}
	private final String Status;
	public int compareTo(Student other){
		return getName().compareTo(other.getName());
	}
}


class Employee extends Person{
	private String office;
	private double salary;
	private MyDate date_hired;
	public Employee(String n, String Adr, String Pho, String Ema, String off, double sala, MyDate date){
		super(n, Adr, Pho, Ema);
		this.office = off;
		this.salary = sala;
		this.date_hired = date;
		}
	public Employee(){
		this("", "", "", "", "", 0, new MyDate());
	}
	public String toString(){
		return "This employee named " + getName() +", salary is "+ this.salary;
	}
	public double getSalary(){
		return salary;
	}
	public int compareTo(Person other){
		//if(salary != other.salary) return (int)(salary-other.salary);
		Employee o = (Employee) other;
		if(salary < o.getSalary()) return -1;
		if(salary > o.getSalary()) return 1;
		return getName().compareTo(other.getName());
	}
}


class Faculty extends Employee{
	private double office_hours;
	private String rank;
	public Faculty(String n, String Adr, String Pho, String Ema,String off, double sala, MyDate date, double hours, String rank){
		super(n, Adr, Pho, Ema, off, sala, date);
		this.office_hours = hours;
		this.rank = rank;
	}
	public Faculty(){
		this("", "", "", "", "", 0, new MyDate(), 0, "");
	}
	public String toString(){
		return "This faculty named " + getName();
	}
	public int compareTo(Faculty other){
		return getName().compareTo(other.getName());
	}
}


class Staff extends Employee{
	private String title;
	public Staff(String n, String Adr, String Pho, String Ema,String off, double sala, MyDate date, String title){
		super(n, Adr, Pho, Ema, off, sala, date);
		this.title = title;
	}
	public Staff(){
		this("", "", "", "", "",0, new MyDate(), ""); 
	}
	public String toString(){
		return "This faculty named " + getName();
	}
	public int compareTo(Staff other){
		return getName().compareTo(other.getName());
	}
}


class MyDate{
	public MyDate(int y, int m, int d){
		this.year = y;
		this.month = m;
		this.day = d;
	}
	public MyDate(){
		this(1970, 1, 1);
	}
	private int year,month,day;
}
