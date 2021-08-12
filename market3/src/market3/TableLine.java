package market3;


public class TableLine {
	
		private int price;
		
		public int getPrice() {
			return price;
		}

		public int getSize() {
			return size;
		}
		
		
		// public void $setSize(int s) { this.size=s; }
		 

		public Type getType() {
			return type;
		}

		private int size;
		
		private Type type;
		
		public enum Type {
			
			/** Bid type */
			B,
			/** Ask type */
			A,
			/** Spread type */
			S,
		}
	
		public TableLine (int price, int size,Type type) {
			super();
			this.type = type;
			this.price=price;
			this.size = size;			
		}		
}

