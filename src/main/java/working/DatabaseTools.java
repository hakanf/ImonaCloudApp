package working;

import org.hibernate.classic.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManager;
import java.nio.channels.Channels;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: hakan
 * Date: 6/19/14
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseTools {

    public static void persist   (String name, String surname, String gender,Date date,String birthCity,boolean activation, Object caption){
        String a=caption.toString().replace("[","").replace("]","").replace(",","");
        String[] channels= a.split(" ");

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();


        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String birthDate = df.format(date);


        Customer customer = new Customer();

        System.out.println(name+"\n"+surname+"\n"+gender+"\n"+birthDate+"\n"+birthCity+"\n"+activation+"\n");

        customer.setname(name);
        customer.setsurname(surname);
        customer.setGender(gender);
        customer.setBirthDate(birthDate);
        customer.setBirthCity(birthCity);
        if(activation)
        customer.setActivation("active");
        else
        customer.setActivation("passive");

        session.save(customer);


        for (int i=0;i <channels.length;i++){
            Channel channel = new Channel();
            channel.setChannelName(channels[i]);
            channel.setCustomer(customer);
            customer.getChannels().add(channel);

            session.save(channel);
        }

        session.getTransaction().commit();


    }

    public static List<Customer> findAll   (){
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
        CustomerBo customerBo = (CustomerBo)appContext.getBean("customerBo");
        List<Customer> customers = customerBo.find();

        return customers;

    }
    public static List<Customer> findMaleCustomers   (){
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
        CustomerBo customerBo = (CustomerBo)appContext.getBean("customerBo");
        List<Customer> customers = customerBo.findMaleCustomers();

        return customers;

    }
    public static List<Customer> findIstanbulCustomers   (){
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
        CustomerBo customerBo = (CustomerBo)appContext.getBean("customerBo");
        List<Customer> customers = customerBo.findIstanbulCustomers();

        return customers;

    }
    public static void update   (String name, String surname, String gender,Date date,String birthCity,boolean activation, Object caption){
        String a=caption.toString().replace("[","").replace("]","").replace(",","");
        String[] channels= a.split(" ");

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();


        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String birthDate = df.format(date);

        Customer customer = new Customer();


        customer.setname(name);
        customer.setsurname(surname);
        customer.setGender(gender);
        customer.setBirthDate(birthDate);
        customer.setBirthCity(birthCity);
        if(activation)
            customer.setActivation("active");
        else
            customer.setActivation("passive");

        session.update(customer);


        for (int i=0;i <channels.length;i++){
            Channel channel = new Channel();
            channel.setChannelName(channels[i]);
            channel.setCustomer(customer);
            customer.getChannels().add(channel);

            session.update(channel);
        }

        session.getTransaction().commit();
    }
    public static void delete (String[] row)
    {

        Customer customer = new Customer();
        customer.setCustomerId(Integer.parseInt(row[0]));

        customer.setname(row[1]);
        customer.setsurname(row[2]);
        customer.setGender(row[3]);


        customer.setBirthDate(row[4]);
        customer.setBirthCity(row[5]);
        customer.setActivation(row[6]);


        String[] channels = row[7].split(",");
        for (int i=0;i <channels.length;i++){
            Channel channel = new Channel();
            channel.setChannelName(channels[i]);
            channel.setCustomer(customer);
            customer.getChannels().add(channel);

        }
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");

        CustomerBo customerBo = (CustomerBo) appContext.getBean("customerBo");

        customerBo.delete(customer);
}
}
