package com.company;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MicroserviceTest extends TestcontainersPostgresInitializer {
    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(ctx)
                .alwaysExpect(status().isOk())
                .alwaysExpect(content().contentType("application/json;charset=UTF-8"))
                .build();
    }

    @Test
    public void shouldReturnEmptyJsonIfDbIsEmpty() throws Exception {
        mockMvc.perform(get("/hello/contacts?nameFilter=^a.*$"))
                .andExpect(jsonPath("$.contacts", hasSize(0)));
    }

    @Test
    @Sql("/populate_contacts.sql")
    @Transactional
    public void shouldNotReturnFilteredContact() throws Exception {
        mockMvc.perform(get("/hello/contacts?nameFilter=^a.*$"))
                .andExpect(jsonPath("$.contacts[0].name", is("bbb")))
                .andExpect(jsonPath("$.contacts", hasSize(1)));
    }

    @Test
    @Sql("/populate_contacts.sql")
    @Transactional
    public void shouldReturnAllContactsWhenFilterWithUnexistedContact() throws Exception {
        mockMvc.perform(get("/hello/contacts?nameFilter=^c.*$"))
                .andExpect(jsonPath("$.contacts[0].name", is("aaa")))
                .andExpect(jsonPath("$.contacts[1].name", is("bbb")))
                .andExpect(jsonPath("$.contacts", hasSize(2)));
    }
}





