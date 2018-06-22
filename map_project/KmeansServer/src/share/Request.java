package share;

import java.io.Serializable;

public class Request implements Serializable {
	private static final long serialVersionUID = 1L;
	private int menuChoice;
    private String fileName;
    
    public Request(int menuChoice, String fileName) {
        this.menuChoice = menuChoice;
        this.fileName = fileName;
    }  
    
    public int getMenuChoice() {
        return menuChoice;
    }        
    public String getFileName() {
        return fileName;
    }
}