package edu.eci.arsw.myrestaurant.test;

import edu.eci.arsw.myrestaurant.beans.BillCalculator;
import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServices;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ApplicationServicesTests {

    @Autowired
    RestaurantOrderServices ros;

    @Test
    public void contextLoads() throws OrderServicesException {

    }

    /**
     * Verifica que el valor retornado por la funcion calculateTableBill con la
     * inyeccion de TaxesCalculator2016TributaryReform calcule correctamente el
     * valor
     *
     * @throws OrderServicesException
     */
    @Test
    public void calculateBillTable1WithTaxes() throws OrderServicesException {

        Integer total = ros.calculateTableBill(1);

        assertEquals(total, Integer.valueOf(45302));
    }

    /**
     * Verifica que el valor retornado por la funcion calculateTableBill con la
     * inyeccion de TaxesCalculator2016TributaryReform calcule correctamente el
     * valor
     *
     * @throws OrderServicesException
     */
    @Test
    public void calculateBillTable2WithTaxes() throws OrderServicesException {

        Integer total = ros.calculateTableBill(3);

        assertEquals(total, Integer.valueOf(32290));
    }

}
