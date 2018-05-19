package com.zmj.fiveInRow;

public class Rule implements FIR{
	public int count(int r,int c,ChessBoard cb,int color,int direction){
		int pointNum=0;
		int tempR=r;
		int tempC=c;

		for(int i=1;i<5;i++){
			r=tempR;
			c=tempC;
			try{
				switch(direction){
					case HOR:c=c+i;break;
					case VER:r=r+i;break;
					case SLA:r=r-i;c=c+i;break;
					case BSLA:r=r-i;c=c-i;break;
				}
				if(cb.getPoint(r, c)==color)
					pointNum++;
				else
					break;
			}catch(Exception e){
				break;
			}
		}
		for(int i=1;i<5;i++){
			r=tempR;
			c=tempC;
			try{
				switch(direction){
					case HOR:c=c-i;break;
					case VER:r=r-i;break;
					case SLA:r=r+i;c=c-i;break;
					case BSLA:r=r+i;c=c+i;break;
				}
				if(cb.getPoint(r, c)==color)
					pointNum++;
				else
					break;
			}catch(Exception e){
				break;
			}
		}
		return pointNum;
	}

	public static int decide(int r,int c,int pointNum,ChessBoard cb){	//决定哪一方赢
		if(pointNum>=4&&cb.getPoint(r, c)==BLACK)
			return BLACK_WIN;
		else if(pointNum>=4&&cb.getPoint(r, c)==WHITE)
			return WHITE_WIN;
		else
			return GO_ON;
	}

	public int check(int r,int c,ChessBoard cb){
		//r和c为落子点的坐标，该方法的思路是判断落下那颗棋子周围的棋子是如何排布的
		int color=cb.getPoint(r, c);
		int pointNum;
		int result=0;

		pointNum=count(r,c,cb,color,HOR);
		result=decide(r,c,pointNum,cb);
		if(result==BLACK_WIN||result==WHITE_WIN)
			return result;

		pointNum=count(r,c,cb,color,VER);
		result=decide(r,c,pointNum,cb);
		if(result==BLACK_WIN||result==WHITE_WIN)
			return result;

		pointNum=count(r,c,cb,color,SLA);
		result=decide(r,c,pointNum,cb);
		if(result==BLACK_WIN||result==WHITE_WIN)
			return result;

		pointNum=count(r,c,cb,color,BSLA);
		result=decide(r,c,pointNum,cb);
		if(result==BLACK_WIN||result==WHITE_WIN)
			return result;

		return result;
	}
}
