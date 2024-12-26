package com.elandt.lil.spring_demo;

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.elandt.lil.spring_demo.adapter.AppleAdapter;
import com.elandt.lil.spring_demo.adapter.MoroOrange;
import com.elandt.lil.spring_demo.decorator.Pepperoni;
import com.elandt.lil.spring_demo.decorator.Sausage;
import com.elandt.lil.spring_demo.decorator.ThickCrustPizza;
import com.elandt.lil.spring_demo.prototype.NotPrototype;
import com.elandt.lil.spring_demo.prototype.Prototype;
import com.elandt.lil.spring_demo.repository.PresidentRepository;
import com.elandt.lil.spring_demo.singleton.SingletonA;
import com.elandt.lil.spring_demo.singleton.SingletonB;

@SpringBootTest
class SpringDemoApplicationTests {

	Logger logger = Logger.getLogger(getClass().getName());

	@Test
	void contextLoads() {
		// Default test ensuring application context loads.
	}

	@Autowired
	SingletonB singletonB1;

	@Autowired
	SingletonB singletonB2;

	@Test
	void testSingleton() {
		var singletonA1 = SingletonA.getInstance();
		var singletonA2 = SingletonA.getInstance();
		var singletonA3 = SingletonA.getInstanceCompliantV1();
		var singletonA4 = SingletonA.getInstanceCompliantV2();
		var singletonA5 = SingletonA.getInstanceCompliantV2();

		Assertions.assertNotNull(singletonA1);
		Assertions.assertNotNull(singletonB1);

		// Asserting that the course version of `getInstance` returns the exact same object
		Assertions.assertSame(singletonA1, singletonA2);
		// Asserting that `@Component` yields a Singleton in a more Spring-ified way
		Assertions.assertSame(singletonB1, singletonB2);

		// Asserting that the compliant version 1 `getInstance` yields the same object as the course version
		Assertions.assertSame(singletonA1, singletonA3);

		// Asserting that compliant version 2 does not yield the same object as either the
		// course `getInstance` or compliant option 1, i.e. option 1 and 2 are mutually exclusive approaches
		Assertions.assertNotSame(singletonA1, singletonA4);

		// Asserting that compliant option 2 does, in fact, produce a Singleton
		Assertions.assertSame(singletonA4, singletonA5);
	}

	@Autowired
	Prototype prototypeA;

	@Autowired
	Prototype prototypeB;

	@Autowired
	NotPrototype notPrototypeA;

	@Autowired
	NotPrototype notPrototypeB;

	@Test
	void testPrototype() {
		// NotPrototype is a typical Spring Singleton because it doesn't explicitly change the scope of the bean created
		Assertions.assertSame(notPrototypeA, notPrototypeB);
		// Scope is defined as `prototype` which deviates from the default Singleton behavior, hence it behaves as a prototype as defined
		Assertions.assertNotSame(prototypeA, prototypeB);
	}

	@Test
	void testAdapter() {
		var orange = new MoroOrange();
		var apple = new AppleAdapter(orange);

		logger.info(apple.getVariety());
		apple.eat();

		// Just to get rid of the warning on the test
		Assertions.assertEquals("Moro Blood Orange", apple.getVariety());
	}

	@Test
	void testDecorator() {
		var pizza = new ThickCrustPizza();

		Assertions.assertEquals(BigDecimal.valueOf(15.00), pizza.getCost());
		Assertions.assertEquals("Thick Crust Pizza", pizza.getDescription());

		var pepperoniPizza = new Pepperoni(pizza);

		Assertions.assertEquals(BigDecimal.valueOf(16.50), pepperoniPizza.getCost());
		Assertions.assertEquals("Thick Crust Pizza + pepperoni", pepperoniPizza.getDescription());

		var doublePepperoniPizza = new Pepperoni(pepperoniPizza);

		Assertions.assertEquals(BigDecimal.valueOf(18.00), doublePepperoniPizza.getCost());
		Assertions.assertEquals("Thick Crust Pizza + pepperoni + pepperoni", doublePepperoniPizza.getDescription());

		var sausagePizza = new Sausage(pizza);

		Assertions.assertEquals(BigDecimal.valueOf(16.00), sausagePizza.getCost());
		Assertions.assertEquals("Thick Crust Pizza + sausage", sausagePizza.getDescription());

		var comboPizza = new Sausage(pepperoniPizza);

		Assertions.assertEquals(BigDecimal.valueOf(17.50), comboPizza.getCost());
		Assertions.assertEquals("Thick Crust Pizza + pepperoni + sausage", comboPizza.getDescription());
	}

	@Autowired
	PresidentRepository presidentRepository;

	@Test
	void testRepository() {
		var maybeFirstPresident = this.presidentRepository.findById(1L);

		Assertions.assertTrue(maybeFirstPresident.isPresent());

		var firstPresident = maybeFirstPresident.get();
		Assertions.assertEquals("George", firstPresident.getFirstName());
		Assertions.assertEquals("Washington", firstPresident.getLastName());

		var barack = this.presidentRepository.findByEmailAddress("Barack.H.Obama@wh.gov");

		Assertions.assertEquals("Barack", barack.getFirstName());
		Assertions.assertEquals("Obama", barack.getLastName());
	}
}
