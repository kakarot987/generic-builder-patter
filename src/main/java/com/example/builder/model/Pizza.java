package com.example.builder.model;

/**
 * Pizza — the Product in the Builder pattern.
 * Immutable: fields are final, only Builder can construct it.
 */
public class Pizza {

    private final String size;
    private final String crust;
    private final String sauce;
    private final String cheese;
    private final String toppings;
    private final boolean extraCheese;
    private final boolean wellDone;

    // Private constructor — only accessible via Builder
    private Pizza(Builder builder) {
        this.size        = builder.size;
        this.crust       = builder.crust;
        this.sauce       = builder.sauce;
        this.cheese      = builder.cheese;
        this.toppings    = builder.toppings;
        this.extraCheese = builder.extraCheese;
        this.wellDone    = builder.wellDone;
    }

    // ── Getters (no setters — immutable) ───────────────────────────────────

    public String getSize()         { return size; }
    public String getCrust()        { return crust; }
    public String getSauce()        { return sauce; }
    public String getCheese()       { return cheese; }
    public String getToppings()     { return toppings; }
    public boolean isExtraCheese()  { return extraCheese; }
    public boolean isWellDone()     { return wellDone; }

    // ── Static inner Builder ────────────────────────────────────────────────

    public static class Builder {

        // Required fields
        private final String size;
        private final String cheese;

        // Optional fields with sensible defaults
        private String  crust       = "Classic";
        private String  sauce       = "Tomato";
        private String  toppings    = "";
        private boolean extraCheese = false;
        private boolean wellDone    = false;

        /**
         * Builder constructor enforces required fields.
         * Everything else is optional and has a default.
         */
        public Builder(String size, String cheese) {
            if (size == null || size.isBlank())
                throw new IllegalArgumentException("Size is required");
            if (cheese == null || cheese.isBlank())
                throw new IllegalArgumentException("Cheese is required");
            this.size   = size.trim();
            this.cheese = cheese.trim();
        }

        public Builder crust(String crust) {
            if (crust == null || crust.isBlank())
                throw new IllegalArgumentException("Crust must not be blank");
            this.crust = crust;
            return this;          // ← fluent chaining
        }

        public Builder sauce(String sauce) {
            if (sauce == null || sauce.isBlank())
                throw new IllegalArgumentException("Sauce must not be blank");
            this.sauce = sauce;
            return this;
        }

        public Builder toppings(String toppings) {
            if (toppings == null || toppings.isBlank())
                throw new IllegalArgumentException("Toppings must not be blank");
            this.toppings = toppings.trim();
            return this;
        }

        public Builder extraCheese() {
            this.extraCheese = true;
            return this;
        }

        public Builder wellDone() {
            this.wellDone = true;
            return this;
        }

        /**
         * Terminal method — validates and constructs the immutable Pizza.
         */
        public Pizza build() {
            return new Pizza(this);
        }
    }
}
