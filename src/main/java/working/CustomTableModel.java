package working;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hakan
 * Date: 6/20/14
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
    public class CustomTableModel extends AbstractTableModel
    {
        private String[] columnNames = {"name", "email", "firstName", "lastName"};

        private Object[][] data = new Object[20][4];


        public CustomTableModel(List<Customer> customers)
        {
            for(int i=0;i< customers.size();i++){
              /*  data[i][0]=String.valueOf(customers.get(i).getStockId()) ;
                data[i][1]= customers.get(i).getStockCode() ;
                data[i][2]= customers.get(i).getStockName() ;
                data[i][3]="4" ;    */



            }

            }
        public int getColumnCount()
        {
            return this.columnNames.length;
        }

        public String getColumnName(int columnIndex)
        {
            return this.columnNames[columnIndex];
        }

        public int getRowCount()
        {
            return this.data.length;
        }

        public Object getValueAt(int rowIndex, int columnIndex)
        {
            return this.data[rowIndex][columnIndex];
        }
    }

