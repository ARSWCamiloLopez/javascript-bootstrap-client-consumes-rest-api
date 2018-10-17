/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.ProductType;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServices;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;
import java.util.HashMap;
import java.util.Hashtable;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/orders")
public class OrdersAPIController {

    @Autowired
    RestaurantOrderServices restOrderServices;

    /**
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllOrders() {

        Set<Integer> numTables = restOrderServices.getTablesWithOrders();
        Map<Integer, Order> orders = new HashMap();
        String codeToJson = "";

        for (Integer x : numTables) {
            orders.put(x, restOrderServices.getTableOrder(x));
        }

        codeToJson = new Gson().toJson(orders);

        try {
            return new ResponseEntity<>(codeToJson, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No se ha podido retornar las ordenes", HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @param idMesa
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "{idMesa}")
    public ResponseEntity<?> getOrdersByIdTable(@PathVariable("idMesa") Integer idMesa) {

        Map<Integer, Order> orders = new HashMap();
        String codeToJson = "";

        orders.put(idMesa, restOrderServices.getTableOrder(idMesa));

        codeToJson = new Gson().toJson(orders);

        try {
            return new ResponseEntity<>(codeToJson, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No se ha podido retornar la ordene", HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @param o
     * @return
     * @throws edu.eci.arsw.myrestaurant.services.OrderServicesException
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addNewOrder(@RequestBody String o) throws OrderServicesException {

        Type listType = new TypeToken<Map<Integer, Order>>() {
        }.getType();
        Map<Integer, Order> orders = new Gson().fromJson(o, listType);

        Object[] keys = orders.keySet().toArray();

        Order orderToAdd = new Order((Integer) keys[0]);
        orderToAdd.setOrderAmountsMap(orders.get((Integer) keys[0]).getOrderAmountsMap());

        restOrderServices.addNewOrderToTable(orderToAdd);

        try {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al añadir una nueva orden", HttpStatus.FORBIDDEN);
        }
    }

    /**
     *
     * @param idMesa
     * @param producto
     * @return
     * @throws OrderServicesException
     */
    @RequestMapping(method = RequestMethod.PUT, path = "{idmesa}")
    public ResponseEntity<?> addNewProductToATable(@PathVariable("idmesa") Integer idMesa,
            @RequestBody String producto) throws OrderServicesException {

        try {
            Order orderToUpdate = restOrderServices.getTableOrder(idMesa);

            //Pasar el String JSON a un Map
            Type listType = new TypeToken<Map<String, Integer>>() {
            }.getType();
            Map<String, Integer> product = new Gson().fromJson(producto, listType);

            //Obtener las llaves del Map
            Object[] keys = product.keySet().toArray();

            //Añadir el producto a la orden obteniendo los objetos en el Map
            orderToUpdate.addDish((String) keys[0], product.get(keys[0]));

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (JsonParseException ex) {
            Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al añadir una nueva orden", HttpStatus.FORBIDDEN);
        }
    }

    /**
     *
     * @param idMesa
     * @return
     * @throws OrderServicesException
     */
    @RequestMapping(method = RequestMethod.GET, path = "{idmesa}/total")
    public ResponseEntity<?> calculateBillByTable(@PathVariable("idmesa") Integer idMesa) throws OrderServicesException {
        try {
            Integer total = restOrderServices.calculateTableBill(idMesa);
            return new ResponseEntity<>("Total: " + total, HttpStatus.ACCEPTED);
        } catch (OrderServicesException ex) {
            Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No se ha podido calcular el total", HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @param idMesa
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "remove/{idmesa}")
    public ResponseEntity<?> deleteOrderByIdTable(@PathVariable("idmesa") Integer idMesa) {
        try {
            restOrderServices.releaseTable(idMesa);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OrderServicesException ex) {
            Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No se ha podido eliminar la orden", HttpStatus.NOT_FOUND);
        }
    }

}
