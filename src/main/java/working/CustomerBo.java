package working;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hakan
 * Date: 6/18/14
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */


public interface CustomerBo {

    void save(Customer customer);
    void update(Customer customer);
    void delete(Customer customer);
    List<Customer> find();

    Customer findByStockCode(String stockCode);
}