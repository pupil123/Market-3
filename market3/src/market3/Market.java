package market3;

import java.io.IOException;

public class Market {
	
	
	public static void main(String[] args) {

	      Table bt = new Table();
			try {
				bt.testRead();
				bt.parseAndWrite();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  }
}