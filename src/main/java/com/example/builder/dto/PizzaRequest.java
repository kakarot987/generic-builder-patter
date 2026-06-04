package com.example.builder.dto;

/**
 * PizzaRequest — Data Transfer Object received from the frontend.
 * Maps directly to the JSON body of POST /api/pizza/build
 */
public class PizzaRequest {

    private String  size;
    private String  crust;
    private String  sauce;
    private String  cheese;
    private String  toppings;
    private boolean extraCheese;
    private boolean wellDone;

    // ── Getters & Setters ──────────────────────────────────────────────────

    public String getSize()                    { return size; }
    public void   setSize(String size)         { this.size = size; }

    public String getCrust()                   { return crust; }
    public void   setCrust(String crust)       { this.crust = crust; }

    public String getSauce()                   { return sauce; }
    public void   setSauce(String sauce)       { this.sauce = sauce; }

    public String getCheese()                  { return cheese; }
    public void   setCheese(String cheese)     { this.cheese = cheese; }

    public String getToppings()                { return toppings; }
    public void   setToppings(String toppings) { this.toppings = toppings; }

    public boolean isExtraCheese()                      { return extraCheese; }
    public void    setExtraCheese(boolean extraCheese)  { this.extraCheese = extraCheese; }

    public boolean isWellDone()                { return wellDone; }
    public void    setWellDone(boolean wellDone){ this.wellDone = wellDone; }
}
