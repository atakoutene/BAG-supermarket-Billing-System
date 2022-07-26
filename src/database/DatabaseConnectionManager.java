package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Product2;
import models.Profile;
import models.Admin;
import models.Cashier;
import models.Category;
import models.Customer;
import models.Product;

/**
 *
 * @author Pacha
 */
public class DatabaseConnectionManager {
// CONVERT_TO_NULL
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/supermarket_billing_system?zeroDateTimeBehavior=convertToNull";
                                                                
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";

    private Connection connection;

    public DatabaseConnectionManager() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnectionManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
//Create a Customer object
    public Customer checkCustomerLogin(Customer user) {
        try {
            Statement myStatement = connection.createStatement();
            ResultSet myResultSet = myStatement.executeQuery(
                    "SELECT * FROM customer WHERE cus_id='" + user.getRefNumber()
                    + "' AND cus_pwd=SHA1('" + user.getPwd() + "') ;");
            if (myResultSet.next()) {
                Customer newCustomer = new Customer(
                        myResultSet.getString("cus_id"),
                        myResultSet.getString("cus_fname"),
                        myResultSet.getString("cus_lname"),
                        myResultSet.getString("cus_email"),
                        myResultSet.getInt("cus_phone"),
                        myResultSet.getInt("cus_qty_purchased")
                );

                myResultSet.close();
                myStatement.close();

                return newCustomer;
            }
        } catch (SQLException e) {
            System.out.println("CUSTOMER MANAGER ERROR: "
                    + e.getMessage());
        }

        return null;
    }
    
//Create a Cashier object
    public Cashier checkCashierLogin(Cashier user) {
        try {
            Statement myStatement = connection.createStatement();
            ResultSet myResultSet = myStatement.executeQuery(
                    "SELECT * FROM cashier WHERE cas_id='" + user.getId()
                    + "' AND cas_pwd=SHA1('" + user.getPwd() + "') ;");
            if (myResultSet.next()) {
                Cashier newCashier = new Cashier(
                        myResultSet.getString("cas_id"),
                        myResultSet.getInt("cas_qty_registered"),
                        myResultSet.getString("cas_pwd")
                );

                myResultSet.close();
                myStatement.close();

                return newCashier;
            }
        } catch (SQLException e) {
            System.out.println("CASHIER MANAGER ERROR: "
                    + e.getMessage());
        }

        return null;
    }
    
//Create an admin object
    public Admin checkAdminLogin(Admin user) {
        try {
            Statement myStatement = connection.createStatement();
            ResultSet myResultSet = myStatement.executeQuery(
                    "SELECT * FROM admin WHERE ad_id='" + user.getId()
                    + "' AND ad_pwd=SHA1('" + user.getPwd() + "') ;");
            if (myResultSet.next()) {
                Admin newAdmin = new Admin(
                        myResultSet.getString("ad_id"),
                        myResultSet.getString("ad_fname"),
                        myResultSet.getString("ad_lname"),
                        myResultSet.getString("ad_pwd")
                );

                myResultSet.close();
                myStatement.close();

                return newAdmin;
            }
        } catch (SQLException e) {
            System.out.println("ADMIN MANAGER ERROR: "
                    + e.getMessage());
        }

        return null;
    }    
    
    //return and array list of  all Category objects in the database
    public ArrayList<Category> getCategories(){
        ArrayList<Category> categories = new ArrayList<>();
        
        String query = "SELECT * FROM `category`";


        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
              Category newCategory = new Category(rs.getInt("cat_id"),rs.getString("cat_name"),rs.getString("cat_description"));
              categories.add(newCategory);
                
            }
            
