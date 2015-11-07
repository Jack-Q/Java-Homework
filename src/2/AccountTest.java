/* 
	Title: Implementing an Account class (demo)
	Author: Heley Chen (陈铮)
	Date: 2015-11-7 17:25:10
*/

import java.util.*;

public class AccountTest{
	public static void main(String[] args){
		System.out.println("It's a test for account");
		//set the AnnualInterestRate = 1.5%
		Account.setAnnualInterestRate(0.015);
		//Create a new account
		Account A = new Account("George", 1122, 1000);
		A.deposit(30).deposit(40).deposit(50).withdraw(5).withdraw(4).withdraw(2).printSummary();
	}
}

class Account{
	//constructors:
	//constructor #0 :let's hide the common constructor
	private Account(int id,double balance, Date dateCreated, String name, ArrayList<Transaction> transactions){
		this.id = id;
		this.balance = balance;
		this.dateCreated = dateCreated;
		this.name = name;
		this.transactions = transactions;
	}
	//constructor #1: call the constructor #0
	public Account(String name, int id, double balance){
		this(id, balance, new Date(), name, new ArrayList<Transaction>());
	}
	//constructor #2: call the constructor #1
	public Account(int id,double balance){
		this("", id, balance);
	}
	//constructor #3: default : call the constructor #2
	public Account(){
		this(0,0);
	}
	//the accessor methods
	public int getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public double getBalance(){
		return this.balance;
	}
	public double getAnnualInterestRate(){
		return annualInterestRate;
	}
	public Date getDateCreated(){
		//we should get the clone, but not the reference of this.dateCreated, or the 'Encapsulation'.
		//我们应该获取Date对象的克隆，而不是它的引用，否则会破坏封装性。
		return (Date) this.dateCreated.clone();
	}
	public double getMonthlyInterestRate(){
		return getAnnualInterestRate()/12;
	}
	//the mutator methods
	public Account setId(int id){
		this.id = id;
		return this;
	}
	public Account setName(String name){
		this.name = name;
		return this;
	}
	public Account setBalance(double balance){
		this.balance = balance;
		return this;
	}
	public static void setAnnualInterestRate(double rate){
		annualInterestRate = rate;
	}
	//the general methods
	public Account withdraw(double amount, String description){
		//Todo something here to guarantee the balance none-negative
		//if it's possible, the program won't do anything
		if(this.balance < amount) return this;
		this.balance -= amount;
		this.transactions.add(new Transaction('W', amount, this.balance, description));
		return this;
	}
	public Account withdraw(double amount){
		return withdraw(amount, "");
	}
	public Account deposit(double amount, String description){
		this.balance += amount;
		this.transactions.add(new Transaction('D', amount, this.balance, description));
		return this;
	}
	public Account deposit(double amount){
		return deposit(amount, "");
	}
	//the print methods(output)
	public Account printSummary(){
		System.out.println("Account ID: " + this.id);
		System.out.println("Name: " + this.name);
		System.out.println("InterestRate:");
		System.out.println("+ Annual: " + getAnnualInterestRate());
		System.out.println("+ Monthly: " + getMonthlyInterestRate());
		System.out.println("Transactions:");
		//to do something here to get a better format
		System.out.println(String.valueOf(transactions));
		return this;
	}

	private int id;
	private double balance;
	private static double annualInterestRate = 0;
	private Date dateCreated;
	private String name;
	private ArrayList<Transaction> transactions;
}

class Transaction {
	public Transaction(char type, double amount, double balance, String description){
		this.date = new Date();
		this.type = type;
		this.amount = amount;
		this.balance = balance;
		this.description = description;
	}
	public String toString(){
		return String.valueOf(this.date) + " " + this.type + " " + this.amount + " " + this.balance + " " + this.description;
	}
	private Date date;
	private char type;
	private double amount;
	private double balance;
	private String description;
}

//API: Java.util.Date 
//Allocates a Date object and initializes it so that it represents the time at which it was allocated, measured to the nearest millisecond.
