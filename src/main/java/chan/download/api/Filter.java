package chan.download.api;

public class Filter {
	
	private Field field;
	private FilterOperator operator;
	private String value;

	public Filter(Field field, FilterOperator operator, String value) {
		this.field = field;
		this.operator = operator;
		this.value = value;
	}
	
	Field getField() {
		return field;
	}
	
	FilterOperator getOperator() {
		return operator;
	}
	
	String getValue() {
		return value;
	}
	
}
