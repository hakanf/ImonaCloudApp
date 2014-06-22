package working;

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

@SuppressWarnings("serial")



    // Create a dynamically updating content for the popup
    public class PopupTextField implements PopupView.Content  {

        private TextField tf = new TextField("Edit me");
        private VerticalLayout root = new VerticalLayout();
        public boolean success=false;
       final CustomerApp customerApp;


    public PopupTextField(String[] row, CustomerApp customerApp) {
        this.customerApp = customerApp;


        root.setSizeUndefined();
            root.setSpacing(true);
            root.setMargin(true);
            root.addComponent(new Label(row[1]));

            root.addComponent(tf);
            tf.setValue("");
            tf.setWidth("300px");

            final Table table = new Table("This is my Table");
            table.setEditable(true);
            table.addContainerProperty("ID", Long.class,  null);
            table.addContainerProperty("Code", Integer.class,  null);
            table.addContainerProperty("Name",  String.class,  null);
        table.addItem(new Object[]{Long.valueOf(row[0]), Integer.parseInt(row[1]), row[2]}, new Integer(1));
        table.setHeight("100px");

        Button submitbutton = new Button("OK");


        submitbutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

                    Customer customer =new Customer();
                    //customer.setStockId(Long.valueOf(table.getItem(1).toString()));
                    String row = table.getItem(1).toString();
                    String[] elements=row.split(" ");
                   /* customer.setStockId(Long.valueOf(elements[0]));
                    customer.setStockCode(elements[1]);
                    customer.setStockName(elements[2]);  */
                ApplicationContext appContext =
                        new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");

                CustomerBo customerBo = (CustomerBo)appContext.getBean("stockBo");

                customerBo.update(customer);
                notification();
                }


        });
        root.addComponent(table);
        root.addComponent(submitbutton);




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
