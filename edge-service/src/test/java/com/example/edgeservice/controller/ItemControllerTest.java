package com.example.edgeservice.controller;

import com.example.edgeservice.client.ItemClient;
import com.example.edgeservice.dto.ItemDTO;
import com.example.edgeservice.dto.ProductDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.Resources;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;
    @Mock
    private ItemClient itemClient;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    public void topItems() throws Exception {
        //Given
        when(itemClient.readItems()).thenReturn(items());
        //When
        MockHttpServletResponse response = mockMvc.perform(get("/top-brands"))
                .andReturn().getResponse();

        System.out.println("topItems: " + response.getContentAsString());

        //Verify
        verify(itemClient, times(1)).readItems();
    }

    @Test
    public void topProducts() throws Exception {
        //Given
        when(itemClient.readProducts()).thenReturn(products());
        //When
        MockHttpServletResponse response = mockMvc.perform(get("/top-products"))
                .andReturn().getResponse();

        System.out.println("topProducts: " + response.getContentAsString());

        //Verify
        verify(itemClient, times(1)).readProducts();
    }

    @Test
    public void topProducts_fallback() throws Exception {
        //Given
        when(itemClient.readProducts()).thenReturn(products());
        //When
        MockHttpServletResponse response = mockMvc.perform(get("/top-productsz"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();
        //Verify
        System.out.println("topProducts_fallback: " + response.getContentAsString());
        verify(itemClient, times(0)).readProducts();
    }

    private Resources<ItemDTO> items(){
        List<ItemDTO> dtos = new ArrayList<ItemDTO>(){{
            add(new ItemDTO("blabla"));
        }};
        return new Resources<>(dtos);
    }
    private Resources<ProductDTO> products(){
        List<ProductDTO> dtos = new ArrayList<ProductDTO>(){{
            add(new ProductDTO("blabla"));
        }};
        return new Resources<>(dtos);
    }
}