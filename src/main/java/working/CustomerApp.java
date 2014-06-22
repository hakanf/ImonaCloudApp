package working;

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

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@Theme("mytheme")
@SuppressWarnings("serial")
public class CustomerApp extends UI
{
    final VerticalLayout tab2 = new VerticalLayout();
    final VerticalLayout tab3 = new VerticalLayout();

    final Button showbutton = new Button("Show");
    final Table table = new Table("This is my Table");
    public PopupView popup;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = CustomerApp.class, widgetset = "working.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        
        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
            }
        });
        layout.addComponent(button);

        final Refresher refresher = new Refresher();
        refresher.setRefreshInterval(10000);
        refresher.addListener(new MyRefreshListener(this));
        addExtension(refresher);

        TabSheet tabsheet = new TabSheet();
        layout.addComponent(tabsheet);
// Create the first tab
        VerticalLayout tab1 = new VerticalLayout();
        tabsheet.addTab(tab1, "Customer Information",
                new ThemeResource("img/planets/Mercury_symbol.png"));

        Form form = new Form();
        form.setCaption("Form Caption");
        form.setDescription("This is a description of the Form that is " +
                "displayed in the upper part of the form. You normally " +
                "enter some descriptive text about the form and its " +
                "use here.");
        final TextField tf1=new TextField("tf1");
        final TextField tf2=new TextField("tf2");
        final TextField tf3=new TextField();

        form.getLayout().addComponent(tf1);
        form.addField("name", tf2);
        form.addField("surname", tf3);

        final TwinColSelect select =
                new TwinColSelect("Select Targets to Destroy");

// Set the column captions (optional)
        select.setLeftColumnCaption("These are left");
        select.setRightColumnCaption("These are done for");

// Put some data in the select
        String channels[] = {"CNN","NBC", "FOX","ABC", "The CW" };
        for (int ch=0; ch<channels.length; ch++)
            select.addItem(channels[ch]);

        form.addField("channels", select);
// Set the number of visible items
        select.setRows(channels.length);


        form.setComponentError(new UserError("This is the error indicator of the Form."));
        form.setFooter(new VerticalLayout());
           System.out.println("bb");
            //asd
        form.getFooter().addComponent(
                new Label("This is the footer area of the Form. "+
                        "You can use any layout here. "+
                        "This is nice for buttons."));


// Have a button bar in the footer.

        HorizontalLayout okbar = new HorizontalLayout();
        okbar.setHeight("25px");
        form.getFooter().addComponent(okbar);


