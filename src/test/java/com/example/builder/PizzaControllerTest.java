package com.example.builder;

import com.example.builder.dto.PizzaRequest;
import com.example.builder.model.Pizza;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PizzaControllerTest {

    private final PizzaController controller = new PizzaController();
    private final MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

    @Test
    void omittedOptionalFieldsKeepBuilderDefaults() {
        PizzaRequest req = new PizzaRequest();
        req.setSize("Medium");
        req.setCheese("Mozzarella");

        Pizza pizza = controller.buildPizza(req).getBody();
        assertNotNull(pizza);
        assertEquals("Classic", pizza.getCrust());
        assertEquals("Tomato", pizza.getSauce());
        assertEquals("", pizza.getToppings());
    }

    @Test
    void explicitlyProvidedOptionsOverrideDefaults() {
        PizzaRequest req = new PizzaRequest();
        req.setSize("Large");
        req.setCheese("Cheddar");
        req.setCrust("Thin");
        req.setSauce("BBQ");

        Pizza pizza = controller.buildPizza(req).getBody();
        assertEquals("Thin", pizza.getCrust());
        assertEquals("BBQ", pizza.getSauce());
    }

    @Test
    void explicitBlankToppingsAreRejectedRatherThanSilentlyDiscarded() throws Exception {
        mvc.perform(post("/api/pizza/build")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"size\":\"Medium\",\"cheese\":\"Cheddar\",\"toppings\":\"  \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Toppings must not be blank"));
    }

    @Test
    void unknownPresetReturns400() throws Exception {
        mvc.perform(get("/api/pizza/preset/unknown"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Unknown preset: unknown"));
    }

    @Test
    void invalidRequestReturns400() throws Exception {
        mvc.perform(post("/api/pizza/build")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"size\":\"Medium\",\"cheese\":\"Cheddar\",\"crust\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}
