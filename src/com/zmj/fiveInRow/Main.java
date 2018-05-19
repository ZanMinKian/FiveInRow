package com.zmj.fiveInRow;
import java.util.Scanner;
public class Main implements FIR {
	private static void printResult(int result){
		if(result==BLACK_WIN)
			System.out.println("黑方获胜！");
		else if(result==WHITE_WIN)
			System.out.println("白方获胜");
		else
			System.out.println("棋盘都被下满了！");
	}
	public static void main(String[] args){
		System.out.println("\n=====欢迎来到健哥最强五子棋=====\n");
		System.out.println("=============请选择=============\n");
		System.out.println("1：人机对战\t2：机人对战\t3：人人对战\t4：机机对战");
		int t=0;
		while(true){
			try{
				t=new Scanner(System.in).nextInt();
				if(t==1||t==2||t==3||t==4)
					break;
				else
					throw new Exception();
			}catch(Exception e){
				System.out.print("输入不合法！请重新输入：");
			}
		}

		Rule rul=new Rule();
		Human man=new Human();
		Human anotherMan=new Human();
		FIRComputer com=new FIRComputer();
		FIRComputer anotherCom=new FIRComputer();
		ChessBoard cb=new ChessBoard(15);

		if(t==1){
			int result=Play.play(cb,rul,man,com);
			printResult(result);
		}
		else if(t==2){
			int result=Play.play(cb,rul,com,man);
			printResult(result);
		}
		else if(t==3){
			int result=Play.play(cb,rul,man,anotherMan);
			printResult(result);
		}
		else if(t==4){
			int result=Play.play(cb, rul, com, anotherCom);
			printResult(result);
		}

		System.out.println("双方总共下了"+cb.getCountStep()+"步！");
	}
}