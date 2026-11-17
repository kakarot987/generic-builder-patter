package com.example.builder;

import com.example.builder.dto.PizzaRequest;
import com.example.builder.model.Pizza;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * PizzaController — exposes the Builder pattern via a REST API.
 *
 * Endpoints:
 *   POST /api/pizza/build  → build a custom pizza
 *   GET  /api/pizza/preset/{name} → get a pre-built preset pizza
 */
@RestController
@RequestMapping("/api/pizza")
@CrossOrigin(origins = "*")   // allow frontend (file:// or localhost) to call us
public class PizzaController {

    /**
     * Build a pizza from a JSON request body.
     * Delegates all construction to the Builder — controller stays clean.
     */
    @PostMapping("/build")
    public ResponseEntity<Pizza> buildPizza(@RequestBody PizzaRequest req) {

        Pizza.Builder builder = new Pizza.Builder(req.getSize(), req.getCheese());

        // Missing optional fields must not override the Builder's defaults.
        if (req.getCrust() != null) builder.crust(req.getCrust());
        if (req.getSauce() != null) builder.sauce(req.getSauce());

        if (req.getToppings() != null)
            builder.toppings(req.getToppings());

        if (req.isExtraCheese()) builder.extraCheese();
        if (req.isWellDone())    builder.wellDone();

        return ResponseEntity.ok(builder.build());
    }

    /**
     * Returns a preset pizza — demonstrates named presets via Builder.
     * GET /api/pizza/preset/margherita
     * GET /api/pizza/preset/bbqloaded
     * GET /api/pizza/preset/vegan
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<java.util.Map<String, String>> handleInvalidOrder(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(java.util.Map.of("error", ex.getMessage()));
    }

    @GetMapping("/preset/{name}")
    public ResponseEntity<Pizza> getPreset(@PathVariable String name) {

        Pizza pizza = switch (name.toLowerCase()) {
            case "margherita" -> new Pizza.Builder("Medium", "Mozzarella")
                    .crust("Thin")
                    .sauce("Tomato")
                    .toppings("Fresh Basil")
                    .build();

            case "bbqloaded" -> new Pizza.Builder("Large", "Cheddar")
                    .crust("Thick")
                    .sauce("BBQ")
                    .toppings("Pepperoni, Jalapeños, Onions")
                    .extraCheese()
                    .wellDone()
                    .build();

            case "vegan" -> new Pizza.Builder("Medium", "Vegan")
                    .crust("Thin")
                    .sauce("Pesto")
                    .toppings("Mushrooms, Olives, Peppers")
                    .build();

            default -> throw new IllegalArgumentException("Unknown preset: " + name);
        };

        return ResponseEntity.ok(pizza);
    }
}
