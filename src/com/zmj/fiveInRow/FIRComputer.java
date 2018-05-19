package com.zmj.fiveInRow;
//import java.lang.Math;

public class FIRComputer extends Action{
	//---------------------这条线以下的代码都是为了计算得到一个order指令-----------------------
	private static int[][][] setChart(ChessBoard cb,Rule rul,int color) {//统计两张表各自的各个点的分数
		int num=cb.getNum();
		int[][][] chart=new int[num][num][4];
		for (int r = 0; r < num; r++)
			for (int c = 0; c < num; c++){
				if (cb.getPoint(r, c) == NO_CHESS) {		//计算的点是空点的，不计算已经下了棋子的点，因为计算没意义
					chart[r][c][HOR] = rul.count(r, c, cb,color,HOR);
					chart[r][c][VER] = rul.count(r, c, cb,color,VER);
					chart[r][c][SLA] = rul.count(r, c, cb,color,SLA);
					chart[r][c][BSLA] = rul.count(r, c, cb,color,BSLA);
				}
				else
					for(int i=0;i<4;i++)
						chart[r][c][i]=-1;
			}
		return chart;
	}
	//为了防止计算机太蠢，防止计算机下在了白棋（对手）围堵过的点
	private static void toPreventGoOnTheBlockedP(int s,ChessBoard cb,int r,int c,int i,int[][] score){
		int manColor=cb.getCountStep()%2==0?WHITE:BLACK;		//人类（对手）的颜色
		try{
			if(i==HOR&&(cb.getPoint(r, c+1)==manColor||cb.getPoint(r, c-1)==manColor))
				score[r][c]-=s;
		}catch(Exception e){}

		try{
			if(i==VER&&(cb.getPoint(r+1, c)==manColor||cb.getPoint(r-1, c)==manColor))
				score[r][c]-=s;
		}catch(Exception e){}

		try{
			if(i==SLA&&(cb.getPoint(r+1, c-1)==manColor||cb.getPoint(r-1, c+1)==manColor))
				score[r][c]-=s;
		}catch(Exception e){}

		try{
			if(i==BSLA&&(cb.getPoint(r+1, c+1)==manColor||cb.getPoint(r-1, c-1)==manColor))
				score[r][c]-=s;
		}catch(Exception e){}
	}
	//活子加分
	private static void alive(int s,ChessBoard cb,int r,int c,int i,int[][] score,int[][][] chart,int aliveNum){
		int num=cb.getNum();
		int t=(aliveNum+1)*(aliveNum+1);

		for(int x=0;x<num;x++)
			for(int y=0;y<num;y++){
				if(chart[x][y][HOR]==aliveNum&&i==HOR&&x==r&&(y-c)*(y-c)==t)
					score[r][c]+=s;
				if(chart[x][y][VER]==aliveNum&&i==VER&&(x-r)*(x-r)==t&&y==c)
					score[r][c]+=s;
				if(chart[x][y][SLA]==aliveNum&&i==SLA&&r+c==x+y&&(x-r)*(x-r)==t)
					score[r][c]+=s;
				if(chart[x][y][BSLA]==aliveNum&&i==BSLA&&r-c==x-y&&(x-r)*(x-r)==t)
					score[r][c]+=s;
			}
	}

	private static int[][] getScore(ChessBoard cb,Rule rul){
		int num=cb.getNum();

		int[][][] comChart=null;
		int[][][] manChart=null;
		if(cb.getCountStep()%2==0){//若当前棋盘上的棋子数是偶数说明计算机是黑方，反之计算机是白方
			comChart=setChart(cb,rul,BLACK);
			manChart=setChart(cb,rul,WHITE);
		}else{
			comChart=setChart(cb,rul,WHITE);
			manChart=setChart(cb,rul,BLACK);
		}

		int[][] score=new int[num][num];
		for(int r=0;r<num;r++)		//初始化
			for(int c=0;c<num;c++)
				score[r][c]=0;

		for (int r = 0; r < num; r++)
			for (int c = 0; c < num; c++)
				if (cb.getPoint(r, c) == NO_CHESS)
					for(int i=0;i<4;i++){
						switch(comChart[r][c][i]){
							case 1:
								score[r][c]+=2;
								toPreventGoOnTheBlockedP(2,cb, r, c, i, score);
								alive(5,cb,r,c,i,score,comChart,1);
								break;
							case 2:
								score[r][c]+=10;
								toPreventGoOnTheBlockedP(10,cb, r, c, i, score);
								alive(20,cb,r,c,i,score,comChart,2);
								break;
							case 3:
								score[r][c]+=40;
								toPreventGoOnTheBlockedP(40,cb, r, c, i, score);
								alive(50,cb,r,c,i,score,comChart,3);
								break;
							case 4:
								score[r][c]+=10000;
								break;
						}
						switch(manChart[r][c][i]){
							case 1:
								score[r][c]+=1;//对方死1得1分
								alive(1,cb,r,c,i,score,manChart,1);
								break;
							case 2:
								score[r][c]+=5;
								alive(15,cb,r,c,i,score,manChart,2);
								break;
							case 3:
								score[r][c]+=20;
								alive(50,cb,r,c,i,score,manChart,3);
								break;
							case 4:
								score[r][c]+=5000;
								break;
						}
					}
		return score;
	}

	public String compute(ChessBoard cb,Rule rul){		//compute方法是这个FIRcomputer类的唯一public方法，即是该类的“接口”
		int num=cb.getNum();
		if(cb.getCountStep()==0)		//若计算机是先手，则下中间点
			return ((num/2)+" "+(num/2));
		//若countStep是偶数，说明计算机执黑子
		//若countStep是奇数，说明计算机执白子

		int[][] score=getScore(cb,rul);
		//后面两个for循环是为了得到最高分的点的坐标
		int s=0;
		int count=0;
		int check=0;


		int i=0,j=0;
		for(int r=0;r<num;r++)
			for(int c=0;c<num;c++)
				if(score[r][c]>s)
					s=score[r][c];

		for(int r=0;r<num;r++)//统计看看最高分的点有多少个
			for(int c=0;c<num;c++)
				if(score[r][c]==s)
					count++;

		int select=((int)(Math.random()*count))+1;//随机选择最高分的点中的一个点，若count为1，则select也必定为1
		loop:
		for(int r=0;r<num;r++)
			for(int c=0;c<num;c++)
				if(score[r][c]==s){
					check++;
					if(check==select){
						i=r;
						j=c;
						break loop;//减少计算机运算
					}
				}

		return i+" "+j;
	}
}