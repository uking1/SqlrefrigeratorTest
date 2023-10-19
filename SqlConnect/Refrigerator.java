package ch16_sec02;

import java.util.Objects;

public class Refrigerator {
	
	private int refrigeratorId;
	private String productName;
	private String brand;
	private String year;
	private int price;
	
	
	public Refrigerator(int refrigeratorId, String productName, String brand, String year, int price) {
		super();
		this.refrigeratorId = refrigeratorId;
		this.productName = productName;
		this.brand = brand;
		this.year = year;
		this.price = price;
	}


	public int getRefrigeratorId() {
		return refrigeratorId;
	}


	public String getProductName() {
		return productName;
	}


	public String getBrand() {
		return brand;
	}


	public String getYear() {
		return year;
	}


	public int getPrice() {
		return price;
	}


	@Override
	public int hashCode() {
		return Objects.hash(refrigeratorId);
	}


	@Override
	public boolean equals(Object obj) {
		Refrigerator r = (Refrigerator) obj;
		if(!(obj instanceof Refrigerator))
			return false;
		r =(Refrigerator)obj;
		return r.refrigeratorId == this.refrigeratorId;
	}


	@Override
	public String toString() {
		return String.format("%3s \t %10s \t %10s \t %5s \t %6d \n",refrigeratorId,productName,brand,year,price);
	}


}
