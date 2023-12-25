package com.nhnacademy.shoppingmall.product.domain;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int ProductID;
    private int CategoryID;
    private String ModelNumber;
    private String ModelName;
    private String Productimage;
    private double UnitCost;
    private String Description;



    public Product(int productID, int categoryID, String modelNumber, String modelName, String productimage,
                   double unitCost, String Description) {
        ProductID = productID;
        CategoryID = categoryID;
        ModelNumber = modelNumber;
        ModelName = modelName;
        Productimage = productimage;
        UnitCost = unitCost;
        this.Description = Description;
    }

    public int getProductID() {
        return ProductID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return ProductID == product.ProductID && CategoryID == product.CategoryID &&
                Double.compare(UnitCost, product.UnitCost) == 0 &&
                Objects.equals(ModelNumber, product.ModelNumber) &&
                Objects.equals(ModelName, product.ModelName) &&
                Objects.equals(Productimage, product.Productimage) &&
                Objects.equals(Description, product.Description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ProductID, CategoryID, ModelNumber, ModelName, Productimage, UnitCost, Description);
    }

    public String getModelNumber() {
        return ModelNumber;
    }

    public String getModelName() {
        return ModelName;
    }

    public String getProductimage() {
        return Productimage;
    }

    public double getUnitCost() {
        return UnitCost;
    }

    public String getDescription() {
        return Description;
    }
}
