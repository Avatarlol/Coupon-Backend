package com.project.CouponMVC;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.context.ConfigurableApplicationContext;

import com.project.CouponMVC.entities.Company;
import com.project.CouponMVC.entities.Coupon;
import com.project.CouponMVC.entities.Customer;
import com.project.CouponMVC.enums.Category;
import com.project.CouponMVC.enums.ClientType;
import com.project.CouponMVC.exceptions.ConditionsNotMet;
import com.project.CouponMVC.exceptions.IncorrectCredentials;
import com.project.CouponMVC.facades.AdminFacade;
import com.project.CouponMVC.facades.CompanyFacade;
import com.project.CouponMVC.facades.CustomerFacade;
import com.project.CouponMVC.facades.LoginManager;
import com.project.CouponMVC.utils.ScanManager;

public class TestApp{

	private ConfigurableApplicationContext ctx;
	private LoginManager lm;
	private AdminFacade AdminF = null;
	private CompanyFacade CompanyF = null;
	private CustomerFacade CustomerF = null;

	public TestApp(ConfigurableApplicationContext ctx, LoginManager lm) {
		super();
		this.ctx = ctx;
		this.lm = lm;
	}

	
	public void login(String email, String password, ClientType clientType) {

		try {
			switch (clientType) {
			case ADMIN:
				AdminF = (AdminFacade) lm.login(email, password, clientType);
				CompanyF = null;
				CustomerF = null;
				break;
			case COMPANY:
				CompanyF = (CompanyFacade) lm.login(email, password, clientType);
				AdminF = null;
				CustomerF = null;
				break;
			case CUSTOMER:
				CustomerF = (CustomerFacade) lm.login(email, password, clientType);
				CompanyF = null;
				AdminF = null;
				break;
			}
			System.out.println("Successfully logged in as " + clientType);
		} catch (IncorrectCredentials e) {
			e.printStackTrace();
		}

	}

	public void logout() {

		if(AdminF==null && CompanyF == null && CustomerF == null) {
			System.out.println("You are not logged in.");
		}

	}

	// ADMIN FUNCTIONS

	public void addCustomer(String firstName, String lastName, String email, String password) {

		Customer customer = ctx.getBean(Customer.class);
		customer.setId(0);
		customer.setFirstname(firstName);
		customer.setLastname(lastName);
		customer.setEmail(email);
		customer.setPassword(password);

		try {
			AdminF.addCustomer(customer);
		} catch (ConditionsNotMet e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("you need to be logged-in as " + ClientType.ADMIN + " to do that.");
		}

	}

	public void addCompany(String name, String email, String password) {

		Company company = ctx.getBean(Company.class);
		company.setId(0);
		company.setName(name);
		company.setEmail(email);
		company.setPassword(password);

		try {
			AdminF.addCompany(company);
		} catch (ConditionsNotMet e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("you need to be logged-in as " + ClientType.ADMIN + " to do that.");
		}

	}

	public void deleteCustomer(int id) {
		try {
			AdminF.deleteCustomer(id);
		} catch (ConditionsNotMet e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("you need to be logged-in as " + ClientType.ADMIN + " to do that.");
		}
	}

	public void deleteCompany(int id) {
		try {
			AdminF.deleteCompany(id);
		} catch (ConditionsNotMet e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("you need to be logged-in as " + ClientType.ADMIN + " to do that.");
		}
	}

	// COMPANY FUNCTIONS

	public void addCoupon(String title, LocalDate endDate) {

		Random random = new Random();
		Coupon coupon = new Coupon();
		coupon.setId(0);
		coupon.setAmount(random.nextInt(8) + 2);
		coupon.setCategory(Category.getCategoryById(random.nextInt(4)));
		coupon.setDescription("Description");
		coupon.setTitle(title);
		coupon.setPrice(random.nextInt(201) + 50);
		coupon.setStartDate(LocalDate.now());
		coupon.setEndDate(endDate);
		coupon.setImage("image.png");
		coupon.setCompany(CompanyF.getCompany());

		try {
			CompanyF.addCoupon(coupon);
		} catch (ConditionsNotMet e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("you need to be logged-in as " + ClientType.COMPANY + " to do that.");
		}
	}

	public void deleteCoupon(int id) {

		try {
			CompanyF.deleteCoupon(id);
		} catch (ConditionsNotMet e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("you need to be logged-in as " + ClientType.COMPANY + " to do that.");
		}
	}

	public void printCompanyDetails() {
		System.out.println(CompanyF.getCompany());
	}

	public void printCoupons() {
		System.out.println(CompanyF.getAllCoupons());
	}

	public void printCouponsByCategory(Category category) {
		System.out.println(CompanyF.getAllCoupons(category));
	}

	public void printCouponsByPrice(double maxPrice) {
		System.out.println(CompanyF.getAllCoupons(maxPrice));
	}

	// CUSTOMER FUNCTIONS

	public void purchaseCoupon() {

		try {

			List<Coupon> allCoupons = CustomerF.getAllCoupons();
			System.out.println(allCoupons);

			int id = ScanManager.getInt("\n Enter the ID of the coupon you would like to purchase.");

			Coupon coupon = null;

			for (Coupon c : allCoupons) {
				if (c.getId() == id) {
					coupon = c;
					break;
				}
			}

			if (coupon == null) {
				System.err.println("no coupon with ID entered was found.");
				return;
			}

			CustomerF.purchaseCoupon(coupon);
		} catch (ConditionsNotMet e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("you need to be logged-in as " + ClientType.CUSTOMER + " to do that.");
		}

	}

	public void printOwnedCoupons() {
		try {
			System.out.println(CustomerF.getAllCoupons());
		} catch (NullPointerException e) {
			System.err.println("you need to be logged-in as " + ClientType.CUSTOMER + " to do that.");
		}
	}

	public void printOwnedCouponsByCategory(Category category) {
		try {
			System.out.println(CustomerF.getAllPurchasedCoupons(category));
		} catch (NullPointerException e) {
			System.err.println("you need to be logged-in as " + ClientType.CUSTOMER + " to do that.");
		}
	}
	
	public void printOwnedCouponsByPrice(double maxPrice) {
		try {
			System.out.println(CustomerF.getAllPurchasedCoupons(maxPrice));
		} catch (NullPointerException e) {
			System.err.println("you need to be logged-in as " + ClientType.CUSTOMER + " to do that.");
		}
	}
	
	public void printCustomerDetails() {
		try {
			System.out.println(CustomerF.getCustomer());
		} catch (NullPointerException e) {
			System.err.println("you need to be logged-in as " + ClientType.CUSTOMER + " to do that.");
		}
	}
	
	
}