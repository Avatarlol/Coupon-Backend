package com.project.CouponMVC;


import java.time.LocalDate;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.project.CouponMVC.entities.Company;
import com.project.CouponMVC.entities.Coupon;
import com.project.CouponMVC.entities.Customer;
import com.project.CouponMVC.enums.Category;
import com.project.CouponMVC.enums.ClientType;
import com.project.CouponMVC.exceptions.ConditionsNotMet;
import com.project.CouponMVC.exceptions.IncorrectCredentials;
import com.project.CouponMVC.facades.AdminFacade;
import com.project.CouponMVC.facades.ClientFacade;
import com.project.CouponMVC.facades.CompanyFacade;
import com.project.CouponMVC.facades.CustomerFacade;
import com.project.CouponMVC.facades.LoginManager;

@SpringBootApplication
@EnableScheduling
public class CouponSpringApplication {
	
	private static ConfigurableApplicationContext ctx;
	private static LoginManager lm;
	
	public static void main(String[] args) {
		ctx = SpringApplication.run(CouponSpringApplication.class, args);
		lm = ctx.getBean(LoginManager.class);
		TestApp test = new TestApp(ctx, lm);
		msg("START");
		
		//type tests with certain orders here through the test's offered functions
		
		test.login("Admin@admin.com", "admin", ClientType.ADMIN);
		test.addCompany("Company's name", "lol@gmail.com", "123");
		
		test.login("lol@gmail.com", "123", ClientType.COMPANY);
		test.addCoupon("1st coupon", LocalDate.now().plusDays(1));
		test.addCoupon("2nd expired coupon", LocalDate.now().minusDays(1));
		
		msg("END");
	}

	public static void msg(String str) {
		
		String stars = "******************************";
		stars += str.length()%2==0? "" : "*";
		String space = " ";
		for (int i = 1; i < (29-str.length())/2; i++) {
			space +=" ";
		}
		
		System.out.println(stars);
		System.out.println("*" + space + str + space + "*");
		System.out.println(stars);
	}
	
	public static void generate() {
		Random random = new Random();
		
		
		for (int i = 1; i <= 5; i++) {
			
			Company company = ctx.getBean(Company.class);
			Customer customer = ctx.getBean(Customer.class);
			Coupon coupon = ctx.getBean(Coupon.class);
			
			company.setId(0);
			company.setName("Company"+i);
			company.setEmail("company"+i+"@gmail.com");
			company.setPassword("123");
			
			customer.setId(0);
			customer.setFirstname("Firstname"+i);
			customer.setLastname("Lastname"+i);
			customer.setEmail("customer"+i+"@gmail.com");
			customer.setPassword("123");
			
			coupon.setId         (0);
			coupon.setAmount     (random.nextInt(8)+2);
			coupon.setCategory   (Category.getCategoryById(random.nextInt(4)));
			coupon.setDescription("Description"+i);
			coupon.setTitle      ("Title"+i);
			coupon.setPrice      (random.nextInt(500)+1);
			coupon.setStartDate  (LocalDate.of(2021, 11, random.nextInt(30)+1));
			coupon.setEndDate    (LocalDate.of(2022, 1, random.nextInt(30)+1));
			coupon.setImage      ("image.png");
			coupon.setCompany    (company);
			
			try 
			{
				{
				AdminFacade f = (AdminFacade)lm.login("Admin@admin.com", "admin", ClientType.ADMIN);
				f.addCompany(company);
				f.addCustomer(customer);
				}
				
				{
				CompanyFacade f = (CompanyFacade)lm.login("company"+i+"@gmail.com", "123", ClientType.COMPANY);
				f.addCoupon(coupon);
				}
				
				{
				CustomerFacade f = (CustomerFacade)lm.login("customer"+i+"@gmail.com", "123", ClientType.CUSTOMER);
				f.purchaseCoupon(coupon);
				}
			} catch (IncorrectCredentials | ConditionsNotMet e) {
				e.printStackTrace();
			}
			
		}
	
	}

}
