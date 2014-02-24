/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dnevnik;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;

/**
 *
 * @author Spike
 */
public class MyBindComposer extends org.zkoss.bind.BindComposer {
    
    @Listen("onChooseItem = #Seznam")
    public void doChooseItem(ForwardEvent e) {
        MouseEvent me = (MouseEvent) e.getOrigin();                
        Sessions.getCurrent().setAttribute("ID_naloge", me.getData());
        Executions.getCurrent().sendRedirect("../Naloge/OddajNalogo.zul");
    }        
}
