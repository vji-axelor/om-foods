package com.axelor.apps.omfoods.web;

import com.axelor.apps.om.db.OmInvoiceLine;
import com.axelor.apps.om.db.OmProduct;
import com.axelor.apps.omfoods.service.OmInvoiceLineService;
import com.axelor.exception.AxelorException;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Singleton;
import com.ibm.icu.math.BigDecimal;

@Singleton
public class OmInvoiceLineController {

  public void setProductQty(ActionRequest request, ActionResponse response) throws AxelorException {
    OmInvoiceLine invoiceLine = request.getContext().asType(OmInvoiceLine.class);

    OmProduct product = invoiceLine.getProduct();
    if (product == null) {
      response.setError("Please select Product");
      return;
    }

    BigDecimal totalLinePiece = new BigDecimal(invoiceLine.getTotalPiece());

    BigDecimal totalProdPiece = new BigDecimal(product.getTotalPiece());
    BigDecimal prodUnitQty = new BigDecimal(product.getUnitstock());

    if (totalLinePiece.compareTo(totalProdPiece) == 1) {
      response.setValue("totalPiece", new BigDecimal(0));
      response.setValue(
          "status",
          "This product has " + prodUnitQty + " Cartoon and/or " + totalProdPiece + " Piece.");
    }
  }

  public void getAvailableQTY(ActionRequest request, ActionResponse response)
      throws AxelorException {
    OmInvoiceLine invoiceLine = request.getContext().asType(OmInvoiceLine.class);
    OmProduct product = invoiceLine.getProduct();
    if (product == null) {
      response.setError("Please select Product");
      return;
    }
    BigDecimal prodPiece = new BigDecimal(product.getTotalPiece());
    BigDecimal prodUnitQty = new BigDecimal(product.getUnitstock());
    response.setValue(
        "status",
        "This product has " + prodUnitQty + " Cartoon and/or " + prodPiece + " Single Quntity.");
  }

  public void lessProductQty(ActionRequest request, ActionResponse response)
      throws AxelorException {
    OmInvoiceLine invoiceLine = request.getContext().asType(OmInvoiceLine.class);

    OmInvoiceLineService iLine = Beans.get(OmInvoiceLineService.class);
    iLine.setProductQty(invoiceLine);
    //	    response.setCanClose(true);
  }
}
