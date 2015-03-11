package Test;

import java.io.File;

//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//
//import org.junit.Test;

import Logic.Drive;
import Logic.DriveList;
import Logic.Information;
import Logic.InformationList;
import Logic.Roles;

public class TestDriveList {
	
//	public void test1() throws JAXBException {
//		DriveList driveList = new DriveList();
//		Drive master = new Drive(false);
//		System.out.println(master.niceName);
//		master.setRole(Roles.master);
//		Drive client = new Drive(true);
//		System.out.println(client.niceName);
//
//		driveList.addDrive(master);
//		driveList.addDrive(client);
//
//		JAXBContext jaxbContext = JAXBContext
//				.newInstance(InformationList.class);
//		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//		InformationList emps = (InformationList) jaxbUnmarshaller
//				.unmarshal(new File(
//						"/home/developer/git/sdrive/LogicSoDrive/content/20141029_Fahrt1_SDI.XML"));
//		// for (Information emp : emps.getInformations()) {
//		// driveList.updateMasterInformation(emp);
//		// System.out.println(driveList.getDifferenceAngle());
//		// }
//		int timeD = 10;
//		for (int i = 0; i < emps.getInformations().size(); i++) {
//			Information masterInfo = emps.getInformations().get(i);
//			driveList.updateMasterInformation(masterInfo);
//			int j = i - timeD;
//			if (j >= 0) {
//				Information currentInfo = emps.getInformations().get(j);
//				driveList.updateCurrentInformation(currentInfo);
//				System.out.println(driveList.getDifference() * 1000
//						+ " ,Speed-C:" + currentInfo.getSpeed() + " ,Speed-M:"
//						+ masterInfo.getSpeed()+ " getDefIndex:"+driveList.getClosestDistanceIndex());
//			}
//
//		}
//
//	}
//
//	@Test
//	public void test2() throws JAXBException {
//		DriveList driveList = new DriveList();
//		Drive master = new Drive(false);
//		System.out.println(master.niceName);
//		master.setRole(Roles.master);
//		Drive client = new Drive(true);
//		System.out.println(client.niceName);
//
//		driveList.addDrive(master);
//		driveList.addDrive(client);
//
//		JAXBContext jaxbContext = JAXBContext
//				.newInstance(InformationList.class);
//		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//		InformationList emps = (InformationList) jaxbUnmarshaller
//				.unmarshal(new File(
//						"/home/developer/git/sdrive/LogicSoDrive/content/20141029_Fahrt1_SDI.XML"));
//		// for (Information emp : emps.getInformations()) {
//		// driveList.updateMasterInformation(emp);
//		// System.out.println(driveList.getDifferenceAngle());
//		// }
//		int timeD = 10;
//		for (int i = 0; i < emps.getInformations().size(); i++) {
//			Information masterInfo = emps.getInformations().get(i);
//			driveList.updateMasterInformation(masterInfo);
//			int j = i - timeD;
//			if (j >= 0) {
//				if(j>30)
//					timeD --;
//				Information currentInfo = emps.getInformations().get(j);
//				driveList.updateCurrentInformation(currentInfo);
//				float cd = driveList.getClosestDistanceIndex();
//				if(cd == 0)
//					System.out.println("-----------");
//
//				System.out.println(driveList.getDifference() * 1000
//						+ " ,Speed-C:" + currentInfo.getSpeed() + " ,Speed-M:"
//						+ masterInfo.getSpeed()+ " getDefIndex:"+driveList.getClosestDistanceIndex());
//			}
//
//		}
//
//	}
}
