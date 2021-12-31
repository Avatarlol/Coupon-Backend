package com.project.CouponMVC.utils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.project.CouponMVC.enums.ClientType;
import com.project.CouponMVC.exceptions.InvalidInput;

@SuppressWarnings("resource")
public class ScanManager {

	public static String getStringLine(String message){ 
		Scanner scan = new Scanner(System.in);
		String strLine;
		
		while(true) {
			try {
				System.out.println(message);
				
				strLine = scan.nextLine();
				
				return strLine;
			}catch (InputMismatchException e) {
				System.out.println("Invalid input.\n");
			}
		}
	}
	
	public static String getStringSingle(String message){ 
		Scanner scan = new Scanner(System.in);
		String strSingle;
		
		while(true) {
			try {
				System.out.println(message);
				
				strSingle = scan.next();
				if(scan.nextLine().length()>0) throw new InvalidInput("Entered too many arguments (expected 1 argument).");
				
				return strSingle;
			}catch (InvalidInput e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int getInt(String message){ 
		Scanner scan = new Scanner(System.in);
		int intSingle;

		while(true) {
			try {
				System.out.println(message);
				
				intSingle = scan.nextInt();
				if(scan.nextLine().length()>0) throw new InvalidInput("Entered too many arguments (expected 1 argument).");
				
				return intSingle;
			}catch (InputMismatchException e) {
				System.out.println("Invalid input.\n");
			} catch (InvalidInput e) {
				e.printStackTrace();
			}
		}
	}

	public static double getDouble(String message) { 
		Scanner scan = new Scanner(System.in);
		double doubleNum;

		while(true) {
			try {
				System.out.println(message);
				doubleNum = scan.nextDouble();
				
				if(scan.nextLine().length()>0) throw new InvalidInput("Entered too many arguments (expected 1 argument).");
				
				return doubleNum;
			}catch (InputMismatchException e) {
				System.out.println("Invalid input.\n");
			} catch (InvalidInput e) {
				e.printStackTrace();
			}
		}
	}

	public static LocalDate getDate(String message) {
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			try {
				
				System.out.println(message);
				System.out.print("(Correct form : YYYY MM DD)\n");
				
				int y,m,d;
				y = scan.nextInt();
				m = scan.nextInt();
				d = scan.nextInt();
				
				if(scan.nextLine().length()>0) throw new InvalidInput("Entered too many arguments.");
			
				LocalDate localDate = LocalDate.of(y, m, d);
				return localDate;
			}catch (InvalidInput e) {
				e.printStackTrace();
			}catch (DateTimeException e) {
				System.err.println(e.getMessage());
			}catch (InputMismatchException e) {
				scan.nextLine();
				System.err.println("Invalid input.");
			}
		}
	}

	public static ClientType getClientType(String message) {
		ClientType clientType;
		
		String msg = "";
		for (ClientType type : ClientType.values()) {
			msg += type.ordinal() + " - " + type + "\n";
		}
		
		while(true) {
			try {
				System.out.println(message + "\n" +  msg);

				int typeID = getInt("Enter number that represent the client type.");
				clientType = ClientType.getClientTypeById(typeID);
				
				if(clientType==null) throw new InvalidInput("No client type exist for the number entered.");
				
				return clientType;
			}catch (InvalidInput e) {
				e.printStackTrace();
			}
		}
	}
	
}
