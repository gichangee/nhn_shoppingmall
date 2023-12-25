package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RequestMapping(method = RequestMapping.Method.POST, value = "/productinsertAction.do")

public class ProductInsertPostController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        int ProductID = Integer.parseInt(req.getParameter("ProductID"));
        int CategoryID = Integer.parseInt(req.getParameter("CategoryID"));
        String ModelNumber = req.getParameter("ModelNumber");
        String ModelName = req.getParameter("ModelName");
        double UnitCost = Double.parseDouble(req.getParameter("UnitCost"));
        String Description = req.getParameter("Description");
        Part filePart = null;
        try {
            filePart = req.getPart("ProductImage");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        String fileName = getSubmittedFileName(filePart);
        String tomcatWebappPath = req.getServletContext().getRealPath("/");
        String relativeImagePath = "resources/images";

        Path uploadPath = Paths.get(tomcatWebappPath, relativeImagePath, fileName);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, uploadPath, StandardCopyOption.REPLACE_EXISTING);
            //Files.copy(input, uploadPath2, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
        }

        String productImage = "/resources/images/" + fileName;

        Product product = new Product(ProductID, CategoryID, ModelNumber, ModelName, productImage, UnitCost, Description);

        productService.saveProduct(product);

        HttpSession session = req.getSession(true);
        session.setMaxInactiveInterval(60 * 60);
        session.setAttribute("product", product);

        if (product != null) {
            return "redirect:/index.do";
        } else {
            req.setAttribute("errorMessage", "Invalid product ... ");
            return "layout/admin";
        }
    }

    private String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }
}