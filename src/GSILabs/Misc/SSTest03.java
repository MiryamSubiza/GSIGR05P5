/*
 * Gestion de Sistemas de Informacion
 * Universidad Publica de Navarra
 * First semester of the Academic Year 2015-2016
 */

package GSILabs.Misc;

import java.io.File;
import java.io.IOException;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * Lectura de datos almacenados en el archivo test02.ods. El color es 
 * innecesario, pero los datos numéricos han de recuperarse.
 * @author subiza.79082
 * @author izu.78236
 * @version 15/10/2015
 */

public class SSTest03 {
    
    public static void main(String[] args) throws IOException {
        
        //Array bidimensional para almacenar los datos del excel
        int[][] valores = new int[4][6];

        final File file = new File("test02.ods");
                
        Sheet sheet;
        sheet = SpreadSheet.createFromFile(file).getSheet(0);
        
        
        /* La posición donde se encuentra la matriz en la hoja de cálculo 
         * es codificada de manera explícita (hard-coded).
         */
        for (int i = 5; i < 9; i++) {
            for (int j = 3; j < 9; j++) {
                valores[i-5][j-3] = Integer.parseInt(sheet.getCellAt(j, i).getTextValue());
                System.out.print(valores[i-5][j-3] + "\t");
            }
            System.out.println();
        }

    }
    
}