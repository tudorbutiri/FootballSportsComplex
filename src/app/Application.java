package app;

import controller.Ctrl;
import repository.Repo;
import ui.Console;


public class Application {
	public static void main(String[] args) {
		Repo repo= new Repo();
		Ctrl ctrl = new Ctrl(repo);
		Console c = new Console(ctrl);
		c.run();	  
	}
}
