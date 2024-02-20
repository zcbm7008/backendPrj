package webshop.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import webshop.catalog.command.domain.product.Artwork;
import webshop.catalog.query.product.ItemService;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@AutoConfigureMockMvc
@SpringBootTest
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;

    @Mock
    Artwork artwork;

    @Test
    @WithMockUser
    public void testItemDetail() throws Exception{
       given(itemService.findOne(anyLong())).willReturn(Optional.of(artwork));

        mockMvc.perform(get("/items/{itemId}", artwork.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("item"))
                .andExpect(view().name("category/itemDetail"));

    }


}
