package com.zmj.fiveInRow;

public abstract class Action implements FIR{
	private int color=BLACK;		//黑子还是白子,默认是黑子
	private int key=0;		//用于防止多次设置棋子颜色，保证每位只能设置一次
	public void setColor(int color){
		if(key==0){
			if(color==BLACK)
				this.color=BLACK;
			else
				this.color=WHITE;
		}
		key++;
	}
	public int getColor(){
		return color;
	}
	//--------------------------
	public Action(){

	}

	public Action(int color){
		this.setColor(color);
	}
	//---------------------------
	private int r=0;		//下子的行号，默认为0
	private int c=0;		//下子的列号，默认为0
	public int getR() {
		return r;
	}
	public int getC() {
		return c;
	}


	public int[] getAndSetPosition(String order) throws ArrayIndexOutOfBoundsException,ClassCastException {		 // 接收走的那步棋
		String[] ordArr = order.split(" ");
		r= Integer.parseInt(ordArr[0]); // 这里可能产生转换异常
		c= Integer.parseInt(ordArr[1]); // 这里可能产生下标越界异常&转换异常
		int[] position={r,c};
		return position;
	}

	// -----------------------------------------
	public void go(String order, ChessBoard cb) throws Exception {		//接收字符串形式的指令order，以及一个棋盘cb
		getAndSetPosition(order);
		if (cb.getPoint(r, c) == NO_CHESS) {
			if (this.getColor() == BLACK)
				cb.setPoint(r, c, BLACK);
			if (this.getColor() == WHITE)
				cb.setPoint(r, c, WHITE);
		} else
			throw new Exception(); // 这里抛出的是棋子被占异常
	}
}

