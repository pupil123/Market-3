package market3;



import java.io.BufferedReader;
import java.io.File;
//import  org.apache.commons.lang3.math.NumberUtils;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



import javax.swing.JFileChooser;
import org.apache.commons.lang3.math.NumberUtils;
public class Table {
		
	private File file;
	private int maxprice;

	public void testRead() throws IOException {
	      JFileChooser fileopen = new JFileChooser();
	      int ret = fileopen.showDialog(null, "Открыть файл");                
	      if (ret == JFileChooser.APPROVE_OPTION) {
	         this.file = fileopen.getSelectedFile();
          
	                BufferedReader br1 = new BufferedReader(new FileReader(file));	              
	                String line ;	                
	                maxprice=0;	                
	                String[] splits;
	                while ((line=br1.readLine()) != null) {
	                	splits = line.split(",");
	                	if (splitisempty(splits)) {
	                		System.out.println(  "input error"  );
	                		System.exit(0);
	                	}
	                	if (splits.length==4 && splits[0].equalsIgnoreCase("u")&&(Integer.valueOf(splits[1])>maxprice)) {
	                		maxprice=Integer.valueOf(splits[1]);
	                		}
	                }	                
	                br1.close();
	               
	                System.out.println(  "maxprice="+maxprice   );	                
	             }
	      else {
	    	  System.out.println(  "no input "  );
	    	  System.exit(0);	
	      	}
	  }

	      
	      
	      public void parseAndWrite() throws IOException {
	                TableLine[] tableline = new TableLine[maxprice+1] ;
	                for (int i = 0; i <= maxprice; i++) {
	                	tableline[i]= new TableLine(i,0,TableLine.Type.S);
	                    }
	                String[] splits;
	                BufferedReader br2 = new BufferedReader(new FileReader(file));
	                FileWriter writer = new FileWriter("C:\\Users\\User\\Documents\\probe market2.txt", false);
					String line;
					int lineNumber = 0;

					while ((line = br2.readLine()) != null) {
	                	splits = line.split(",");

	                		if (splits.length==4 && splits[0].equalsIgnoreCase("u")) {
	                			if(splits[3].equalsIgnoreCase("ask")){
	                			tableline[Integer.valueOf(splits[1])]= new TableLine(Integer.valueOf(splits[1]),
	                					tableline[Integer.valueOf(splits[1])].getSize() + Integer.valueOf(splits[2]),
	                					TableLine.Type.A);
	                			}
	                			else if(splits[3].equalsIgnoreCase("bid")) {
	                				tableline[Integer.valueOf(splits[1])]= new TableLine(Integer.valueOf(splits[1]),
	                						tableline[Integer.valueOf(splits[1])].getSize()+Integer.valueOf(splits[2]),
	                						TableLine.Type.B);
	                			}
	                		}
	                		
	                		if (splits.length==3 && splits[0].equalsIgnoreCase("o")&&isDigit( splits[2]) ){
	                			if(splits[1].equalsIgnoreCase("sell")){
	                				int deltaS=Integer.valueOf(splits[2]);
	                				int current_bestBidPrice =0;
	                				while(tableline[bestBidPrice(tableline)].getSize() <= deltaS && bestBidPrice(tableline)!=0) {
	                					deltaS=deltaS-tableline[bestBidPrice(tableline)].getSize();
	                					current_bestBidPrice=bestBidPrice(tableline);
	                					tableline[bestBidPrice(tableline)]= new TableLine(bestBidPrice(tableline),0,TableLine.Type.S);
		                				}
	                					if (bestBidPrice(tableline)==0)
	                						tableline[current_bestBidPrice]= new TableLine(current_bestBidPrice,0,TableLine.Type.S);
	                					
	                					else tableline[bestBidPrice(tableline)]= new TableLine(bestBidPrice(tableline),
		                					tableline[bestBidPrice(tableline)].getSize() - deltaS,
		                					TableLine.Type.B);
	                			}
	                			else if(splits[1].equalsIgnoreCase("buy")) {
	                				int deltaB=Integer.valueOf(splits[2]);
	                				int current_bestAskPrice =0;
	                				while(tableline[bestAskPrice(tableline)].getSize() <= deltaB && bestAskPrice(tableline)!=0) {
	                					deltaB=deltaB-tableline[bestAskPrice(tableline)].getSize();
	                					current_bestAskPrice = bestAskPrice(tableline);
	                					tableline[bestAskPrice(tableline)]= new TableLine(bestAskPrice(tableline),0,TableLine.Type.S);
		                				}
	                					if(bestAskPrice(tableline)==0) 
	                						tableline[current_bestAskPrice]= new TableLine(current_bestAskPrice,0,TableLine.Type.S);
	                					
	                					else tableline[bestAskPrice(tableline)]= new TableLine(bestAskPrice(tableline),
	                						tableline[bestAskPrice(tableline)].getSize()-deltaB,
	                						TableLine.Type.A);
	                			}
	                		}
	                		
	                		if ( splits[0].equalsIgnoreCase("q")) {
	                			if(splits.length==3 &&splits[1].equalsIgnoreCase("size")&& NumberUtils.isDigits( splits[2] )){
	                					        			
	               				writer.write(String.valueOf(tableline[Integer.valueOf(splits[2])].getSize()));
                				writer.append('\n');
	                			}
	                			else if(splits.length==2) {
	                				if (splits[1].equalsIgnoreCase("best_bid")) {
	                					writer.write(bestBidPrice(tableline)+",");
			               				writer.write(String.valueOf(tableline[bestBidPrice(tableline)].getSize()));
		                				writer.append('\n');	
	                			}
	                				else if  (splits[1].equalsIgnoreCase("best_ask")) {	                 					
	                				writer.write(bestAskPrice(tableline)+",");
		               				writer.write(String.valueOf(tableline[bestAskPrice(tableline)].getSize()));
	                				writer.append('\n');
	                				}
	                			}
	                		}
	    	                              
                		lineNumber++;
	
	                  }
					br2.close();
					writer.close();
					

	                for (int i = 0; i <= maxprice; i++) {
	                	System.out.println (tableline[i].getPrice() +"   "+
 					           tableline[i].getSize() +"   "+
 					           tableline[i].getType());
	                 }
	                System.out.println ("best_bid_price="+bestBidPrice(tableline));
	                System.out.println ("best_ask_price="+bestAskPrice(tableline));
	                System.out.println(  "lineNumber="+ lineNumber   );
	               }
	                

	private static boolean splitisempty(String[] arr) {
	    for (String s: arr) {
	        if (s.equals(""))
	            return true;
	    }
	    return false;
	}
	
	private static boolean isDigit(String s) throws NumberFormatException {
	    try {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	private int bestBidPrice(TableLine[] e) {
		int best_bid_price=0;
    for (int i = 0; i <= maxprice; i++) {
    	if (e[i].getType().equals(TableLine.Type.B))
    		best_bid_price=i;	
    }
    return best_bid_price;	
	}
	
	private int bestAskPrice(TableLine[] e) {
		int best_ask_price=0;	
    for (int i = maxprice; i >= 0; i--) {    	
    	if (e[i].getType().equals(TableLine.Type.A))
    		best_ask_price=i;
    	} 
    return best_ask_price;	
	}
	
	/*
	 * public class NumberUtilsDelegator { public boolean isDigit(String str) {
	 * return NumberUtils.isNumber(str); }
	 * 
	 * }
	 */
}


