package tr.com;

import jakarta.persistence.*;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cascade");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        Service service = new Service(em, et);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("\n=== Company-Employee Management ===");
            System.out.println("1. Create Company with Employees");
            System.out.println("2. Read Company and Employees");
            System.out.println("3. Update Company Name");
            System.out.println("4. Delete Company");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 : service.createCompanyWithEmployees(scanner);
                case 2 : service.readCompany(scanner);
                case 3 :service.updateCompanyName(scanner);
                case 4 : service.deleteCompany(scanner);
                case 5 : {
                    running = false;
                    System.out.println("Exiting program. Goodbye!");
                }
                default :System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
        em.close();
        emf.close();
    }
}
