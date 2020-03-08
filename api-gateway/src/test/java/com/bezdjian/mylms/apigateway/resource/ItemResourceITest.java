package com.bezdjian.mylms.apigateway.resource;

import com.bezdjian.mylms.apigateway.client.ItemServiceClient;
import com.bezdjian.mylms.apigateway.dto.ItemDTO;
import com.bezdjian.mylms.apigateway.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Resources;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ItemResourceITest {

  private static final String BASE_PATH = "/api/items";

  @MockBean
  private ItemServiceClient itemClient;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void topItems() throws Exception {
    //Given
    when(itemClient.readItems()).thenReturn(items());
    //When
    mockMvc.perform(get(BASE_PATH + "/brands"))
      .andExpect(status().isOk())
      .andDo(print());

    //Verify
    verify(itemClient).readItems();
  }

  @Test
  void topProducts() throws Exception {
    //Given
    when(itemClient.readProducts()).thenReturn(products());
    //When
    mockMvc.perform(get(BASE_PATH + "/products"))
      .andExpect(status().isOk())
      .andDo(print());

    //Verify
    verify(itemClient).readProducts();
  }

  @Test
  void testTopProductsFallback() throws Exception {
    //Given
    when(itemClient.readProducts()).thenReturn(products());
    //When
    mockMvc.perform(get(BASE_PATH + "/productsz"))
      .andExpect(status().isNotFound())
      .andDo(print());
    //Verify
    verify(itemClient, times(0)).readProducts();
  }

  private Resources<ItemDTO> items() {
    List<ItemDTO> dtos = new ArrayList<>() {{
      add(new ItemDTO("das item"));
    }};
    return new Resources<>(dtos);
  }

  private Resources<ProductDTO> products() {
    List<ProductDTO> dtos = new ArrayList<>() {{
      add(new ProductDTO("das produkt"));
    }};
    return new Resources<>(dtos);
  }
}