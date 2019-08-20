package com.seb.account.component.test.integration.service;


import com.lexicon.account.component.test.common.domain.OrderTestBuilder;
import com.so4it.test.category.IntegrationTest;
import com.so4it.test.common.Assert;
import com.so4it.test.common.probe.Poller;
import com.so4it.test.common.probe.SatisfiedWhenTrueReturned;
import com.so4it.test.gs.rule.ClearGigaSpaceTestRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.RuleChain;
import org.openspaces.core.GigaSpace;
import se.lexicon.account.component.domain.Order;
import se.lexicon.account.component.service.OrderComponentService;

import java.math.BigDecimal;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
@Category(IntegrationTest.class)
public class OrderComponentServiceIntegrationTest {

    @ClassRule
    public static final RuleChain SUITE_RULE_CHAIN = AccountComponentServiceIntegrationTestSuite.SUITE_RULE_CHAIN;

    @Rule
    public ClearGigaSpaceTestRule clearGigaSpaceTestRule = new ClearGigaSpaceTestRule(AccountComponentServiceIntegrationTestSuite.getExportContext().getBean(GigaSpace.class));

    @Test
    public void testOrderComponentServiceExists() throws Exception{
        OrderComponentService orderComponentService = AccountComponentServiceIntegrationTestSuite.getImportContext().getBean(OrderComponentService.class);
        Assert.assertNotNull(orderComponentService);

        Order order = OrderTestBuilder.builder().build();
        orderComponentService.placeOrder(order);
        Poller.pollAndCheck(SatisfiedWhenTrueReturned.create(() -> orderComponentService.getOrders(order.getSsn()).size() == 1));
    }

}
