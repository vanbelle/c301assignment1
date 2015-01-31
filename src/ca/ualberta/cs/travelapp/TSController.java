package ca.ualberta.cs.travelapp;

import java.io.IOException;

//public class TSController
//{
//	// Lazy Singleton
//	private static TotalSum ts= null;
//	
//	// Warning: throws a runTimeException
//	static public TotalSum getTS() {
//		if (ts == null) {
//			try {
//				ts = TSManager.getManager().loadTS();
//				ts.addListener(new Listener() {
//					@Override
//					public void update() {
//						saveTS();
//					}
//				});
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				throw new RuntimeException("Could not deserialize StudentList from StudentListManager");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				throw new RuntimeException("Could not deserialize StudentList from StudentListManager");
//			}
//		}
//		return ts;
//	}
//	static public void saveStudentList() {
//		try {
//			TSManager.getManager().savets(getts());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new RuntimeException("Could not deserialize StudentList from StudentListManager");
//		}
//	}
//
//	public void check(ExpenseItemList e) {
//		
//	}
//}
