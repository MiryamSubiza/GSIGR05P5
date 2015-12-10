/*
 * Gestion de Sistemas de Informacion
 * Universidad Publica de Navarra
 * First semester of the Academic Year 2015-2016
 */

package GSILabs.Misc;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * Almacenar en un fichero test02.ods una matriz 4x6 de números enteros,
 * donde el color de fondo de las celdas estará determinado por el valor de
 * los enteros.
 * @author subiza.79082
 * @author izu.78236
 * @version 15/10/2015
 */

public class SSTest02 {
    
    public static void main(String[] args) throws IOException {
        
        //Array bidimensional de 4x6 números enteros a almacenar en la tabla
        int[][] valores = new int[][] {{7,4,7,5,19,5},{4,7,6,18,1,6},{3,11,6,15,8,9},{9,8,7,22,7,13}};
        
        DefaultTableModel table = new DefaultTableModel(9,9);       
        final File file = new File("test02.ods");
        try {
            SpreadSheet.createEmpty(table);
            SpreadSheet.createEmpty(table).saveAs(file);
        }
        catch (IOException e) {
            System.out.println("An error with the IO system appeared");
        }
        
        Sheet sheet;
        sheet = SpreadSheet.createFromFile(file).getSheet(0);
        
        //Almacenar en el fichero test02.ods la matriz "valores"
        MutableCell cellAux;
        //Primeras 5 filas vacías (Primer elemento en sexta fila)
        for (int i=5; i<9; i++) {
            //Primeras 3 columnas vacías (Primer elemento en cuarta columna)
            for (int j=3; j<9; j++) {
                /* Si el número a almacenar es mayor o igual que 10, el color 
                * de fondo de la celda será azul. En caso contrario será rojo.
                */
                cellAux = sheet.getCellAt(j,i);
                if (valores[i-5][j-3] >= 10) {
                    cellAux.setBackgroundColor(Color.BLUE);
                }
                else {
                    cellAux.setBackgroundColor(Color.RED);
                }
                sheet.setValueAt(valores[i-5][j-3], j, i);
            }
        }
        //Eliminación de los nombres de las columnas (primera fila)
        for (int i=0; i<9; i++) {
            sheet.setValueAt(null, i, 0);
        }
        
        //Guardar la nueva tabla en el fichero file (test02.ods) y abrirlo
        OOUtils.open(sheet.getSpreadSheet().saveAs(file));
        
    }
    
}