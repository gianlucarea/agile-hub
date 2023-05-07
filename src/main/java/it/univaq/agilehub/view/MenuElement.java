package it.univaq.agilehub.view;

public class MenuElement {
    
	private String buttonName;
	private String viewName;
	
	
	
	public MenuElement(String button, String view) {
		this.buttonName = button;
		this.viewName = view;
	}
	public String getButtonName() {
		return this.buttonName;
	}
	public String getViewName() {
		return this.viewName;
	}
	public void setButtonName(String button) {
		this.buttonName = button;
	}
	public void setViewName(String view) {
		this.viewName = view;
	}
}
