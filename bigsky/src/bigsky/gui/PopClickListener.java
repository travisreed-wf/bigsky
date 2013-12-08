package bigsky.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import bigsky.Global;

/**
 * Right Click Listener used by Popup class
 * @author Travis Reed
 *
 */
class PopClickListener extends MouseAdapter {
    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        PopUp menu = new PopUp();
        Global.list.setSelectedIndex(Global.list.locationToIndex(e.getPoint()));
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}