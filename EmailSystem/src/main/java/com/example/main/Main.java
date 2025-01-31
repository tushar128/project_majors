package com.example.main;

import com.example.dao.EmailDao;
import com.example.model.Email;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmailDao emailDao = new EmailDao();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n📩 Email Management System");
            System.out.println("1️⃣ Add Email");
            System.out.println("2️⃣ Search Emails");
            System.out.println("3️⃣ Delete Email");
            System.out.println("4️⃣ Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Enter body: ");
                    String body = scanner.nextLine();
                    
                    Email email = new Email();
                    email.setSubject(subject);
                    email.setBody(body);
                    emailDao.addEmail(email);
                    System.out.println("✅ Email added successfully!");
                    break;

                case 2:
                    System.out.print("Enter search query: ");
                    String searchQuery = scanner.nextLine();
                    
                    // Type inference for the List of Email objects
                    List<Email> emails = emailDao.searchEmails(searchQuery);
                    System.out.println("\n🔍 Search Results:");
                    if (emails == null || emails.isEmpty()) {
                        System.out.println("No emails found.");
                    } else {
                        for (Email e : emails) {
                            System.out.println("📧 ID: " + e.getId() + ", Subject: " + e.getSubject() + ", Body: " + e.getBody());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Email ID to delete: ");
                    Long emailIdToDelete = scanner.nextLong();
                    
                    emailDao.deleteEmail(emailIdToDelete);
                    System.out.println("🗑️ Email deleted successfully!");
                    break;

                case 4:
                    System.out.println("🚪 Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("❌ Invalid option! Please choose a valid operation.");
            }
        }
    }
}
