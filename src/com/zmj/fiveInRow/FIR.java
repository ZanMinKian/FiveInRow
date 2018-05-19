package com.zmj.fiveInRow;
public interface FIR {		//FIR指的是Five-In-Row，即是五子棋
	//public static final int NUM=15;		//棋盘行列数
	int BLACK_WIN=-1;
	int WHITE_WIN=1;
	int GO_ON=0;
	int BLACK=-1;
	int WHITE=1;
	int NO_CHESS=0;

	int HOR = 0; // 水平
	int VER = 1; // 竖直
	int SLA = 2; // 斜线
	int BSLA = 3; // 反斜线
}