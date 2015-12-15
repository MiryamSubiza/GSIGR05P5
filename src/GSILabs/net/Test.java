/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GSILabs.net;

import GSILabs.BSystem.BussinessSystem;
import GSILabs.BSystem.TicketOffice;
import java.io.IOException;

/**
 *
 * @author Labora1
 */
public class Test {
    
    public static void main(String[] args) throws IOException {

        TicketWebServer t = new TicketWebServer(new BussinessSystem());
        
        t.run(8000, "www.megustamuchogsi.com");
        t.stop();
        
    }
    
}
