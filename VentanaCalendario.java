import com.mindfusion.common.*;
//import com.mindfusion.common.Rectangle;
import com.mindfusion.drawing.*;
//import com.mindfusion.drawing.awt.AwtImage;
import com.mindfusion.scheduling.*;
//import com.mindfusion.scheduling.awt.*;
import com.mindfusion.scheduling.model.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class VentanaCalendario extends JFrame {
   
    private static final long serialVersionUID = 1L;			
    java.util.Calendar selectedDate = java.util.Calendar.getInstance();
    Calendar calendario = new Calendar();
    protected PropertyChangeSupport changeSupport;
     
     public VentanaCalendario(){
       
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        Dimension tamañoPantalla = miPantalla.getScreenSize();
        int ancho = tamañoPantalla.width;
        int alto = tamañoPantalla.height;
        setBounds(ancho/4, alto/4,ancho/2, alto/2);
        setTitle("Calendario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
        //establece la hora actual
        calendario.setCurrentTime(DateTime.now());
        DateTime today = DateTime.today();
        //establece la fecha actual
        calendario.setDate(today);
        //selecciona la fecha actual
        calendario.getSelection().set(DateTime.today());
        calendario.setCurrentView(CalendarView.SingleMonth);
        */
        changeSupport = new PropertyChangeSupport(this);
                
		
		calendario.setTheme(ThemeType.Light);

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(calendario, BorderLayout.CENTER);
		
		
		calendario.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
				{			
					//clear the selection
					calendario.getSelection().reset();
					//get the date that was double-clicked
					DateTime pointedDate = calendario.getDateAt(e.getX(), e.getY());
					//create a java.util.Calendar instance that points to the selected Date
					java.util.Calendar cal = java.util.Calendar.getInstance();
					cal.set(pointedDate.getYear(), pointedDate.getMonth() - 1, pointedDate.getDay());
					//raise the event
					setSelectedDate(cal);					
 
					dispose();						
					
				}
				
			}
		});	
		
	}
	
	
	//getter of the selectedDate property
	public java.util.Calendar getSelectedDate()
	{
		return selectedDate;
	
	}
	
	//set the selectedDate when typed in the text field
	public void resetSelection(Date date)
	{
		calendario.getSelection().reset();
		calendario.getSelection().set(new DateTime(date), new DateTime(date).addMinutes(2));
		calendario.setDate(new DateTime(date));
		
	}
	
	
	//raises the event that the selectedDate property has changed
	public void setSelectedDate (java.util.Calendar selDate)
	{

		java.util.Calendar oldValue = (java.util.Calendar)selectedDate.clone();
		selectedDate = selDate;				
				
		changeSupport.firePropertyChange("selectedDate",oldValue, selectedDate);
		
	}
	
	//adds a listener for the PropertyChange event
	 public void addPropertyChangeListener(PropertyChangeListener listener) {
		 changeSupport.addPropertyChangeListener(listener);
	    }
        
}