// Add an Ok (commit), Reset (discard), and Cancel buttons
// for the form.


        Button okbutton = new Button("OK");

        okbutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                DatabaseTools.persist(tf1.getValue(), tf2.getValue(), tf3.getValue(), select.getValue());         System.out.println(tf1.getValue());

            }
        });
        okbar.addComponent(okbutton);
        okbar.setComponentAlignment(okbutton, Alignment.TOP_RIGHT);
        okbar.addComponent(new Button("Reset"));
        okbar.addComponent(new Button("Cancel"));

        tab1.addComponent(form);

        table.addContainerProperty("ID", Long.class,  null);

        table.addContainerProperty("Code", Integer.class,  null);
        table.addContainerProperty("Name",  String.class,  null);
        //table.addContainerProperty("Year",       Integer.class, null);

        final Button popupButton = new Button("popup");
        //  popupButton.setVisible(false);


        showbutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                List<Customer> customers =DatabaseTools.findAll();
                for(int i=0;i< customers.size();i++){
                   table.addItem(new Object []{(new Long(1)),Integer.parseInt(customers.get(i).getname()), customers.get(i).getsurname()},new Integer(i));
                   // (customers.get(i).getStockId())
                }

            }
        });



        tab2.addComponent(showbutton);


        tab2.addComponent(table);

        table.setImmediate(true);
        table.setMultiSelect(false);
        table.setSelectable(true);

        BrowserWindowOpener popupOpener = new BrowserWindowOpener(CustomerApp.class);
        popupOpener.setFeatures("height=300,width=300");
        popupOpener.extend(popupButton);

        // Add a parameter
        popupOpener.setParameter("foo", "bar");
        // Set a fragment
        popupOpener.setUriFragment("myfragment");

        final Label content = new Label(
                "This is a simple Label component inside the popup. You can place any Vaadin components here.");
        // The PopupView popup will be as large as needed by the content
        content.setWidth("300px");

        // Construct the PopupView with simple HTML text representing the
        // minimized view


        final HashSet<Object> markedRows = new HashSet<Object>();

        final Action ACTION_MARK = new Action("Mark");
        final Action ACTION_UNMARK = new Action("Unmark");
        final Action ACTION_LOG = new Action("Save");
        final Action[] ACTIONS_UNMARKED = new Action[] { ACTION_MARK,
                ACTION_LOG };
        final Action[] ACTIONS_MARKED = new Action[] { ACTION_UNMARK,
                ACTION_LOG };

        table.setColumnHeaders(new String[] { "Country", "Code","mark" });


        final Action actionMark = new Action("Mark");
        final Action actionUnmark = new Action("Unmark");


        table.addActionHandler(new Action.Handler() {
            @Override
            public Action[] getActions(final Object target, final Object sender) {
                return new Action[] { actionUnmark,actionMark };

            }

            @Override
            public void handleAction(final Action action, final Object sender,
                                     final Object target) {
                if (actionMark == action) {
                    markedRows.add(target);
                    Item rowItem = table.getItem(target);
                    String[] row= rowItem.toString().split(" ");
                    System.out.println(row[1]);
                    popup(row);




                    System.out.println("marked");
                } else if (actionUnmark == action) {
                    System.out.println("unmarked");

                    markedRows.remove(target);
                }
                table.markAsDirtyRecursive();

            }

        });


        tab2.setCaption("List of Customers");
        tabsheet.addTab(tab2).setIcon(
                new ThemeResource("img/planets/Venus_symbol.png"));


        tabsheet.addTab(tab3, "Report",
                new ThemeResource("img/planets/Mercury_symbol.png"));

        Button reportbutton = new Button("Report");

        reportbutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                try {
                    report();
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
    }


    public void report() throws JRException, SQLException, IOException {
        /*
        List<working.Customer> stocks= working.DatabaseTools.findAll();
        working.CustomTableModel model=new working.CustomTableModel(stocks);

        FileOutputStream fos = null;
        File tempFile = null;
        try {
            tempFile = File.createTempFile("tmp", ".pdf");
            fos = new FileOutputStream(tempFile);



            String jrxml = "report4.jrxml";
            String jrxmlPath = getClass().getClassLoader().getResource(".").getPath() + jrxml;
            System.out.println("done");
            String jasperCompile= JasperCompileManager.compileReportToFile(jrxmlPath);
            JasperPrint print = JasperFillManager.fillReport(jasperCompile, null, new JRTableModelDataSource(model));
            JasperExportManager.exportReportToPdfStream(print, fos);
            System.out.println("done2");

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



        Embedded pdf = new Embedded("", new FileResource(tempFile));
        pdf.setMimeType("application/pdf");
        pdf.setHeight("800");
        pdf.setType(Embedded.TYPE_BROWSER);
        tab3.addComponent(pdf);
        pdf.setSizeFull();
        tab3.setSizeFull();




      /* try {
            TemporaryFileDownloadResource resource = new TemporaryFileDownloadResource(getApplication(), downloadFileName, contentType, tempFile);
            getWindow().open(resource, "_self");
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }


        final JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/report4.jasper"));

          System.out.println("2");
        final Map paramMap=new HashMap();
        paramMap.put("CompanyName","My Company Inc.");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        final Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer/?user=root&password=root");


        StreamResource.StreamSource source = new StreamResource.StreamSource() {
            public InputStream getStream() {
                byte[] b = null;
                try {
                    b = JasperRunManager.runReportToPdf(report, paramMap, con);
                } catch (JRException ex) {
                    System.out.println(ex);
                }
                return new ByteArrayInputStream(b);
            }

        };
        System.out.println("done");

        */
    }



    public void notification(){
        System.out.println("notified");
        final Window subWindow = new Window("Sub-window");
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subWindow.setContent(subContent);

        Button ok = new Button("OK");

        // Put some components in it
        subContent.addComponent(new Label("Meatball sub"));
        subContent.addComponent(ok);
        subWindow.setClosable(false);

        ok.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                System.out.println("1");
                popup.setPopupVisible(false);
                System.out.println("2");
                subWindow.close(); // Close the sub-window
                System.out.println("3");
            }
        });

        // Center it in the browser window
        subWindow.center();
        addWindow(subWindow);


    }

    public void trayMessage() throws InterruptedException {
        System.out.println("notified");
        /*final Window subWindow = new Window("Sub-window");
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subWindow.setContent(subContent);

        Button ok = new Button("OK");

        // Put some components in it
        subContent.addComponent(new Label("Meatball sub"));
        subContent.addComponent(ok);
        subWindow.setClosable(false);
        wait(2000);
        subWindow.close(); // Close the sub-window
        subWindow.center();
        addWindow(subWindow);       */
        Notification notif=new Notification("Refresher Add-on is Active",

                Notification.Type.TRAY_NOTIFICATION);

        notif.setDelayMsec(0);
        notif.show(Page.getCurrent());


        // Center it in the browser window



    }
    public void popup(String[] row){
        popup = new PopupView(new working.PopupTextField(row, this));
        //final PopupView popup = new PopupView(new PopupTextField(row[1]));
        tab2.addComponent(showbutton);
        tab2.addComponent(popup);
        popup.setHideOnMouseOut(false);




        tab2.addComponent(table);
        popup.setVisible(true);

        popup.setPopupVisible(true);

       /* tf.setAsd(asd);
        System.out.println("asd in popup" + asd);
        popup.setVisible(true);
        popup.setPopupVisible(true); */


        // ------
        // Static content for the minimized view
        // ------

        // Create the content for the popup



       /* Window subWindow = new Window("Sub-window");
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subWindow.setContent(subContent);

        // Put some components in it
        subContent.addComponent(new Label("Meatball sub"));
        subContent.addComponent(new Button("Awlright"));

        // Center it in the browser window
        subWindow.center();

        // Open it in the UI
        addWindow(subWindow);


        BrowserWindowOpener popupOpener = new BrowserWindowOpener(CustomerApp.class);
        popupOpener.setFeatures("height=300,width=300");

        // Add a parameter
        popupOpener.setParameter("foo", "bar");

        // Set a fragment
        popupOpener.setUriFragment("myfragment");
        // Have some content for it
        VerticalLayout content = new VerticalLayout();
        Label label =
                new Label("I just popped up to say hi!");
        label.setSizeUndefined();
        content.addComponent(label);
        content.setComponentAlignment(label,
                Alignment.MIDDLE_CENTER);
        content.setSizeFull();
        setContent(content);       */


    }
    }


