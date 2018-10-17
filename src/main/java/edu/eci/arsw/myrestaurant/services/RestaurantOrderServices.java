/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.myrestaurant.services;

import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
public interface RestaurantOrderServices {

    /**
     *
     * @param o
     * @throws OrderServicesException
     */
    void addNewOrderToTable(Order o) throws OrderServicesException;

    /**
     *
     * @param tableNumber
     * @return
     * @throws OrderServicesException
     */
    int calculateTableBill(int tableNumber) throws OrderServicesException;

    /**
     *
     * @return
     */
    Set<String> getAvailableProductNames();

    /**
     *
     * @param product
     * @return
     * @throws OrderServicesException
     */
    RestaurantProduct getProductByName(String product) throws OrderServicesException;

    /**
     *
     * @param tableNumber
     * @return
     */
    Order getTableOrder(int tableNumber);

    /**
     *
     * @return
     */
    Set<Integer> getTablesWithOrders();

    /**
     *
     * @param tableNumber
     * @throws OrderServicesException
     */
    void releaseTable(int tableNumber) throws OrderServicesException;

}
