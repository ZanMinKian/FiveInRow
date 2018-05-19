package com.zmj.fiveInRow;

import java.util.Scanner;

public class Play implements FIR {
	public static int play(ChessBoard cb, Rule rul, Action obj1, Action obj2) { //obj1为先手，obj2为后手
		obj1.setColor(BLACK);		//先手执黑
		obj2.setColor(WHITE);		//后手执白

		int judge = obj1.getColor(); // 判断下哪种颜色的棋子,先下obj1所执子的颜色的棋子
		cb.showBoard(cb.getPoints()); // 打印棋盘

		int result=0;

		while (true) {
			String order1 = "";
			String order2 = "";

			if (obj1 instanceof FIRComputer && judge == obj1.getColor()) {
				order1 = ((FIRComputer) obj1).compute(cb,rul);
				System.out.println(
						"计算机输入点是：" + obj1.getAndSetPosition(order1)[0] + " " + obj1.getAndSetPosition(order1)[1]);
			}
			if (obj1 instanceof Human && judge == obj1.getColor()) {
				System.out.print("黑方请输入棋子：");
				order1 = new Scanner(System.in).nextLine();
			}
			if (obj2 instanceof FIRComputer && judge == obj2.getColor()) {
				order2 = ((FIRComputer) obj2).compute(cb,rul);
				System.out.println(
						"计算机输入点是：" + obj2.getAndSetPosition(order2)[0] + " " + obj2.getAndSetPosition(order2)[1]);
			}
			if (obj2 instanceof Human && judge == obj2.getColor()) {
				System.out.print("白方请输入棋子：");
				order2 = new Scanner(System.in).nextLine();
			}

			// 上面这堆if是用来判断当前该是哪一方下棋，每次循环都必经上面4条if且选择其一运行，然后继续往下走

			if (judge == obj1.getColor()) {
				try {
					obj1.go(order1, cb);
					cb.showBoard(cb.getPoints());
				} catch (Exception e) {
					System.out.println("黑方输入无效！请重新输入！");
					continue;
				}
			} else if (judge == obj2.getColor()) {
				try {
					obj2.go(order2, cb);
					cb.showBoard(cb.getPoints());
				} catch (Exception e) {
					System.out.println("白方输入无效！请重新输入！");
					continue;
				}
			}


			result=judge==obj1.getColor()?rul.check(obj1.getR(),obj1.getC(),cb):rul.check(obj2.getR(),obj2.getC(),cb); // 用于判断胜负
			// 判断当前下子方是先手方还是后手方，然后获得那一方落子点，传入check方法

			if (result == BLACK_WIN||result==WHITE_WIN||cb.getCountStep()==cb.getNum()*cb.getNum())
				break;
			judge *= -1; // 交换
		}

		return result;
	}
}
