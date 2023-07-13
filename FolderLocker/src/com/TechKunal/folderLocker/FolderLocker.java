package com.TechKunal.folderLocker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class FolderLocker {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter the path of the folder to Lock/unlock: ");
		String folderPath= scanner.nextLine();
		
		System.out.println("Enter the Password: ");
		String password = scanner.nextLine();
		
		lockUnlockFolder(folderPath, password);
	}

	private static void lockUnlockFolder(String folderPath, String password) {
		String lockerFolderPath = folderPath + "_locked";
		File folder = new File(folderPath);
		File lockerFolder = new File(lockerFolderPath);
		
		if(!lockerFolder.exists()) {
			if(folder.exists() && folder.isDirectory()) {
				if(folder.renameTo(lockerFolder)) {
				System.out.println("Folder Locked Successfully");
				
				//Create a file containing the password
				File passwordFile = new File(lockerFolder.getAbsolutePath() + File.separator + "password.txt");
				
				try {
					passwordFile.createNewFile();
					Files.write(passwordFile.toPath(),password.getBytes());
				} catch (IOException e) {
					System.out.println("Error creating password file :" +e.getMessage());
				}
			}else {
				System.out.println("Error Locking the Folder");
			}
		}else {
			System.out.println("Folder does not exist or is not a directory.");
		}
	}else {
		//Check if the entered password is correct or not
		File passwordFile = new File(lockerFolder.getAbsoluteFile() + File.separator + "password.txt");
		if(passwordFile.exists()) {
			
			try {
				byte[] storedPasswordBytes = Files.readAllBytes(passwordFile.toPath());
				String storedPassword = new String(storedPasswordBytes);
				if(password.equals(storedPassword)) {
					if(lockerFolder.renameTo(folder)) {
						System.out.println("Folder unlocked uccessfully.");
					}else {
						System.out.println("Error unlocking the folder.");
					}
				}else {
					System.out.println("Incorrect Password");
				}
			} catch (Exception e) {
				System.out.println("Error reading password file: " +e.getMessage());
			}
		}else {
			System.out.println("password file does not exist.");
		}
		}
	}
}

