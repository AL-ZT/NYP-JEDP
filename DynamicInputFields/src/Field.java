import java.io.Serializable;

public class Field implements Serializable {
	
	private String value;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return String.format("Item[value=%s]", value);
	}
}