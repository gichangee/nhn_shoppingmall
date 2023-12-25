package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {


    @Override
    public Optional<Product> findById(int productId) {
        String sql = "select * from Products where ProductID=?";
        ResultSet rs = null;
        try {
            Connection connection = DbConnectionThreadLocal.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setInt(1, productId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getInt("CategoryID"),
                        rs.getString("ModelNumber"),
                        rs.getString("ModelName"),
                        rs.getString("ProductImage"),
                        rs.getDouble("UnitCost"),
                        rs.getString("Description")
                );
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


    @Override
    public int save(Product product) {

        String sql = "insert into Products values(?,?,?,?,?,?,?)";
        try {
            Connection connection = DbConnectionThreadLocal.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setInt(1, product.getProductID());
            psmt.setInt(2, product.getCategoryID());
            psmt.setString(3, product.getModelNumber());
            psmt.setString(4, product.getModelName());
            psmt.setString(5, product.getProductimage());
            psmt.setDouble(6, product.getUnitCost());
            psmt.setString(7, product.getDescription());
            int result = psmt.executeUpdate();
            log.debug("save-result:{}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByProductId(int productId) {

        String sql = "delete from Products where ProductID=?";
        try {
            Connection connection = DbConnectionThreadLocal.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setInt(1, productId);

            int result = psmt.executeUpdate();
            log.debug("result:{}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(Product product) {
        String sql =
                "update Products set CategoryID=?, ModelNumber=?, ModelName=?, ProductImage=?, UnitCost=?, Description=? where ProductID=?";

        try {
            Connection connection = DbConnectionThreadLocal.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setInt(1, product.getCategoryID());
            psmt.setString(2, product.getModelNumber());
            psmt.setString(3, product.getModelName());
            psmt.setString(4, product.getProductimage());
            psmt.setDouble(5, product.getUnitCost());
            psmt.setString(6, product.getDescription());
            psmt.setInt(7, product.getProductID());
            int result = psmt.executeUpdate();
            log.debug("result:{}", result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByProductId(int productID) {

        int count = 0;
        String sql = "select * from Products where ProductID=?";
        ResultSet resultSet = null;
        try {
            Connection connection = DbConnectionThreadLocal.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setInt(1, productID);

            resultSet = psmt.executeQuery();

            if (resultSet.next()) {
                count++;
            }
            return count;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Integer> categorynaemList() {
        List<Integer> categoryname = new ArrayList<>();
        String sql = "select CategoryID from Categories";
        ResultSet resultSet = null;
        try {
            Connection connection = DbConnectionThreadLocal.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);

            resultSet = psmt.executeQuery();

            while (resultSet.next()) {
                categoryname.add(Integer.valueOf(resultSet.getString(1)));
            }
            return categoryname;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Product> productList() {
        List<Product> categoryname = new ArrayList<>();
        String sql =
                "select ProductID, CategoryID, ModelNumber, ModelName, ProductImage, UnitCost, Description from Products";
        ResultSet resultSet = null;
        try {
            Connection connection = DbConnectionThreadLocal.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);

            resultSet = psmt.executeQuery();

            while (resultSet.next()) {

                Product product = new Product(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getDouble(6), resultSet.getString(7));

                categoryname.add(product);
            }
            return categoryname;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Page<Product> findAll(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        ResultSet rs = null;
        ResultSet countResultSet = null;
        String sql =
                "select ProductID, CategoryID, ModelNumber, ModelName, ProductImage, UnitCost, Description from Products order by ProductID desc limit  ?,? ";
        String countSql = "select count(*) as total from Products";

        try {
            Connection connection = DbConnectionThreadLocal.getConnection();

            PreparedStatement countPsmt = connection.prepareStatement(countSql);
            countResultSet = countPsmt.executeQuery();
            countResultSet.next();
            long total = countResultSet.getLong("total");

            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);
            rs = psmt.executeQuery();

            List<Product> productList = new ArrayList<>(pageSize);

            while (rs.next()) {
                productList.add(
                        new Product(
                                rs.getInt("ProductID"),
                                rs.getInt("CategoryID"),
                                rs.getString("ModelNumber"),
                                rs.getString("ModelName"),
                                rs.getString("ProductImage"),
                                rs.getDouble("UnitCost"),
                                rs.getString("Description")
                        )
                );
            }


            return new Page<Product>(productList, total);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (Objects.nonNull(rs)) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}