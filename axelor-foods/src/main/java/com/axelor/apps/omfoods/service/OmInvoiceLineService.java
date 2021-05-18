package com.axelor.apps.omfoods.service;

import com.axelor.apps.om.db.OmInvoiceLine;
import com.axelor.exception.AxelorException;

public interface OmInvoiceLineService {
  void setProductQty(OmInvoiceLine invoiceLine) throws AxelorException;
}
