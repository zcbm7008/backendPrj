package webshop.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@AutoConfigureMockMvc
@SpringBootTest
public class SellerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testListSellers() throws Exception {
        mockMvc.perform(get("/my/sellers"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/sellerList"))
                .andExpect(model().attributeExists("sellers"));
    }

    @Test
    @WithMockUser
    public void testNewSellers() throws Exception {
        mockMvc.perform(get("/my/sellers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/createSellerForm"));
    }
}
