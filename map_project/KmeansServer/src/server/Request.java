package server;
public class Request {
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