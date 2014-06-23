package CustomerApp;

/**
 * Created with IntelliJ IDEA.
 * User: hakan
 * Date: 6/18/14
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.List;

public class CustomerBoImpl implements CustomerBo {

    CustomerDao customerDao;

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void save(Customer customer){
        customerDao.save(customer);
    }

    public void update(Customer customer){
        customerDao.update(customer);
    }

    public void delete(Customer customer){
        customerDao.delete(customer);
    }

    public List<Customer> find(){
        return customerDao.findAll();
    }
    public List<Customer> findMaleCustomers(){
        return customerDao.findMaleCustomers();
    }
    public List<Customer> findIstanbulCustomers(){
        return customerDao.findIstanbulCustomers();
    }

    public Customer findByStockCode(String stockCode){
        return customerDao.findByStockCode(stockCode);
    }
}