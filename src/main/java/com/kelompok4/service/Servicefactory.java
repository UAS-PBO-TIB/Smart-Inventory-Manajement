/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kelompok4.service;

import java.sql.Connection;
import com.kelompok4.service.*;

/**
 *
 * @author n03ll
 */
public class Servicefactory {
    private final Userservice userService;
    
    public Servicefactory(Connection conn) {
        this.userService = new Userservice(conn);
    }
    
    public Userservice getUserService(){return userService;}    
}
