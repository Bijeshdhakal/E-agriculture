package com.bjshDkl.agriculture.analysis.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bjshDkl.agriculture.utils.Constants;

import java.io.Serializable;

@Entity(tableName = Constants.tableName)
public class CropDetailModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int day;

    @ColumnInfo(name = "income")
    private float income;

    @ColumnInfo(name = "expense")
    private float expense;

    @ColumnInfo(name = "producedGoods")
    private float producedGoods;

    @ColumnInfo(name = "soldGoods")
    private float soldGoods;

    @ColumnInfo(name = "profit")
    private float profit;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public float getExpense() {
        return expense;
    }

    public void setExpense(float expense) {
        this.expense = expense;
    }

    public float getProducedGoods() {
        return producedGoods;
    }

    public void setProducedGoods(float producedGoods) {
        this.producedGoods = producedGoods;
    }

    public float getSoldGoods() {
        return soldGoods;
    }

    public void setSoldGoods(float soldGoods) {
        this.soldGoods = soldGoods;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }
}
