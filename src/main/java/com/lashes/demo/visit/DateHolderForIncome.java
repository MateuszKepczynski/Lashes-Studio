package com.lashes.demo.visit;

import java.sql.Date;

public class DateHolderForIncome
{
    Date date;

    int tempMonthForIncome;

    String tempYearForIncome;

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getTempMonthForIncome()
    {
        return tempMonthForIncome;
    }

    public void setTempMonthForIncome(int tempMonthForIncome)
    {
        this.tempMonthForIncome = tempMonthForIncome;
    }

    public String getTempYearForIncome()
    {
        return tempYearForIncome;
    }

    public void setTempYearForIncome(String tempYearForIncome)
    {
        this.tempYearForIncome = tempYearForIncome;
    }
}
