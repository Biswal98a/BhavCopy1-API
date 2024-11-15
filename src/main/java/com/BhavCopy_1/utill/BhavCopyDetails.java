package com.BhavCopy_1.utill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class BhavCopyDetails {
	private String enCodedBhavCopy;
    private String symbol;
    private String series;
    private double gain;
    private int number;
	public BhavCopyDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BhavCopyDetails(String enCodedBhavCopy, String symbol, String series, double gain, int number) {
		super();
		this.enCodedBhavCopy = enCodedBhavCopy;
		this.symbol = symbol;
		this.series = series;
		this.gain = gain;
		this.number = number;
	}
	public String getEnCodedBhavCopy() {
		return enCodedBhavCopy;
	}
	public void setEnCodedBhavCopy(String enCodedBhavCopy) {
		this.enCodedBhavCopy = enCodedBhavCopy;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public double getGain() {
		return gain;
	}
	public void setGain(double gain) {
		this.gain = gain;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "BhavCopyDetails [enCodedBhavCopy=" + enCodedBhavCopy + ", symbol=" + symbol + ", series=" + series
				+ ", gain=" + gain + ", number=" + number + "]";
	}
    
    

	
}
