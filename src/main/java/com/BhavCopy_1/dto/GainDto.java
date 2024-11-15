package com.BhavCopy_1.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GainDto {
	   private String symbol;
	    private String open_price;
	    private String close_price;
		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
		public String getOpen_price() {
			return open_price;
		}
		public void setOpen_price(String open_price) {
			this.open_price = open_price;
		}
		public String getClose_price() {
			return close_price;
		}
		public void setClose_price(String close_price) {
			this.close_price = close_price;
		}
	    

}
