import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.order.query.dao.OrderSummaryDao;
import webshop.order.query.dto.OrderView;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class OrderSummaryDaoTest {
    @Autowired
    private OrderSummaryDao orderSummaryDao;

    @Test
    void findOrderView() {
        List<OrderView> result = orderSummaryDao.findOrderView("1");
    }

}
