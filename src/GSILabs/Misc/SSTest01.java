/*
 * Gestion de Sistemas de Informacion
 * Universidad Publica de Navarra
 * First semester of the Academic Year 2015-2016
 */

package GSILabs.Misc;

import java.io.File;
import java.io.IOException;
import javax.swing.table.*;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * Almacenar en un fichero test01.ods una matriz 4x6 de números enteros,
 * ocupando las primeras 4 filas y 6 columnas de la primera de sus hojas.
 * @author subiza.79082
 * @author izu.78236
 * @version 08/10/2015
 */

public class SSTest01 {
    
    public static void main(String[] args) throws IOException {
        
        DefaultTableModel table = new DefaultTableModel(4,6);       
        final File file = new File("test01.ods");
        try{
            SpreadSheet.createEmpty(table);
            SpreadSheet.createEmpty(table).saveAs(file);
        }
        catch (IOException e){
            System.out.println("An error with the IO system appeared");
        }
        
        Sheet sheet;
        sheet = SpreadSheet.createFromFile(file).getSheet(0);
        
        int cont = 1;
        /* Almacenar en el fichero test01.ods una matriz de números enteros
        * utilizando una variable contador que aumentará cada vez que se
        * inserte un número en la matriz.
        */
        for (int i=0; i<4; i++) {
            for (int j=0; j<6; j++) {
                sheet.setValueAt(cont, j, i);
                cont++;
            }
        }
        
        //Guardar la nueva tabla en el fichero file (test01.ods) y abrirlo
        OOUtils.open(sheet.getSpreadSheet().saveAs(file));

    }
    
}