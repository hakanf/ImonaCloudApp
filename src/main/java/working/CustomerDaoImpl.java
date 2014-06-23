package working;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hakan
 * Date: 6/18/14
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {

    public void save(Customer customer){
        getHibernateTemplate().save(customer);
    }

    public void update(Customer customer){
        getHibernateTemplate().update(customer);
    }

    public void delete(Customer customer){
        getHibernateTemplate().delete(customer);
    }

    public List<Customer> findAll(){
       List <Customer> customers = getHibernateTemplate().find("from Customer ");

         return customers;
    }
    public List<Customer> findMaleCustomers(){
        List <Customer> customers = getHibernateTemplate().find("from Customer where gender=?","Male");

        return customers;
    }
    public List<Customer> findIstanbulCustomers(){
        List <Customer> customers = getHibernateTemplate().find("from Customer where birthCity=? ","Istanbul");

        return customers;
    }

    public Customer findByStockCode(String stockCode){
        List list = getHibernateTemplate().find(
                "from Customer where stockCode=?",stockCode
        );
        return (Customer)list.get(0);
    }

}