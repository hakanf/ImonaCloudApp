package CustomerApp;

/**
 * Created with IntelliJ IDEA.
 * User: hakan
 * Date: 6/19/14
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
import com.vaadin.ui.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@SuppressWarnings("serial")



    // Create a dynamically updating content for the popup
    public class PopupWindow implements PopupView.Content  {

        private TextField tf = new TextField("Edit me");
        private VerticalLayout root = new VerticalLayout();
        public boolean success=false;
       final CustomerApp customerApp;


    public PopupWindow(String[] row, CustomerApp customerApp) {
        this.customerApp = customerApp;


        final Integer customerID=Integer.parseInt(row[0]);
        Form formup = new Form();
        formup.setCaption("Customer");
        formup.setDescription("Edit Customer Data ");
        final TextField tf1=new TextField("Name");
        tf1.setValue(row[1]);
        final TextField tf2=new TextField("Surname");
        tf2.setValue(row[2]);

        formup.addField("name", tf1);

        formup.addField("surname", tf2);

        final ComboBox gender = new ComboBox("Gender");

        gender.addItem("Male");
        gender.addItem("Female");
        gender.setValue(row[3]);

        formup.addField("gender",gender);

        final DateField date = new DateField("Date");
        date.setDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = row[4];
        Date birthdate =new Date();
        try {

            birthdate = formatter.parse(dateInString);
            System.out.println(date);
            System.out.println(formatter.format(birthdate));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        date.setValue(birthdate);

        formup.addField("date",date) ;

        final ComboBox city = new ComboBox("City");
        city.addItem("Istanbul");
        city.addItem("Ankara");
        city.addItem("Izmir");
        city.addItem("Bursa");
        city.addItem("Adana");
        city.addItem("Izmit");
        city.setValue(row[5]);

        formup.addField("city", city);

        final CheckBox activation = new CheckBox("Activation");
        if(row[6].equals("activation"))
        activation.setValue(true);
        else
        activation.setValue(false);


        formup.addField("activation",activation);

        formup.addField("gender",gender);

        final TwinColSelect select =
                new TwinColSelect("Channel");

// Set the column captions (optional)
        select.setLeftColumnCaption("Channel List");
        select.setRightColumnCaption("Channels to be added");


        String[] elements = row[7].split(",");
        HashSet<String> preselected = new HashSet<String>(Arrays.asList(elements));


// Put some data in the select
        String channels[] = {"CNN","NBC", "FOX","ABC", "TheCW" };
        for (int ch=0; ch<channels.length; ch++)
            select.addItem(channels[ch]);


        for(int i=0;i<elements.length;i++)
        select.setValue(preselected);
        formup.addField("channels", select);
// Set the number of visible items
        select.setRows(channels.length);


        formup.setFooter(new VerticalLayout());



// Have a button bar in the footer.

        HorizontalLayout okbar = new HorizontalLayout();
        okbar.setHeight("25px");
        formup.getFooter().addComponent(okbar);


        Button updatebutton = new Button("UPDATE");


        updatebutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

                Customer customer = new Customer();
                customer.setCustomerId(customerID);

                customer.setname(tf1.getValue());
                customer.setsurname(tf2.getValue());
                customer.setGender(gender.getValue().toString());

                Date bd=date.getValue();
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String birthDate = df.format(bd);
                customer.setBirthDate(birthDate);
                customer.setBirthCity(city.getValue().toString());
                customer.setActivation("passive");


                Object caption=select.getValue();
                String a=caption.toString().replace("[","").replace("]","").replace(",","");
                String[] channels= a.split(" ");
                for (int i=0;i <channels.length;i++){
                    Channel channel = new Channel();
                    channel.setChannelName(channels[i]);
                    channel.setCustomer(customer);
                    customer.getChannels().add(channel);

                }
                ApplicationContext appContext =
                        new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");

                CustomerBo customerBo = (CustomerBo) appContext.getBean("customerBo");

                customerBo.update(customer);

                notification();
            }


        });
        Button deletebutton = new Button("DELETE");


        deletebutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

                Customer customer = new Customer();
                customer.setCustomerId(customerID);

                customer.setname(tf1.getValue());
                customer.setsurname(tf2.getValue());
                customer.setGender(gender.getValue().toString());

                Date bd = date.getValue();
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String birthDate = df.format(bd);
                customer.setBirthDate(birthDate);
                customer.setBirthCity(city.getValue().toString());
                customer.setActivation("passive");


                Object caption = select.getValue();
                String a = caption.toString().replace("[", "").replace("]", "").replace(",", "");
                String[] channels = a.split(" ");
                for (int i = 0; i < channels.length; i++) {
                    Channel channel = new Channel();
                    channel.setChannelName(channels[i]);
                    channel.setCustomer(customer);
                    customer.getChannels().add(channel);

                }                //customer.setStockId(Long.valueOf(table.getItem(1).toString()));
                //String row = table.getItem(1).toString();
                // String[] elements = row.split(" ");
                   /* customer.setStockId(Long.valueOf(elements[0]));
                    customer.setStockCode(elements[1]);
                    customer.setStockName(elements[2]);  */
                ApplicationContext appContext =
                        new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");

                CustomerBo customerBo = (CustomerBo) appContext.getBean("customerBo");

                customerBo.delete(customer);
                //DatabaseTools.update(tf1.getValue(), tf2.getValue(), gender.getValue().toString(), date.getValue(),city.getValue().toString(),activation.getValue(), select.getValue()); System.out.println(tf1.getValue());

                notification();
            }


        });
        root.addComponent(formup);
        okbar.addComponent(updatebutton);
        okbar.setComponentAlignment(updatebutton, Alignment.TOP_RIGHT);

        okbar.addComponent(deletebutton);





    }
        public void notification(){
            customerApp.notification();

 }
        public String getMinimizedValueAsHTML() {
            return tf.getValue().toString();
        }

        public Component getPopupComponent() {
            return root;
        }


    }
