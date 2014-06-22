package working;

/**
 * Created with IntelliJ IDEA.
 * User: hakan
 * Date: 6/18/14
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.List;

public interface CustomerDao {

    void save(Customer customer);
    void update(Customer customer);
    void delete(Customer customer);
    List<Customer> findAll();

    Customer findByStockCode(String stockCode);

}