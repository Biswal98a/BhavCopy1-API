package com.BhavCopy_1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="bhav_copy")
public class BhavCopy {
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private int id;
    private String symbol;
    private String series;
    private String date1;
    private double prev_close;
    private double open_price;
    private double high_price;
    private double low_price;
    private double last_price;
    private double close_price;
    private double avg_price;
    private double ttl_trd_qnty;
    private double turnover_lacs;
    private double no_of_trades;
    private double deliv_qty;
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public double getPrev_close() {
		return prev_close;
	}

	public void setPrev_close(double prev_close) {
		this.prev_close = prev_close;
	}

	public double getOpen_price() {
		return open_price;
	}

	public void setOpen_price(double open_price) {
		this.open_price = open_price;
	}

	public double getHigh_price() {
		return high_price;
	}

	public void setHigh_price(double high_price) {
		this.high_price = high_price;
	}

	public double getLow_price() {
		return low_price;
	}

	public void setLow_price(double low_price) {
		this.low_price = low_price;
	}

	public double getLast_price() {
		return last_price;
	}

	public void setLast_price(double last_price) {
		this.last_price = last_price;
	}

	public double getClose_price() {
		return close_price;
	}

	public void setClose_price(double close_price) {
		this.close_price = close_price;
	}

	public double getAvg_price() {
		return avg_price;
	}

	public void setAvg_price(double avg_price) {
		this.avg_price = avg_price;
	}

	public double getTtl_trd_qnty() {
		return ttl_trd_qnty;
	}

	public void setTtl_trd_qnty(double ttl_trd_qnty) {
		this.ttl_trd_qnty = ttl_trd_qnty;
	}

	public double getTurnover_lacs() {
		return turnover_lacs;
	}

	public void setTurnover_lacs(double turnover_lacs) {
		this.turnover_lacs = turnover_lacs;
	}

	public double getNo_of_trades() {
		return no_of_trades;
	}

	public void setNo_of_trades(double no_of_trades) {
		this.no_of_trades = no_of_trades;
	}

	public double getDeliv_qty() {
		return deliv_qty;
	}

	public void setDeliv_qty(double deliv_qty) {
		this.deliv_qty = deliv_qty;
	}

	public double getDeliv_per() {
		return deliv_per;
	}

	public void setDeliv_per(double deliv_per) {
		this.deliv_per = deliv_per;
	}

	private double deliv_per;

    public BhavCopy() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BhavCopy(String symbol, String series, String date1, double prev_close, double open_price, double high_price, double low_price, double last_price, double close_price, double avg_price, double ttl_trd_qnty, double turnover_lacs, double no_of_trades, double deliv_qty, double deliv_per) {
        this.symbol = symbol;
        this.series = series;
        this.date1 = date1;
        this.prev_close = prev_close;
        this.open_price = open_price;
        this.high_price = high_price;
        this.low_price = low_price;
        this.last_price = last_price;
        this.close_price = close_price;
        this.avg_price = avg_price;
        this.ttl_trd_qnty = ttl_trd_qnty;
        this.turnover_lacs = turnover_lacs;
        this.no_of_trades = no_of_trades;
        this.deliv_qty = deliv_qty;
        this.deliv_per = deliv_per;
    }
	

}
