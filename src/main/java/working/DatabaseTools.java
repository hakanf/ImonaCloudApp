package working;

import org.hibernate.classic.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManager;
import java.nio.channels.Channels;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hakan
 * Date: 6/19/14
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseTools {

    public static void persist   (String field1, String field2, String field3, Object caption){
        System.out.println("1");
        String a=caption.toString().replace("[","").replace("]","").replace(",","");
        String[] channels= a.split(" ");
//        System.out.println(channels[0] + "\n"+ channels[1]) ;

        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("2");

        session.beginTransaction();
        System.out.println("3");

       /* ApplicationContext appContext =
                new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");

        working.CustomerBo stockBo = (working.CustomerBo)appContext.getBean("stockBo");
        System.out.println("2");

        /** insert **/
       /* working.Customer customer = new working.Customer();
        customer.setStockCode(field1);
        System.out.println(field1);

        System.out.println(customer.getStockCode());

        customer.setStockName(field2);
        stockBo.save(customer);

         */

        int range = 10000;
        Long stockID= Long.valueOf(String.valueOf((int)(Math.random() * range) + 1));

        Customer customer = new Customer();
        //customer.setStockId(stockID);
        customer.setname("75532");
        customer.setsurname("cemg22in");
        session.save(customer);


        for (int i=0;i <channels.length;i++){
            Channel channel = new Channel();
            channel.setChannelName(channels[i]);
            channel.setCustomer(customer);
            customer.getChannels().add(channel);

            session.save(channel);
        }
        session.getTransaction().commit();

        /** select **/
        // Customer stock2 = stockBo.findByStockCode("7668");
        //System.out.println(stock2.getStockCode());

        /** update **/
        //stock2.setStockName("HAIO-1");
        //stockBo.update(stock2);
        //System.out.println(customer.getStockCode());


        /** delete **/
        //stockBo.delete(stock2);

        System.out.println("Done");
    }

    public static List<Customer> findAll   (){
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
        CustomerBo customerBo = (CustomerBo)appContext.getBean("customerBo");
        List<Customer> customers = customerBo.find();
        System.out.println("done1");
        System.out.println(customers.size());
        Collection<Channel> channels = new LinkedHashSet<Channel>();

        channels = new HashSet<Channel>(customers.get(0).getChannels());

        for (Channel c : channels) {
            System.out.println("IMPORTANT" + c.getChannelName());
        }

               // System.out.println(customers.get(4).getStockCode());
        for(Customer customer : customers){
        }
        System.out.println("showbutton");
        return customers;

    }
}
