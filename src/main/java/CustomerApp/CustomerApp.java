package CustomerApp;

import javax.servlet.annotation.WebServlet;

import com.github.wolfie.refresher.Refresher;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Theme("mytheme")
@SuppressWarnings("serial")
public class CustomerApp extends UI
{
    final VerticalLayout tab2 = new VerticalLayout();
    final VerticalLayout tab3 = new VerticalLayout();
    public boolean full=false;
    public Embedded pdf=null;


    final Button showbutton = new Button("Show Records");
    final Table table = new Table("Customer Table");
    public PopupView popup;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = CustomerApp.class, widgetset = "CustomerApp.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        Page.getCurrent().setTitle("ImonaCloudApp")  ;
        Label label = new Label("ImonaCloudApp");

        layout.addComponent(label);
        


        final Refresher refresher = new Refresher();
        refresher.setRefreshInterval(10000);
        refresher.addListener(new MyRefreshListener(this));
        addExtension(refresher);

        TabSheet tabsheet = new TabSheet();
        layout.addComponent(tabsheet);
// Create the first tab
        VerticalLayout tab1 = new VerticalLayout();
        tabsheet.addTab(tab1, "Customer Information");

        final Form form = new Form();
        form.setImmediate(true);
        form.setCaption("Customer");
        form.setDescription("Enter Customer Information.");
        final TextField tf1=new TextField("Name");
        final TextField tf2=new TextField("Surname");
        form.addField("name", tf1);

        form.addField("surname", tf2);

        final ComboBox gender = new ComboBox("Gender");
        gender.addItem("Male");
        gender.addItem("Female");
        form.addField("gender",gender);

        final DateField date = new DateField("Birth Date");
        date.setDateFormat("yyyy-MM-dd");

        date.setValue(new Date());
        form.addField("date",date) ;

        final ComboBox city = new ComboBox("City");
        city.addItem("Istanbul");
        city.addItem("Ankara");
        city.addItem("Izmir");
        city.addItem("Bursa");
        city.addItem("Adana");
        city.addItem("Izmit");

        form.addField("city", city);

        form.addField("gender",gender);

        final CheckBox activation = new CheckBox("Activation");
        activation.setValue(true);
        form.addField("activation",activation);

        final TwinColSelect select =
                new TwinColSelect("Channel");

// Set the column captions (optional)
        select.setLeftColumnCaption("Channel List");
        select.setRightColumnCaption("Channel to be Added");


// Put some data in the select
        String channels[] = {"CNN","NBC", "FOX","ABC", "The CW" };
        for (int ch=0; ch<channels.length; ch++)
            select.addItem(channels[ch]);

        form.addField("channels", select);
// Set the number of visible items
        select.setRows(channels.length);


        form.setFooter(new VerticalLayout());
            //asd



// Have a button bar in the footer.

        HorizontalLayout okbar = new HorizontalLayout();
        okbar.setHeight("25px");
        form.getFooter().addComponent(okbar);


// Add an Ok (commit), Reset (discard), and Cancel buttons
// for the form.


        Button savebutton = new Button("Save");
         final UserError error= new UserError("All Fields must be Filled") ;

        savebutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if((tf1.getValue()==null)||(tf2.getValue()==null)|| (gender.getValue()==null)|| (date.getValue()==null)||(city.getValue()==null)||(select.getValue()==null))  {
                    System.out.println("NOT FILLED");
                form.setComponentError(error);
                }
                else  {
                DatabaseTools.persist(tf1.getValue(), tf2.getValue(), gender.getValue().toString(), date.getValue(),city.getValue().toString(),activation.getValue(), select.getValue()); System.out.println(tf1.getValue());
                form.setComponentError(null);
                }
            }
        });

        Button cancelbutton = new Button("Cancel");

        cancelbutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                    tf1.setValue("");
                tf1.setValue("");
                tf2.setValue("");
                gender.setValue(null);
                date.setValue(new Date());
                city.setValue(null);
                select.setValue(null);

            }
        });
        okbar.addComponent(savebutton);
        okbar.setComponentAlignment(savebutton, Alignment.TOP_RIGHT);
        okbar.addComponent(cancelbutton);

        tab1.addComponent(form);

        table.addContainerProperty("Id",  Integer.class,  null);

        table.addContainerProperty("Name",  String.class,  null);
        table.addContainerProperty("Surname",  String.class,  null);
        table.addContainerProperty("Gender",  String.class,  null);
        table.addContainerProperty("Birth Date",  String.class,  null);
        table.addContainerProperty("Birth City",  String.class,  null);
        table.addContainerProperty("Activation",  String.class,  null);
        table.addContainerProperty("Channels",  String.class,  null);



        showbutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                table.removeAllItems();
                List<Customer> customers =DatabaseTools.findAll();
                for(int i=0;i< customers.size();i++){
                    Collection<Channel> channels;
                    channels = new HashSet<Channel>(customers.get(i).getChannels());
                    String channelstring="";
                    for (Channel c : channels) {

                        channelstring=channelstring+c.getChannelName()+",";
                    }
                    channelstring = channelstring.substring(0, channelstring.length()-1);
                   System.out.println(customers.get(i).getCustomerId());
                    table.addItem(new Object []{(customers.get(i).getCustomerId()),(customers.get(i).getname()), customers.get(i).getsurname(),customers.get(i).getGender(),customers.get(i).getBirthDate(),customers.get(i).getBirthCity(),customers.get(i).getActivation(),channelstring },new Integer(i));
                }

            }
        });



        tab2.addComponent(showbutton);


        tab2.addComponent(table);

        table.setImmediate(true);
        table.setMultiSelect(false);
        table.setSelectable(true);




        final HashSet<Object> markedRows = new HashSet<Object>();

        final Action ACTION_EDIT = new Action("Edit");
        final Action ACTION_DELETE = new Action("Delete");




        final Action actionEdit = new Action("Edit");
        final Action actionDelete = new Action("Delete");


        table.addActionHandler(new Action.Handler() {
            @Override
            public Action[] getActions(final Object target, final Object sender) {
                return new Action[] { actionEdit,actionDelete };

            }

            @Override
            public void handleAction(final Action action, final Object sender,
                                     final Object target) {
                if (actionEdit == action) {
                    markedRows.add(target);
                    Item rowItem = table.getItem(target);
                    String[] row= rowItem.toString().split(" ");
                    popup(row);




                } else if (actionDelete == action) {
                    markedRows.add(target);
                    Item rowItem = table.getItem(target);
                    String[] row= rowItem.toString().split(" ");
                    DatabaseTools.delete(row);
                }
                table.markAsDirtyRecursive();

            }

        });


        tab2.setCaption("List of Customers");
        tabsheet.addTab(tab2);


        tabsheet.addTab(tab3, "Report");



        final ComboBox reportType = new ComboBox("Report Type");
        reportType.addItem("Male Customers");
        reportType.addItem("Customers of birth city Istanbul");

        final Form reportform = new Form();
        reportform.addField("reportType", reportType);
        tab3.addComponent(reportform);
        Button reportbutton = new Button("Generate Report");

        reportbutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                try {
                    report(reportType.getValue().toString());
                } catch (JRException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        tab3.addComponent(reportbutton);
        tab3.setImmediate(true);
    }


    public void report(String reportType) throws JRException, SQLException, IOException {
        if(full)
            tab3.removeComponent(pdf);
        CustomTableModel model=null;
        if(reportType.equals("Male Customers")){
        List<Customer> customers= DatabaseTools.findMaleCustomers();
        model=new CustomTableModel(customers,"2"); }
        else if(reportType.equals("Customers of birth city Istanbul"))  {
            List<Customer> customers= DatabaseTools.findIstanbulCustomers();
        model=new CustomTableModel(customers,"3"); }

        FileOutputStream fos = null;
        File tempFile = null;
        try {
            tempFile = File.createTempFile("tmp", ".pdf");
            fos = new FileOutputStream(tempFile);



            String jrxml = "customerreport.jrxml";
            //String jrxmlPath= "C:/finalbeforetest/lastvers/a/CustomerApp/src/main/resources/jasperreports/" + jrxml;
            String jrxmlPath = new File(".").getCanonicalPath()+"/src/main/resources/jasperreports/"+jrxml;
            String jasperCompile= JasperCompileManager.compileReportToFile(jrxmlPath);
            JasperPrint print = JasperFillManager.fillReport(jasperCompile, null, new JRTableModelDataSource(model));
            JasperExportManager.exportReportToPdfStream(print, fos);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JRException e) {
            e.printStackTrace();
        }
        finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String downloadFileName = System.currentTimeMillis() + "_filename.pdf";
        String contentType = "application/pdf";
        System.out.println("done3");



        pdf = new Embedded("", new FileResource(tempFile));
        pdf.setMimeType("application/pdf");
       // tab3.set("1000");

       // pdf.setHeight("1000");

        pdf.setType(Embedded.TYPE_BROWSER);
        tab3.addComponent(pdf);
        pdf.setSizeFull();
        tab3.setSizeFull();
         full=true;


    }



    public void notification(){
        final Window subWindow = new Window("Message");
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subWindow.setContent(subContent);

        Button ok = new Button("OK");

        // Put some components in it
        subContent.addComponent(new Label("Successfully Completed"));
        subContent.addComponent(ok);
        subWindow.setClosable(false);

        ok.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                popup.setPopupVisible(false);
                subWindow.close(); // Close the sub-window
            }
        });

        // Center it in the browser window
        subWindow.center();
        addWindow(subWindow);

        subWindow.bringToFront();


    }

    public void trayMessage() throws InterruptedException {

        Notification notif=new Notification("Refresher Add-on is Active",

                Notification.Type.TRAY_NOTIFICATION);

        notif.setDelayMsec(0);
        notif.show(Page.getCurrent());





    }
    public void popup(String[] row){
        popup = new PopupView(new PopupWindow(row, this));
        //final PopupView popup = new PopupView(new PopupWindow(row[1]));
        tab2.addComponent(showbutton);
        tab2.addComponent(popup);
        popup.setHideOnMouseOut(false);


        tab2.addComponent(table);
        popup.setVisible(true);

        popup.setPopupVisible(true);




    }
    }


