package com.kaushik.bekal.springboottutorial.tests;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaushik.bekal.springboottutorial.dao.CustomerRepository;
import com.kaushik.bekal.springboottutorial.domain.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SpringBootTutorialApplicationTests {

	private JacksonTester<Customer> custJson;

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	CustomerRepository customerRepository;

	private MockMvc mockMvc;

	private final int CREATE_COUNT = 3;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
		
//		createCustomers();
//		System.out.println("In setup");
	}

	@Test
	public void testCustomerCreate() {
			try {
				
				for (int i = 1; i <= CREATE_COUNT; i++) {
					Customer customer = new Customer();
					customer.setFirstName("TestUserFN" + i);
					customer.setLastName("TestUserLN" + i);
					customer.setEmail("testuser@someemail.com");
					customer.setUsername("testusername" + i);
					customer.setPassword("testuserpwd" + i);
					
					this.mockMvc
					.perform(post("/customers")
					.contentType(MediaType.APPLICATION_JSON)
					.content(this.custJson.write(customer).getJson()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.firstName", is("TestUserFN" + i)))
					.andExpect(jsonPath("$.lastName", is("TestUserLN" + i)))
					.andExpect(jsonPath("$.email", is("testuser@someemail.com")))
					.andExpect(jsonPath("$.username", is("testusername" + i)))
					.andExpect(jsonPath("$.password", is("testuserpwd" + i)));
				}
				
				deleteCustomers();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Test
	public void testCustomerGet() {
		try {
			List<Long> ids = createCustomers();
			
			for (int i = 1; i <= CREATE_COUNT; i++) {
				this.mockMvc.perform(get("/customers/"+ids.get(i-1))
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.firstName", is("TestUserFN" + i)))
						.andExpect(jsonPath("$.lastName", is("TestUserLN" + i)))
						.andExpect(jsonPath("$.email", is("testuser@someemail.com")))
						.andExpect(jsonPath("$.username", is("testusername" + i)))
						.andExpect(jsonPath("$.password", is("testuserpwd" + i)));
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCustomerGetByEmail() {
		try {
			createCustomers();
			this.mockMvc.perform(get("/customers/email/testuser@someemail.com")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(CREATE_COUNT)))
					.andExpect(jsonPath("$[0].firstName", is("TestUserFN1")))
					.andExpect(jsonPath("$[0].lastName", is("TestUserLN1")))
					.andExpect(jsonPath("$[0].email", is("testuser@someemail.com")))
					.andExpect(jsonPath("$[0].username", is("testusername1")))
					.andExpect(jsonPath("$[0].password", is("testuserpwd1")));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCustomerGetAll() {
		try {
			createCustomers();
			this.mockMvc.perform(get("/customers").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(CREATE_COUNT)))
					.andExpect(jsonPath("$[1].firstName", is("TestUserFN2")))
					.andExpect(jsonPath("$[1].lastName", is("TestUserLN2")))
					.andExpect(jsonPath("$[1].email", is("testuser@someemail.com")))
					.andExpect(jsonPath("$[1].username", is("testusername2")))
					.andExpect(jsonPath("$[1].password", is("testuserpwd2")));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCustomerUpdate() {
		List<Long> ids = createCustomers();
		Customer customer = new Customer();
		customer.setFirstName("TestUserFNUpdated");
		customer.setLastName("TestUserLNUpdated");
		customer.setEmail("testuseupdatedr@someemail.com");
		customer.setUsername("testusernameupdated");
		customer.setPassword("testuserpwdupdated");
		try {
			this.mockMvc
					.perform(put("/customers/"+ids.get(0))
					.contentType(MediaType.APPLICATION_JSON)
					.content(this.custJson.write(customer).getJson()))
					.andExpect(status().isOk());
			
			this.mockMvc.perform(get("/customers/"+ids.get(0))
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.firstName", is("TestUserFNUpdated")))
					.andExpect(jsonPath("$.lastName", is("TestUserLNUpdated")))
					.andExpect(jsonPath("$.email", is("testuseupdatedr@someemail.com")))
					.andExpect(jsonPath("$.username", is("testusernameupdated")))
					.andExpect(jsonPath("$.password", is("testuserpwdupdated")));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCustomerCreateWithPut() {

		Customer customer = new Customer();
		customer.setFirstName("TestUserFNPutCreated");
		customer.setLastName("TestUserLNPutCreated");
		customer.setEmail("testuserputcreated@someemail.com");
		customer.setUsername("testusernameputcreated");
		customer.setPassword("testuserpwdputcreated");
		try {
			MvcResult result = this.mockMvc
					.perform(put("/customers/"+(CREATE_COUNT+1))
					.contentType(MediaType.APPLICATION_JSON)
					.content(this.custJson.write(customer).getJson()))
					.andExpect(status().isOk())
					.andReturn();
			
			Customer cust = this.custJson.parseObject(result.getResponse().getContentAsString());
			
			this.mockMvc.perform(get("/customers/"+cust.getId())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.firstName", is("TestUserFNPutCreated")))
					.andExpect(jsonPath("$.lastName", is("TestUserLNPutCreated")))
					.andExpect(jsonPath("$.email", is("testuserputcreated@someemail.com")))
					.andExpect(jsonPath("$.username", is("testusernameputcreated")))
					.andExpect(jsonPath("$.password", is("testuserpwdputcreated")));
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<Long> createCustomers() {
		List<Long> ids = new ArrayList<Long>();
		for (int i = 1; i <= CREATE_COUNT; i++) {
			Customer customer = new Customer();
			customer.setFirstName("TestUserFN" + i);
			customer.setLastName("TestUserLN" + i);
			customer.setEmail("testuser@someemail.com");
			customer.setUsername("testusername" + i);
			customer.setPassword("testuserpwd" + i);
			
			ids.add((customerRepository.save(customer)).getId());
		}
		return ids;
	}
	
	private void deleteCustomers() {
		for (int i = 1; i <= CREATE_COUNT; i++) {
			customerRepository.delete((long)i);
		}
	}
}