            return categories;

        } catch (SQLException e) {
            System.out.println(
                    "GET CATEGORIES ERROR: "
                    + e.getMessage());
        }


        
        return null;
    }
    
    //return and array list of  all product objects in the database
    public ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();
        
        String query = "SELECT * FROM `product`";


        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
              Product newProduct = new Product(rs.getString("pro_id"),rs.getString("pro_name"),rs.getFloat("pro_unit_price"),rs.getInt("pro_qty_in_stock"), rs.getInt("pro_qty_sold"),rs.getString("cat_name"));
              products.add(newProduct);
                
            }
            
            return products;

        } catch (SQLException e) {
            System.out.println(
                    "GET CATEGORIES ERROR: "
                    + e.getMessage());
        }


        
        return null;
    }    
    
    //return and array list of  all customers objects in the database
    public ArrayList<Customer> getCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();
        
        String query = "SELECT * FROM `customer`";


        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
              Customer newCustomer = new Customer(rs.getString("cus_id"),
                      rs.getString("cus_fname"),
                      rs.getString("cus_lname"),
                      rs.getString("cus_email"), 
                      rs.getInt("cus_phone"),
                      rs.getInt("cus_qty_purchased"),
                      rs.getString("cus_pwd"));
              customers.add(newCustomer);
                
            }
            
            return customers;

        } catch (SQLException e) {
            System.out.println(
                    "GET CUSTOMERS ERROR: "
                    + e.getMessage());
        }


        
        return null;
    }     
    
    //return and array list of  all customers objects in the database
    public ArrayList<Cashier> getCashiers(){
        ArrayList<Cashier> cashiers = new ArrayList<>();
        
        String query = "SELECT * FROM `cashier`";


        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
              Cashier newCashier = new Cashier(
                      rs.getString("cas_id"),
                      rs.getInt("cas_qty_registered"),
                      rs.getString("cas_pwd"));
              cashiers.add(newCashier);
                
            }
            
            return cashiers;

        } catch (SQLException e) {
            System.out.println(
                    "GET CASHIERS ERROR: "
                    + e.getMessage());
        }


        
        return null;
    }    
    
    
   //update the information of a category
   public void postCategoryInfo(Category category) {
      
        String query = "UPDATE `category` " +
                       "SET " +
                       "`cat_id`= ? ," +
                       "`cat_name`= ? ," +
                       "`cat_description`= ? " +
                       "WHERE `cat_id` = ?;";
    //UPDATE `person` SET `per_pseudo`=  "Koodjo" WHERE person.per_id = (SELECT student.per_id FROM student WHERE student.stud_id = "21S034");    
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, category.getId());
            ps.setString(2, category.getName());
            ps.setString(3, category.getDescription());
            ps.setInt(4, category.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SET CATEGORY INFO ERRORS: "
                    + e.getMessage());
        }
        System.out.println(category.getDescription() + " " + category.getId() + " " + category.getName());
    }     
   
   //update the information of a product
   public void postProductInfo(Product product) {
      
        String query = "UPDATE `product` " +
                       "SET " +
                       "`pro_id`= ? ," +
                       "`pro_name`= ? ," +
                       "`pro_unit_price`= ? , " +
                       "`pro_qty_in_stock`= ?," +
                       "`cat_name`= ?" +
                       "WHERE `pro_id` = ?;";    
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, product.getId());
            ps.setString(2, product.getName());
            ps.setFloat(3, product.getUnitPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getCategory());
            ps.setString(6, product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SET PRODUCT INFO ERRORS: "
                    + e.getMessage());
        }
        
        System.out.println(product.getName() + " " + product.getQuantity() + " Id: " + product.getId());
    }    
   
   //Add a product into the database
   public void putProduct(Product product){
        String query = "INSERT " +
                       "INTO " +
                       "`product`(`pro_id`, `pro_name`, `pro_unit_price`, `pro_qty_in_stock`,  `cat_name`) " +
                       "VALUES (?,?,?,?,?);";    
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, product.getId());
            ps.setString(2, product.getName());
            ps.setFloat(3, product.getUnitPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getCategory());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("INSERT PRODUCT INFO ERRORS: "
                    + e.getMessage());
        }
        
        System.out.println("INSERT PRODUCT: " + product.getName() + " " + product.getQuantity() + " Id: " + product.getId());       
   }
   
   //Add a category into the database
   public void putCategory(Category category){
        String query = "INSERT " +
                       "INTO " +
                       "`category`(`cat_id`, `cat_name`, `cat_description`) " +
                       "VALUES (?,?,?);";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, category.getId());
            ps.setString(2, category.getName());
            ps.setString(3, category.getDescription());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("INSERT CATEGORY INFO ERRORS: "
                    + e.getMessage());
        }
        System.out.println("INSERT CATEGORY: " + category.getDescription() + " " + category.getId() + " " + category.getName());       
   }
   
   //Add a customer into the database
   public void putCustomer(Customer customer){
        String query = "INSERT " +
                       "INTO " +
                       "`customer`(`cus_id`, `cus_fname`, `cus_lname`, " +
                       "`cus_email`, `cus_phone`, `cus_dob`, `cus_pwd`) " +
                       "VALUES (?,?,?,?,?,?,SHA1(?));";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, customer.getRefNumber());
            ps.setString(2, customer.getFname());
            ps.setString(3, customer.getLname());
            ps.setString(4, customer.getEmail());
            ps.setInt(5, customer.getPhoneNumber());            
            ps.setDate(6, customer.getDob());            
            ps.setString(7, customer.getPwd());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("INSERT CUSTOMER INFO ERRORS: "
                    + e.getMessage());
        }
        System.out.println("INSERT CUSTOMER: " + customer.getRefNumber() + " " + customer.getFname() + " " + customer.getLname());       
   }
   
   //Delete a category from the database
   public void deleteCategory(Category category){
        String query = "DELETE FROM `category` WHERE `cat_id` = ?;";     
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, category.getId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("DELETE CATEGORY INFO ERRORS: "
                    + e.getMessage());
        }
        System.out.println("DELETE CATEGORY: " + category.getDescription() + " " + category.getId() + " " + category.getName());       
   }
   
   //Delete a product from the database
   public void deleteProduct(Product product){
        String query = "DELETE FROM `product` WHERE `pro_id` = ?";    
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, product.getId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("DELETE PRODUCT INFO ERRORS: "
                    + e.getMessage());
        }
        
        System.out.println("DELETE PRODUCT: " + product.getName() + " " + product.getQuantity() + " Id: " + product.getId());       
   }
    
    public ObservableList<Product2> getProductInStockFromDatabase(){
        ObservableList<Product2> productInStock = FXCollections.observableArrayList();
        
        String query = "SELECT product.pro_name, product.pro_unit_price, product.pro_qty_in_stock FROM `product`;";
        
        try{
            PreparedStatement prepStatement = connection.prepareStatement(query);
            ResultSet rs = prepStatement.executeQuery();
            
            while(rs.next()){
                productInStock.add(new Product2(rs.getString("pro_name"), rs.getFloat("pro_unit_price"), rs.getInt("pro_qty_in_stock")));
            }
            
            rs.close();
        }catch(SQLException e){
            System.out.println("get Product In Stock From Database Error: "+ e.getMessage());
        }
        
        return productInStock;
    }
    
    
    // when you add a product in your cart
    public void removeQuantityInStockInDatabase(String proName, int proQtity){
                
        String query2 = "update product set product.pro_qty_in_stock = product.pro_qty_in_stock - ? WHERE product.pro_name = ? ;";
        
        try{
            
            PreparedStatement prepStatement2 = connection.prepareStatement(query2);

            prepStatement2.setInt(1, proQtity);
            prepStatement2.setString(2, proName);
            prepStatement2.executeUpdate();
        }catch(SQLException e){
            System.out.println("remove Quantity In Stock In Database Error: "+ e.getMessage());
        }
    }
    
    public void addQuantitySoldInDatabase(String proName, int proQtity){
        
        String query2 = "update product set product.pro_qty_sold = product.pro_qty_sold + ? WHERE product.pro_name = ? ;";
        
        try{
            
            PreparedStatement prepStatement2 = connection.prepareStatement(query2);

            prepStatement2.setInt(1, proQtity);
            prepStatement2.setString(2, proName);
            prepStatement2.executeUpdate();
        }catch(SQLException e){
            System.out.println("add Quantity Sold In Database Error: "+ e.getMessage());
        }
    }    
    
    
    // when you remove a product from your cart
    public void addQuantityInStockInDatabase(String proName, int proQtity){
        
        String query2 = "update product set product.pro_qty_in_stock = product.pro_qty_in_stock + ? WHERE product.pro_name = ? ;";
        
        try{
            
            PreparedStatement prepStatement2 = connection.prepareStatement(query2);

            prepStatement2.setInt(1, proQtity);
            prepStatement2.setString(2, proName);
            prepStatement2.executeUpdate();
        }catch(SQLException e){
            System.out.println("add Quantity In Stock In Database Error: "+ e.getMessage());
        }        
    }

    public void removeQuantitySoldInDatabase(String proName, int proQtity){
        String query1 = "update product set product.pro_qty_sold = product.pro_qty_sold - ? WHERE product.pro_name = ? ;";
        
        
        try{
            PreparedStatement prepStatement1 = connection.prepareStatement(query1);
            
            prepStatement1.setInt(1, proQtity);
            prepStatement1.setString(2, proName);
            prepStatement1.executeUpdate();
        }catch(SQLException e){
            System.out.println("remove Quantity Sold In Database Error: "+ e.getMessage());
        }        
    }
    
 // should enter the id of the current customer   
    public Profile getCustomerProfile(String cus_id){
        Profile profile = new Profile();
        String query = "SELECT customer.cus_fname, customer.cus_lname, customer.cus_phone, customer.cus_dob, customer.cus_email FROM `customer` where customer.cus_id = ? ;";
        try{
            PreparedStatement prepStatement = connection.prepareStatement(query);
            
            prepStatement.setString(1, cus_id);
            ResultSet rs = prepStatement.executeQuery();
            while(rs.next()){
                profile = new Profile(rs.getString("cus_fname"), rs.getString("cus_lname"), rs.getInt("cus_phone"), String.valueOf(rs.getDate("cus_dob")), rs.getString("cus_email") );
            }
        }catch(SQLException e){
            System.out.println("get Customer Profile Error: "+ e.getMessage());
        }
        
        return profile;
    }

    public void getCashierProfile(){
    
    }    
    
    public Product2 getRegisteredProductByCashier(String cus_id){
        Product2 product = new Product2();
        String query = "SELECT product.pro_name, product.pro_unit_price FROM `product` WHERE product.pro_id = ?; ";
        
        try{
            PreparedStatement prepStatement = connection.prepareStatement(query);
            
            prepStatement.setString(1, cus_id);
            ResultSet rs = prepStatement.executeQuery(); 
            while(rs.next()){
                product = new Product2(rs.getString("pro_name"),rs.getFloat("pro_unit_price"));
            }
        }
        catch(SQLException e){
            System.out.println("get Registered Product By Cashier Error: " + e.getMessage());
        }
        
        return product;
    }
    /*
    public String getCashierPseudoFromDatabase(String id){
        String pseudo = "";
        String query = "SELECT cashier.cas_id FROM cashier WHERE cashier.cas_id = ?;";
        try{
            PreparedStatement prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, id);
            ResultSet rs = prepStatement.executeQuery();
            while(rs.next()){
                pseudo = rs.getString("cas_id");
            }
        }catch(SQLException e){
            System.out.println("get Cashier Pseudo From Database Error: " + e.getMessage());
        }
        return pseudo;
    }
    
    public String getCustomerPseudoFromDatabase(String id){
        String pseudo = "";
        String query = "SELECT customer.cus_fname FROM customer WHERE customer.cus_id = ?;";
        try{
            PreparedStatement prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, id);
            ResultSet rs = prepStatement.executeQuery();
            while(rs.next()){
                pseudo = rs.getString("cus_fname");
            }
        }catch(SQLException e){
            System.out.println("get Cashier Pseudo From Database Error: " + e.getMessage());
        }
        return pseudo;
    }    */    

   
}
