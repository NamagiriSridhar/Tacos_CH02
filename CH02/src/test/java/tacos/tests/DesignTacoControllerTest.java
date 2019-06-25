package tacos.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static 
org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import tacos.controllers.DesignTacoController;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.domain.Taco;

@RunWith(SpringRunner.class)
@WebMvcTest(DesignTacoController.class)
public class DesignTacoControllerTest 
{
	@Autowired
	private MockMvc mockMvc;
	
	private List <Ingredient> ingredients;
	private Taco taco;
	
	@Before//Methods annotated with the @Before annotation are executed before each test.
	  public void setup() 
	{
	    ingredients = Arrays.asList(
	      new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
	      new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
	      new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
	      new Ingredient("CARN", "Carnitas", Type.PROTEIN),
	      new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
	      new Ingredient("LETC", "Lettuce", Type.VEGGIES),
	      new Ingredient("CHED", "Cheddar", Type.CHEESE),
	      new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
	      new Ingredient("SLSA", "Salsa", Type.SAUCE),
	      new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
	    );
	    taco = new Taco();
	    taco.setName("Namagiri S");
	    taco.setIngredients(Arrays.asList("FLTO","GRBF","CHED"));
	}
	
	@Test
	public void testShowDesignForm() throws Exception //tests showDesignForm() function in DesignTacoController class
	{
	    mockMvc.perform(get("/design"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("designForm"))
	        .andExpect(model().attribute("wrap", ingredients.subList(0, 2))) //list of locations 0 & 1
	        .andExpect(model().attribute("protein", ingredients.subList(2, 4))) // locations 2 & 3
	        .andExpect(model().attribute("veggies", ingredients.subList(4, 6)))
	        .andExpect(model().attribute("cheese", ingredients.subList(6, 8)))
	        .andExpect(model().attribute("sauce", ingredients.subList(8, 10)));
	  }
	
	@Test
	  public void testProcessDesign() throws Exception { //function to test processDesign in designtacocontroller
	    mockMvc.perform(post("/design")
	        .content("name=Namagiri+S&ingredients=FLTO,GRBF,CHED")
	        .contentType(MediaType.APPLICATION_FORM_URLENCODED))// not sure what it means
	        .andExpect(status().is3xxRedirection())//not sure what it means
	        .andExpect(header().stringValues("Location", "/orders/current"));
	  }
}
