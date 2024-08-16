/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ayseserra
 */
public class Database {

    static final String baglanti = "jdbc:mysql://127.0.0.1:3306/"
            + "material_db?user=root&password=Ay.segm27";
    static Connection bagl = null;

    public static boolean Addnewuser(User user) {
        try {
            bagl = DriverManager.getConnection(baglanti);

            // Method to check if username is unique
            String checkUsernameQuery = "SELECT * FROM table_user WHERE username=?";
            PreparedStatement checkUsernameStatement = bagl.prepareStatement(checkUsernameQuery);
            checkUsernameStatement.setString(1, user.username);
            ResultSet existingUser = checkUsernameStatement.executeQuery();

            if (existingUser.next()) {
                JOptionPane.showMessageDialog(null, "Username is already in use. Please choose a different one.");
                return false;
            }

            Statement statement = bagl.createStatement();
            String qr = "INSERT INTO table_user "
                    + "(id, username, password, user_type)"
                    + "VALUES('" + user.id + "','" + user.username + "','"
                    + user.password + "','" + user.user_type + "')";
           //regex
            if (!user.password.matches("^[0-9]{5,10}$")) {
                JOptionPane.showMessageDialog(null, "Password should consist only of numbers and be between 5 and 10 characters long.");

                return false;
            }

            statement.executeUpdate(qr);

            bagl.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "SQL Exception during user addition", ex);
            ex.printStackTrace();
        }

        return false;
    }
//method for delete users from database
    public static boolean DeleteUser(User user) {
        try {
            bagl = DriverManager.getConnection(baglanti);

            Statement statement = bagl.createStatement();
            String qr = "DELETE FROM table_user WHERE id='" + user.id + "'";

            statement.executeUpdate(qr);

            bagl.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "SQL Exception during user deletion", ex);
            ex.printStackTrace();
        }

        return false;
    }
//method for loginframe
    public static User Login(String username, String password) {
        User user_a = null;
        try {
            bagl = DriverManager.getConnection(baglanti);
            Statement statement = bagl.createStatement();
            String qr = "SELECT * FROM table_user WHERE username='" + username + "' AND password='" + password + "'";
            ResultSet rs = statement.executeQuery(qr);
            if (rs.next()) {
                user_a = new User();
                user_a.id = rs.getInt("id");
                user_a.username = rs.getString("username");
                user_a.password = rs.getString("password");
                user_a.user_type = rs.getInt("user_type");
            }
            bagl.close();
        } catch (SQLException ex) {

            System.out.println(" error:" + ex.getMessage());
        }

        return user_a;

    }
//method for seelct users from database
    public static ResultSet SelectUsers(String username) {
        ResultSet rs = null;
        try {
            bagl = DriverManager.getConnection(baglanti);

            String qr = (username != null && !username.isEmpty())
                    ? "SELECT * FROM table_user WHERE username=?"
                    : "SELECT * FROM table_user";

            PreparedStatement preparedStatement = bagl.prepareStatement(qr);

            if (username != null && !username.isEmpty()) {
                preparedStatement.setString(1, username);
            }

            rs = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "SQL Exception during user selection", ex);
            ex.printStackTrace();
        }

        return rs;
    }
//method for update users from database
    public static boolean UpdateUser(User user) {
        try {
            bagl = DriverManager.getConnection(baglanti);

            Statement statement = bagl.createStatement();
            String qr = "UPDATE table_user SET "
                    + "username='" + user.username + "',"
                    + "password='" + user.password + "',"
                    + "user_type='" + user.user_type + "'"
                    + " WHERE id='" + user.id + "'";

            statement.executeUpdate(qr);

            bagl.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "SQL Exception during user update", ex);
            ex.printStackTrace();
        }

        return false;
    }
//method for ınsert materials to database
    public static boolean InsertMaterial(Material material) {
        try {
            bagl = DriverManager.getConnection(baglanti);

            if (!material.material_name.matches("^[a-zA-Z]+$")) {
                JOptionPane.showMessageDialog(null, "Invalid material name. Material name should contain only letters.");
                return false;
            }

            Statement statement = bagl.createStatement();
            String qr = "INSERT INTO table_material (materialId, material_name, quantity, category)"
                    + "VALUES('" + material.materialId + "','" + material.material_name + "','"
                    + material.quantity + "','" + material.category + "')";

            statement.executeUpdate(qr);

            bagl.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "SQL Exception during material addition", ex);
            ex.printStackTrace();
        }

        return false;
    }
