package webshop.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import webshop.catalog.command.domain.product.Artwork;
import webshop.catalog.command.domain.product.Item;
import webshop.catalog.query.product.ItemService;
import webshop.user.domain.seller.Seller;
import webshop.user.domain.seller.SellerService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@AutoConfigureMockMvc
@SpringBootTest
public class SellerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SellerService sellerService;
    @MockBean
    private ItemService itemService;
    @Mock
    Seller seller;

    @Captor
    private ArgumentCaptor<Item> artworkCaptor;

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

    @Test
    @WithMockUser
    public void testSellerCreateNewItem() throws Exception {

        given(sellerService.findById(anyLong())).willReturn(seller);

        mockMvc.perform(post("/my/sellers/{sellerId}/items/new",seller.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Artwork Name")
                .param("price", "100")
                .param("content", "Artwork Description")
                .with(csrf()))
                .andExpect(status().is3xxRedirection()); // 예상되는 리디렉션 또는 다른 상태 코드

        verify(itemService).saveItem(artworkCaptor.capture());
        Artwork capturedArtwork = (Artwork) artworkCaptor.getValue();

        // 캡처된 Artwork 객체의 속성 검증
        assertEquals("Artwork Name", capturedArtwork.getName());
        assertEquals(100, capturedArtwork.getPrice().getValue());
        assertEquals("Artwork Description", capturedArtwork.getContent());

    }

    @Test
    @WithMockUser
    public void testSellerItems() throws Exception {
        Artwork artwork1 = new Artwork();
        Artwork artwork2 = new Artwork();
        seller.addSellerItem(artwork1);
        seller.addSellerItem(artwork2);

        given(sellerService.findById(anyLong())).willReturn(seller);

        mockMvc.perform(get("/my/sellers/{sellerId}/items", seller.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("sellerItems"))
                .andExpect(view().name("member/SellerItemList"));
    }
}
