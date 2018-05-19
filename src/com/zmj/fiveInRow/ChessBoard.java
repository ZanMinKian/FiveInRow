package com.zmj.fiveInRow;

import com.zmj.fiveInRow.FIR;

public class ChessBoard implements FIR {

	private int num=15;
	public int getNum(){
		return num;
	}
	public void setNum(int num){
		this.num=num;
	}
	//----------------------------------------
	private int countStep=0;
	public int getCountStep(){
		int temp=num*num;
		for(int i=0;i<num;i++)
			for(int j=0;j<num;j++)
				if(this.getPoint(i,j)!=NO_CHESS)
					temp--;
		this.countStep=num*num-temp;
		return this.countStep;
	}
	//-----------------------------------------
	private int[][] points=null;		//points代表棋盘上的纵横线的交叉点的集合
	public int[][] getPoints(){
		return points;
	}
	private void setPoints(){		//设置棋盘，即是points属性初始化
		points=new int[num][num];
		for(int i=0;i<num;i++)
			for(int j=0;j<num;j++)
				points[i][j]=NO_CHESS;
	}
	//---------------------------------------
	public void setPoint(int r,int c,int p){		//r代表行，c代表列，p代表单个棋盘上纵线的交叉点
		points[r][c]=p;
	}
	public int getPoint(int r,int c){
		return points[r][c];
	}
	//-----------------------------------------
	private void showBoardBoeder(int num){		//输出上下两行等号,美观
		for(int i=0;i<num*2+2;i++)
			System.out.print("=");
		System.out.println();
	}
	public void showBoard(int[][] points){		//输出棋盘
		showBoardBoeder(num);
		System.out.print("  ");
		for(int i=0;i<num;i++){		//输出列编号
			if(i<10)
				System.out.print("0");
			System.out.print(i);
		}
		System.out.println();

		for(int i=0;i<num;i++){
			System.out.print(i);		//输出行编号
			if(i<10)
				System.out.print(" ");		//美观
			for(int j=0;j<num;j++){
				char p;
				if(points[i][j]==BLACK)
					p='●';
				else if(points[i][j]==WHITE)
					p='○';
				else
					p='╂';
				System.out.print(p);
			}
			System.out.print("\n");
		}
		showBoardBoeder(num);
	}
	//-----------------------------------------
	public ChessBoard(){
		setPoints();
	}
	public ChessBoard(int num){
		setNum(num);
		setPoints();
	}
}
