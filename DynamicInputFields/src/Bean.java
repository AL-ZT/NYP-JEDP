import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class Bean implements Serializable {
	private List<Field> items;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@PostConstruct
	public void init() {
		items = new ArrayList<Field>();
	}
	
	public void add() {
		items.add(new Field());
		logger.info("New Field added");
	}
	
	public void remove(Field field) {
		items.remove(field);
	}
	
	public void save() {
		System.out.println("Fields: " + items);
		logger.info("Fields: " + items);
	}
	
	public List<Field> getItems() {
		return items;
	}
}
