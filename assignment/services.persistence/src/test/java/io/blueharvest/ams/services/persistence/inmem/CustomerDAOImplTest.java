package io.blueharvest.ams.services.persistence.inmem;

import io.blueharvest.ams.services.persistence.CustomerDAO;

import io.blueharvest.ams.services.persistence.DuplicateEntityException;
import io.blueharvest.ams.services.persistence.EntityNotFoundException;
import io.blueharvest.ams.services.persistence.domain.Customer;
import junit.framework.TestCase;
import org.junit.Assert;

public class CustomerDAOImplTest extends TestCase {

    private CustomerDAO customerDAO;

    private Customer customer1;
    private Customer customer2;
    private Customer customer3;

    @Override
    protected void setUp() throws DuplicateEntityException {
        customer1 = new Customer();
        customer1.setId("ABCEDF");
        customer1.setName("Name1");
        customer1.setSurname("Family1");

        customer2 = new Customer();
        customer2.setId("12BFE3");
        customer2.setName("Name2");
        customer2.setSurname("Family2");

        customer3 = new Customer();
        customer3.setId("13A3BE");
        customer3.setName("Name3");
        customer3.setSurname("Family3");

        customerDAO = new CustomerDAOImpl();
        customerDAO.add(customer1);
        customerDAO.add(customer2);
        customerDAO.add(customer3);
    }

    public void testGetAll() {
        boolean customer1Seen = false;
        boolean customer2Seen = false;
        boolean customer3Seen = false;
        for (Customer customer : customerDAO.getAll()) {
            if (customer.getId().equals(customer1.getId())) {
                customer1Seen = true;
            }
            if (customer.getId().equals(customer2.getId())) {
                customer2Seen = true;
            }
            if (customer.getId().equals(customer3.getId())) {
                customer3Seen = true;
            }
        }
        Assert.assertTrue(customer1Seen);
        Assert.assertTrue(customer2Seen);
        Assert.assertTrue(customer3Seen);
    }

    public void testUpdate() throws EntityNotFoundException {
        Customer customer = customerDAO.getOne(customer3.getId());
        Assert.assertEquals("Name3", customer.getName());
        Customer newCustomer = new Customer();
        newCustomer.setId(customer3.getId());
        newCustomer.setName("NewName");
        customerDAO.update(newCustomer);
        customer = customerDAO.getOne(customer3.getId());
        Assert.assertEquals("NewName", customer.getName());
    }

    public void testDelete() throws EntityNotFoundException {
        customerDAO.delete(customer2.getId());
        boolean customer1Seen = false;
        boolean customer2Seen = false;
        boolean customer3Seen = false;
        for (Customer customer : customerDAO.getAll()) {
            if (customer.getId().equals(customer1.getId())) {
                customer1Seen = true;
            }
            if (customer.getId().equals(customer2.getId())) {
                customer2Seen = true;
            }
            if (customer.getId().equals(customer3.getId())) {
                customer3Seen = true;
            }
        }
        Assert.assertTrue(customer1Seen);
        Assert.assertFalse(customer2Seen);
        Assert.assertTrue(customer3Seen);
    }

    public void testDeleteAll() throws EntityNotFoundException {
        customerDAO.delete(customer1.getId());
        customerDAO.delete(customer2.getId());
        customerDAO.delete(customer3.getId());
        Assert.assertEquals(0, customerDAO.getAll().size());
    }

    public void testGetOne() throws EntityNotFoundException {
        customerDAO.delete(customer1.getId());
        try {
            customerDAO.getOne(customer1.getId());
            Assert.fail();
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        } catch (Throwable t) {
            Assert.fail();
        }
    }

    public void testAddDuplicate() {
        try {
            customerDAO.add(customer1);
            Assert.fail();
        } catch (DuplicateEntityException e) {
            Assert.assertTrue(true);
        } catch (Throwable t) {
            Assert.fail();
        }
    }

    public void testGetByPredicate() {
        boolean customer1Seen = false;
        for (Customer customer : customerDAO.getMatches(entity -> entity.getName().equals("Name1"))) {
            if (customer.getId().equals(customer1.getId())) {
                customer1Seen = true;
            }
        }
        Assert.assertTrue(customer1Seen);
    }
}
