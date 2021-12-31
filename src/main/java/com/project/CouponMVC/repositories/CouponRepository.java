package com.project.CouponMVC.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.CouponMVC.entities.Coupon;
import com.project.CouponMVC.exceptions.ConditionsNotMet;
import com.project.CouponMVC.repositories.interfaces.CouponRepositoryInt;

public interface CouponRepository extends JpaRepository<Coupon, Integer>, CouponRepositoryInt{
	
	@Deprecated(since = "05-11-2021\n "
			 + "Reason: Different way of updating coupon ; update company that owns that to apply addition.")
	public default Coupon addCoupon(Coupon coupon) {
		if(!existsById(coupon.getId())) {
			Coupon addedCoupon = save(coupon);
			System.out.println("Coupon added.");
			return addedCoupon;
		}else {
			System.out.println("Can't add a coupon that already exists.");
			return coupon;
		}
	}
	
	public default Coupon updateCoupon(Coupon coupon) {
		if(!existsById(coupon.getId())) {
			System.out.println("Coupon doesn't exist in the database.");
			return coupon;
		}else {
			Coupon updatedCoupon = save(coupon);
			System.out.println("Coupon updated.");
			return updatedCoupon;
		}
	}
	
	@Deprecated(since = "05-11-2021\n "
			 + "Reason: Different way of updating coupon ; update company that owns that to apply deletion.")
	public default void deleteCoupon(int couponID) {
		if(existsById(couponID)) {
			deleteById(couponID);
			System.out.println("Coupon deleted.");
		}else {
			System.out.println("Coupon ID '"+couponID+"' doesn't exist in database.");
		}
	}
	
	public default Coupon getCoupon(int id) {
		if(existsById(id)) {
			return findById(id).get();
		}else {
			return null;
		}
	}
	
	public default List<Coupon> getAllCoupons() {
		List<Coupon> coupons = findAll();
		return coupons;
	}
	
	@Query(nativeQuery = true, value = "SELECT * FROM coupon_spring.customers_coupons JOIN coupon_spring.coupons "
			+ "WHERE (coupons_id = id AND coupons_id = :couponId AND customers_id = :customerId) ")
	public List<Coupon> findByIdAndCustomersId(@Param("couponId")int couponID, @Param("customerId")int customerID);

	
	// Booleans
	
	public boolean existsById(int id);
	
	public boolean existsByTitleAndCompanyId(String title, int companyID);
	
	public boolean existsByIdAndCompanyId(int id, int companyID);
	
	public boolean existsByIdAndAmountGreaterThan(int couponID, int amount);
	
	public boolean existsByIdAndEndDateAfter(int id, LocalDate date); // false == is expired
	
	public boolean existsByIdAndStartDateBefore(int id, LocalDate date); // false == hasn't launched yet
	
	public default boolean isCustomerOwnCoupon(int couponID, int customerID) {
		List<Coupon> coupons = findByIdAndCustomersId(couponID, customerID);
		if(coupons.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
	
	// Conditions
	
	public default boolean newCouponCondition(Coupon coupon, int companyID) throws ConditionsNotMet{
		
		boolean isCouponNotExist = !existsById(coupon.getId());
		
		if(!isCouponNotExist) throw new ConditionsNotMet("Coupon ID already exists.\n"); 
		
		boolean isTitleNotNull = coupon.getTitle()!=null;
		boolean isTitleAvailableForCompany = !existsByTitleAndCompanyId(coupon.getTitle(), companyID);
		boolean isStartAndEndDateCorrect = coupon.getEndDate().isAfter(coupon.getStartDate());
		
		if(isTitleAvailableForCompany && isStartAndEndDateCorrect && isTitleNotNull) {
			return true;
		}else {
			String msg = "";
			if(!isTitleAvailableForCompany) msg += "Company can't own more than 1 coupons with the same title.\\n";
			if(!isTitleNotNull) msg += "Title cannot be null.\n";
			if(!isStartAndEndDateCorrect) msg += "Expiration date can't be before start date.\n";
			throw new ConditionsNotMet(msg);
		}
	}
	
	public default boolean updateCouponCondition(Coupon coupon, int companyID) throws ConditionsNotMet {
		
		boolean isCouponExist = (coupon!=null && existsById(coupon.getId()));
		if(!isCouponExist) throw new ConditionsNotMet("Coupon ID doesn't exist.\n"); 
		
		Coupon beforeCoupon = getCoupon(coupon.getId());
		
		boolean isTitleNotNull = coupon.getTitle()!=null;
		boolean isTitleAvailableForCompany = true;
		boolean isStartAndEndDateCorrect = coupon.getEndDate().isAfter(coupon.getStartDate());
		boolean isCompanySame = existsByIdAndCompanyId(coupon.getId(), coupon.getCompany().getId());
		
			if(!(beforeCoupon.getTitle().equals(coupon.getTitle()))) {
				isTitleAvailableForCompany = !existsByTitleAndCompanyId(coupon.getTitle(), companyID);
			}
		
		if(isTitleAvailableForCompany && isStartAndEndDateCorrect && isCompanySame && isTitleNotNull) {
			return true;
		}else {
			String msg = "";
			if(!isTitleAvailableForCompany) msg += "Company can't own more than 1 coupons with the same title.\n";
			if(!isTitleNotNull) msg += "Title cannot be null.\n";
			if(!isStartAndEndDateCorrect) msg += "Expiration date can't be before start date.\n";
			if(!isCompanySame) msg += "Coupon cannot be moved to a different company.\n";
			throw new ConditionsNotMet(msg);
		}
	}

	public default boolean deleteCouponCondition(int couponID, int companyID) throws ConditionsNotMet {
		
		boolean isCouponExist =  existsById(couponID);
		if(!isCouponExist) throw new ConditionsNotMet("Coupon ID doesn't exist.\n"); 
		
		boolean isCouponBelongToCompany = existsByIdAndCompanyId(couponID, companyID);
		
		if(isCouponBelongToCompany) {
			return true;
		}else {
			String msg = "";
			if(!isCouponBelongToCompany) msg += "Company doesn't own this coupon.\n";
			throw new ConditionsNotMet(msg);
		}
	}
	
	public default boolean purchaseCouponCondition(Coupon coupon, int customerID) throws ConditionsNotMet {
		
		boolean	isCouponExist = (coupon!=null && existsById(coupon.getId()));
		if(!isCouponExist) throw new ConditionsNotMet("Coupon doesn't exist.\n");
		
		boolean isAmountNotZero = existsByIdAndAmountGreaterThan(coupon.getId(), 0);
		boolean isCouponNotExpired = existsByIdAndEndDateAfter(coupon.getId(), LocalDate.now());
		boolean isCouponLaunched = existsByIdAndStartDateBefore(coupon.getId(), LocalDate.now().plusDays(1));
		boolean isCustomerFirstCoupon = !isCustomerOwnCoupon(coupon.getId(), customerID);
		
		if(isAmountNotZero && isCouponNotExpired && isCouponLaunched && isCustomerFirstCoupon) {
			return true;
		}else {
			String msg = "";
			if(!isAmountNotZero) msg += "Coupon is out of stock.";
			if(!isCouponNotExpired) msg += "Coupon has expired.";
			if(!isCouponLaunched) msg += "Coupon hasn't launched yet.";
			if(!isCustomerFirstCoupon) msg += "Customer can't own 2 of the same coupon.";
			throw new ConditionsNotMet(msg);
		}
	}

	
	
}
