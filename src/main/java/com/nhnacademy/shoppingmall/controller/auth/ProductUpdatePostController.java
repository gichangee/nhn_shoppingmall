package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/productupdateAction.do")
public class ProductUpdatePostController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        int productid = Integer.parseInt(req.getParameter("productId"));
        int categoryid = Integer.parseInt(req.getParameter("categoryId"));
        String modelnumber = req.getParameter("ModelNumber");
        String modelName = req.getParameter("ModelName");
        String productimg = req.getParameter("productimage");
        double cost = Double.parseDouble(req.getParameter("UnitCost"));
        String dec = req.getParameter("Description");


        Product a = new Product(productid,categoryid,modelnumber,modelName,productimg,cost,dec);
        productService.updateProduct(a);
        if (a != null) {

            return "redirect:/index.do";
        } else {
            req.setAttribute("errorMessage", "Invalid username or password");
            return "redirect:/index.do";
        }
    }
}
