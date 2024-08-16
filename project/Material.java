/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;

/**
 *
 * @author ayseserra
 */
public class Material {

    public int materialId;
    public String material_name;
    public int quantity;
    public String category;

    public Material(int materialId, String material_name, int quantity, String category) {
        this.materialId = materialId;
        this.material_name = material_name;
        this.quantity = quantity;
        this.category = category;

    }
}
