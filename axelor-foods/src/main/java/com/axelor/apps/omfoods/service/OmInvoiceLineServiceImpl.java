package com.axelor.apps.omfoods.service;

import com.axelor.apps.om.db.OmInvoiceLine;
import com.axelor.apps.om.db.OmProduct;
import com.axelor.apps.om.db.repo.OmProductRepository;
import com.axelor.exception.AxelorException;
import com.axelor.inject.Beans;
import com.google.inject.persist.Transactional;
import java.math.BigDecimal;

public class OmInvoiceLineServiceImpl implements OmInvoiceLineService {

  @Override
  @Transactional
  public void setProductQty(OmInvoiceLine invoiceLine) throws AxelorException {

    OmProductRepository prodRepo = Beans.get(OmProductRepository.class);

    OmProduct product = invoiceLine.getProduct();
    if (product == null) {
      throw new AxelorException(0, "Please select the product on form.");
    }

    BigDecimal invoiceSingleQty = invoiceLine.getSingleQty();
    BigDecimal invoiceUnitQty = invoiceLine.getUnitQty();

    BigDecimal productUnitQty = product.getUnitstock();
    BigDecimal productSingleQty = product.getExtraSinglePiece();

    BigDecimal avlProductUnitQty = productUnitQty.subtract(invoiceUnitQty);
    BigDecimal avlProductSingleQty = productSingleQty.subtract(invoiceSingleQty);

    product.setUnitstock(avlProductUnitQty);
    product.setExtraSinglePiece(avlProductSingleQty);

    BigDecimal unitPiece = avlProductUnitQty.multiply(product.getUnitPiece());
    BigDecimal totalPieceQty = unitPiece.add(product.getExtraSinglePiece());
    product.setTotalPiece(totalPieceQty);
    prodRepo.save(product);
  }
}