//method for delete materials to database

    public static boolean DeleteMaterial(Material material) {
        try {
            bagl = DriverManager.getConnection(baglanti);

            Statement statement = bagl.createStatement();
            String qr = "DELETE FROM table_material WHERE materialId='" + material.materialId + "'";

            statement.executeUpdate(qr);

            bagl.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "SQL Exception during material deletion", ex);
            ex.printStackTrace();
        }

        return false;
    }
//method for select materials from database by usıng id

    public static ResultSet SelectMaterials(String materialId) {
        ResultSet rs = null;
        try {
            bagl = DriverManager.getConnection(baglanti);

            String qr = (materialId != null && !materialId.isEmpty())
                    ? "SELECT * FROM table_material WHERE materialId=?"
                    : "SELECT * FROM table_material";

            PreparedStatement preparedStatement = bagl.prepareStatement(qr);

            if (materialId != null && !materialId.isEmpty()) {
                preparedStatement.setString(1, materialId);
            }

            rs = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "SQL Exception during material selection", ex);
            ex.printStackTrace();
        }

        return rs;
    }
//method for update materials from database

    public static boolean UpdateMaterial(Material material) {
        try {
            bagl = DriverManager.getConnection(baglanti);

            Statement statement = bagl.createStatement();
            String qr = "UPDATE table_material SET "
                    + "material_name='" + material.material_name + "',"
                    + "quantity='" + material.quantity + "'"
                    + " WHERE materialId='" + material.materialId + "'";

            statement.executeUpdate(qr);

            bagl.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "SQL Exception during material update", ex);
            ex.printStackTrace();
        }

        return false;
    }
//method for select materials from database 

    public static ArrayList<String> SelectAllCategories() {
        ArrayList<String> materials = new ArrayList<>();
        try {
            bagl = DriverManager.getConnection(baglanti);
            Statement statement = bagl.createStatement();
            String qr = "SELECT DISTINCT material_name FROM table_material";
            ResultSet rs = statement.executeQuery(qr);
            while (rs.next()) {
                materials.add(rs.getString("material_name"));
            }
            bagl.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "SQL Exception during material selection", ex);
            ex.printStackTrace();
        }
        return materials;
    }

    public static ResultSet SelectMaterialsByCategory(String category) {
    ResultSet rs = null;
    try {
        bagl = DriverManager.getConnection(baglanti);
        String qr = (category != null && !category.isEmpty())
                ? "SELECT * FROM table_material WHERE material_name=?"
                : "SELECT * FROM table_material";
        PreparedStatement preparedStatement = bagl.prepareStatement(qr, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        if (category != null && !category.isEmpty()) {
            preparedStatement.setString(1, category);
        }
        rs = preparedStatement.executeQuery();

        if (!rs.isBeforeFirst() && category != null && !category.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No material found for the selected category.");
            rs.close();  
            return null; 
        }

    } catch (SQLException ex) {
        Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "SQL Exception during material selection by category", ex);
        ex.printStackTrace();
    }

    return rs;
}


    public static boolean IncreaseMaterialQuantity(int materialId, int quantity) {
        try {
            bagl = DriverManager.getConnection(baglanti);

            Statement statement = bagl.createStatement();
            String qr = "UPDATE table_material SET quantity=quantity+" + quantity
                    + " WHERE materialId='" + materialId + "'";

            statement.executeUpdate(qr);

            bagl.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "SQL Exception during material quantity increase", ex);
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean DecreaseMaterialQuantity(int materialId, int quantity) {
        try {
            bagl = DriverManager.getConnection(baglanti);

            Statement statement = bagl.createStatement();
            String qr = "UPDATE table_material SET quantity=quantity-" + quantity
                    + " WHERE materialId='" + materialId + "' AND quantity > 0 AND quantity >= " + quantity;


            int updatedRows = statement.executeUpdate(qr);

            bagl.close();

            return updatedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "SQL Exception during material quantity decrease", ex);
            ex.printStackTrace();
        }

        return false;
    }

}
