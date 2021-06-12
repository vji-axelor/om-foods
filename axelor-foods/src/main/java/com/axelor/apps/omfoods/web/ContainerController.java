package com.axelor.apps.omfoods.web;

import com.axelor.apps.om.db.Container;
import com.axelor.apps.om.db.ContainerProductLine;
import com.axelor.exception.AxelorException;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;

@Singleton
public class ContainerController {
  public void calculateTotalCost(ActionRequest request, ActionResponse response)
      throws AxelorException {
    Container container = request.getContext().asType(Container.class);
    BigDecimal totalCost = new BigDecimal(0);
    BigDecimal prodCount = new BigDecimal(0);

    if (container.getProducts() == null) {
      response.setValue("totalProduct", prodCount);
      response.setValue("totalCost", totalCost);
      return;
    }

    List<ContainerProductLine> prodLines = container.getProducts();
    for (ContainerProductLine productLine : prodLines) {
      totalCost = totalCost.add(productLine.getTotalCost());
      prodCount = prodCount.add(new BigDecimal(1));
    }

    response.setValue("totalProduct", prodCount);
    response.setValue("totalCost", totalCost);
  }
}
