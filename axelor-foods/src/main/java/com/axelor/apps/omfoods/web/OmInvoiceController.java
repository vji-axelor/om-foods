package com.axelor.apps.omfoods.web;

import com.axelor.apps.ReportFactory;
import com.axelor.apps.om.db.OmInvoice;
import com.axelor.apps.om.db.OmInvoiceLine;
import com.axelor.apps.om.db.OmProduct;
import com.axelor.apps.om.db.repo.OmInvoiceLineRepository;
import com.axelor.apps.om.db.repo.OmProductRepository;
import com.axelor.apps.report.engine.ReportSettings;
import com.axelor.exception.AxelorException;
import com.axelor.inject.Beans;
import com.axelor.meta.schema.actions.ActionView;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class OmInvoiceController {
  public void calculateTotal(ActionRequest request, ActionResponse response)
      throws AxelorException {
    OmInvoice invoice = request.getContext().asType(OmInvoice.class);
    BigDecimal totalCost = new BigDecimal(0);

    if (invoice.getProducts() == null) {
      response.setValue("invoiceAmount", totalCost);
      return;
    }

    List<OmInvoiceLine> invoiceLines = invoice.getProducts();
    for (OmInvoiceLine invoiceLine : invoiceLines) {
      totalCost = totalCost.add(invoiceLine.getTotalCost());
    }

    response.setValue("invoiceAmount", totalCost);
  }

  @Transactional
  public void removeProduct(ActionRequest request, ActionResponse response) throws AxelorException {
    OmInvoice invoice = request.getContext().asType(OmInvoice.class);
    OmInvoiceLineRepository invoiceLineRepo = Beans.get(OmInvoiceLineRepository.class);
    OmProductRepository prodRepo = Beans.get(OmProductRepository.class);

    List<OmInvoiceLine> invoiceLines = new ArrayList<OmInvoiceLine>();
    List<OmInvoiceLine> updatedInvoiceLines = new ArrayList<OmInvoiceLine>();
    invoiceLines = invoice.getProducts();

    for (OmInvoiceLine invoiceLine : invoiceLines) {
      if (invoiceLine.isSelected()) {
        OmProduct product = invoiceLine.getProduct();

        
        BigDecimal invoiceProdPiece = invoiceLine.getTotalPiece();
        BigDecimal invoiceProdCartoon = invoiceLine.getTotalCartoon();
        
        BigDecimal productPieceQty = product.getTotalPiece();
        BigDecimal productCartoonQty = product.getUnitstock();
        
        BigDecimal avlProductPieceQty = productPieceQty.add(invoiceProdPiece);
        BigDecimal avlProductCartoonQty = productCartoonQty.add(invoiceProdCartoon);
       
        product.setTotalPiece(avlProductPieceQty);
        product.setUnitstock(avlProductCartoonQty);
        
//        BigDecimal invoiceSingleQty = invoiceLine.getSingleQty();
//        BigDecimal invoiceUnitQty = invoiceLine.getUnitQty();
//
//        BigDecimal productUnitQty = product.getUnitstock();
//        BigDecimal productSingleQty = product.getExtraSinglePiece();
//
//        BigDecimal avlProductUnitQty = productUnitQty.add(invoiceUnitQty);
//        BigDecimal avlProductSingleQty = productSingleQty.add(invoiceSingleQty);
//
//        product.setUnitstock(avlProductUnitQty);
//        product.setExtraSinglePiece(avlProductSingleQty);

//        BigDecimal unitPiece = avlProductUnitQty.multiply(product.getUnitPiece());
//        BigDecimal totalPieceQty = unitPiece.add(product.getExtraSinglePiece());
//        product.setTotalPiece(totalPieceQty);
        
        prodRepo.save(product);
      } else {
        updatedInvoiceLines.add(invoiceLine);
      }
    }

    response.setValue("products", updatedInvoiceLines);
  }

  @Transactional
  public void lessProductQty(ActionRequest request, ActionResponse response)
      throws AxelorException {
    OmInvoice invoice = request.getContext().asType(OmInvoice.class);

    OmProductRepository prodRepo = Beans.get(OmProductRepository.class);

    List<OmInvoiceLine> invoiceLines = new ArrayList<OmInvoiceLine>();
    invoiceLines = invoice.getProducts();

    for (OmInvoiceLine invoiceLine : invoiceLines) {
      OmProduct product = invoiceLine.getProduct();

      
      BigDecimal invoiceProdPiece = invoiceLine.getTotalPiece();
      BigDecimal invoiceProdCartoon = invoiceLine.getTotalCartoon();
      
      BigDecimal productPieceQty = product.getTotalPiece();
      BigDecimal productCartoonQty = product.getUnitstock();
      
      BigDecimal avlProductPieceQty = productPieceQty.subtract(invoiceProdPiece);
      BigDecimal avlProductCartoonQty = productCartoonQty.subtract(invoiceProdCartoon);
     
      product.setTotalPiece(avlProductPieceQty);
      product.setUnitstock(avlProductCartoonQty);
      
      
//      
//      BigDecimal invoiceSingleQty = invoiceLine.getSingleQty();
//      BigDecimal invoiceUnitQty = invoiceLine.getUnitQty();
//
//      BigDecimal productUnitQty = product.getUnitstock();
//      BigDecimal productSingleQty = product.getExtraSinglePiece();
//
//      BigDecimal avlProductUnitQty = productUnitQty.subtract(invoiceUnitQty);
//      BigDecimal avlProductSingleQty = productSingleQty.subtract(invoiceSingleQty);
//   
//      product.setUnitstock(avlProductCartoonQty);
//      product.setExtraSinglePiece(avlProductSingleQty);

//      BigDecimal unitPiece = avlProductUnitQty.multiply(product.getUnitPiece());
//      BigDecimal totalPieceQty = unitPiece.add(product.getExtraSinglePiece());
//      product.setTotalPiece(totalPieceQty);
      
      prodRepo.save(product);
    }
  }

  @Transactional
  public void resetProduct(ActionRequest request, ActionResponse response) throws AxelorException {

    OmInvoice invoice = request.getContext().asType(OmInvoice.class);
    OmProductRepository prodRepo = Beans.get(OmProductRepository.class);

    List<OmInvoiceLine> invoiceLines = new ArrayList<OmInvoiceLine>();
    invoiceLines = invoice.getProducts();

    for (OmInvoiceLine invoiceLine : invoiceLines) {
      OmProduct product = invoiceLine.getProduct();

      BigDecimal invoiceProdPiece = invoiceLine.getTotalPiece();
      BigDecimal invoiceProdCartoon = invoiceLine.getTotalCartoon();
      
      BigDecimal productPieceQty = product.getTotalPiece();
      BigDecimal productCartoonQty = product.getUnitstock();
      
      BigDecimal avlProductPieceQty = productPieceQty.add(invoiceProdPiece);
      BigDecimal avlProductCartoonQty = productCartoonQty.add(invoiceProdCartoon);
     
      product.setTotalPiece(avlProductPieceQty);
      product.setUnitstock(avlProductCartoonQty);
      
      
      
      
//      BigDecimal invoiceSingleQty = invoiceLine.getSingleQty();
//      BigDecimal invoiceUnitQty = invoiceLine.getUnitQty();
//
//      BigDecimal productUnitQty = product.getUnitstock();
//      BigDecimal productSingleQty = product.getExtraSinglePiece();
//
//      BigDecimal avlProductUnitQty = productUnitQty.add(invoiceUnitQty);
//      BigDecimal avlProductSingleQty = productSingleQty.add(invoiceSingleQty);
//
//      product.setUnitstock(avlProductUnitQty);
//      product.setExtraSinglePiece(avlProductSingleQty);
//
//      BigDecimal unitPiece = avlProductUnitQty.multiply(product.getUnitPiece());
//      BigDecimal totalPieceQty = unitPiece.add(product.getExtraSinglePiece());
//      product.setTotalPiece(totalPieceQty);
      
      prodRepo.save(product);
    }

    response.setValue("products", null);
  }
  
  
  public void printReport(ActionRequest request, ActionResponse response) throws AxelorException {
	  OmInvoice invoice = request.getContext().asType(OmInvoice.class);
	  String fileLink =
	          ReportFactory.createReport("test.rptdesign", "title" + "-${date}")
	              .addParam("OmInvoiceId", invoice.getId())
	              .generate()
	              .getFileLink();

	      response.setView(ActionView.define("title").add("html", fileLink).map());
  }
  
}
