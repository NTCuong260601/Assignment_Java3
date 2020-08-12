/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_hibernate;

import dao.UserDao;
import entity.Users;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Assignment_Hibernate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UserDao ud = new UserDao();
//                Users us1 = new Users("Cuong", "Cuong260601", "Lecturer");
        ArrayList<Users> us = new ArrayList<Users>();
        us = ud.getAllDB();

        for (Users u : us) {
            System.out.println(u.getId());
       }
        Users us1 = ud.findToObject(8);
         us1.setPassword("bdscbsbd");
        us1.setPassword("bdscbsbd");
         us1.setPosition("bdscbsbd");
         ud.updateDB(us1);
//        
//        ud.addDB(us1);

//
//        Users us2 = ud.findToObject(8);
//        System.out.println(us2.getUsername());
//
//       Users us3 = new Users("Hello", "Cuong260601", "Education");
//   
////        System.out.println(us1.getId());
////        System.out.println(us1.getUsername());
////       System.out.println(us1.getPassword());
////       System.out.println(us1.getPosition());
//        System.out.println( ud.updateDB(us1));
        
    }

}
