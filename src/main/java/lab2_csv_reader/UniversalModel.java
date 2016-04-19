package lab2_csv_reader;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class UniversalModel extends AbstractTableModel {
    private String[] columnNames;
    private List<?> rows;
    private List<String> methods = new ArrayList<>();

    public UniversalModel(List<?> rows, String[] columnNames) {
        this.rows = rows;
        this.columnNames = columnNames;

        Class<?> c = rows.get(0).getClass();
        List<Method> methods = Arrays.asList(c.getDeclaredMethods());

//        Stream<String> methodsStream = methods.stream()
//                .map( t->t.getName())
//                .filter( t->t.startsWith("get"));
//        Object[] order = Stream.iterate(1, n->n+1).limit(methodsStream.count()).toArray();

        this.methods = methods.stream()
                .map(Method::getName)
                .filter(t -> t.startsWith("get"))
                .collect(Collectors.toList());


        if (c.isAnnotationPresent(Order.class)){
            Order orderAnnotation = c.getAnnotation(Order.class);
            int[] order = orderAnnotation.value();

            List<String> mth = methods.stream()
                    .map(Method::getName)
                    .filter(t->t.startsWith("get"))
                    .collect(Collectors.toList());

            for (String m : mth) {
                this.methods.set(order[mth.indexOf(m)], m);
            }
        }

        System.out.println(this.methods);
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return rows.get(0).getClass().getDeclaredFields().length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        String getterName = methods.get(columnIndex);
        Class<?> c = rows.get(rowIndex).getClass();

        try {
            Method m = c.getDeclaredMethod(getterName);
            return m.invoke(rows.get(rowIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Object getRowAt(int row) {
        return rows.get(row);
    }

    public Class getColumnClass(int column) {
        Class returnValue;
        if ((column >= 0) && (column < getColumnCount())) {
            returnValue = getValueAt(0, column).getClass();
        } else {
            returnValue = Object.class;
        }
        return returnValue;
    }
}
