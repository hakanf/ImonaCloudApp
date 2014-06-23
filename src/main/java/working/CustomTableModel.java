package working;

import javax.swing.table.AbstractTableModel;
import java.util.Collection;
import java.util.HashSet;
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
        private String[] columnNames = {"name", "surname", "gender", "birthCity","birthDate", "channels"};
        public Object[][] data;

        public CustomTableModel(List<Customer> customers, String header)
        {
             data = new Object[customers.size()][6];

            for(int i=0;i< customers.size();i++){
                Collection<Channel> channels;
                channels = new HashSet<Channel>(customers.get(i).getChannels());
                String channelstring="";
                for (Channel c : channels) {

                    channelstring=channelstring+c.getChannelName()+",";
                }
                channelstring = channelstring.substring(0, channelstring.length()-1);

                data[i][0]=customers.get(i).getname() ;
                data[i][1]= customers.get(i).getsurname() ;
                data[i][2]= customers.get(i).getGender() ;
                data[i][3]= customers.get(i).getBirthCity() ;
                data[i][4]= customers.get(i).getBirthDate() ;


                data[i][5]= channelstring;



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

