package me.theofrancisco;

public class Token {		
	
	private int team;
	private int backColor;
	private boolean selected;
			
	static public final int WHITE_SQUARE = 0;
	static public final int GREEN_SQUARE = 1;
	
    public static final int BLUE_HORSE_ON_WHITE = 2;
    public static final int BLUE_HORSE_ON_GREEN = 3;
    
    public static final int BLACK_HORSE_ON_WHITE = 4; 
    public static final int BLACK_HORSE_ON_GREEN = 5;
	
    public static final int FLAG_ON_WHITE = 6; 
    public static final int FLAG_ON_GREEN = 7;	
	
	static public final int WHITE = 11;
	static public final int GREEN = 12;
	public static final int COMPUTER = 13;
	public static final int HUMAN = 14;
	public static final int FLAG = 15;
	public static final int EMPTY_SQUARE = 16;
	
	public void initToken (int id){
		if (id==BLUE_HORSE_ON_WHITE)	{
			team = HUMAN;
			backColor = WHITE;
		}
		if (id==BLUE_HORSE_ON_GREEN)	{
			team = HUMAN;
			backColor = GREEN;
		}
		if (id==BLACK_HORSE_ON_WHITE)	{
			team = COMPUTER;
			backColor = WHITE;
		}
		if (id==BLACK_HORSE_ON_GREEN)	{
			team = COMPUTER;
			backColor = GREEN;
		}
		if (id==FLAG_ON_GREEN)	{
			team = FLAG;
			backColor = GREEN;
		}
		if (id==FLAG_ON_WHITE)	{
			team = FLAG;
			backColor = WHITE;
		}
		if (id==WHITE_SQUARE){
			team = EMPTY_SQUARE;
			backColor = WHITE;
		}
		if (id==GREEN_SQUARE){
			team = EMPTY_SQUARE;
			backColor = GREEN;
		}
		selected=false;		
	}
	public Token(int id){
		initToken (id);
	}	
	
	
	public int getTeam() {		
		return this.team;
	}
	public void setTeam(int team) {
		this.team = team;		
	}

	public boolean isOnWhite() {
		return backColor==Token.WHITE;
	}

	public void setBackColor(int color) {
		backColor = color;		
	}

	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected( boolean op){
		selected = op;
	}

			
}
