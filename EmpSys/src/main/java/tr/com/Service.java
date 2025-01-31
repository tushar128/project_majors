package tr.com;

import jakarta.persistence.*;
import java.util.Scanner;

public class Service {

    private final EntityManager em;
    private final EntityTransaction et;

    public Service(EntityManager em, EntityTransaction et) {
        this.em = em;
        this.et = et;
    }

    // Create Operation
    public void createCompanyWithEmployees(Scanner scanner) {
        System.out.print("Enter Company Name: ");
        scanner.nextLine();
        String companyName = scanner.nextLine();

        Company company = new Company();
        company.setName(companyName);

        System.out.print("Enter number of employees: ");
        int numEmployees = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= numEmployees; i++) {
            System.out.print("Enter Employee Name: ");
            String empName = scanner.nextLine();

            Employee employee = new Employee();
            employee.setName(empName);
            employee.setCompany(company);

            company.getEmployees().add(employee);
        }

        et.begin();
        em.persist(company);
        et.commit();

        System.out.println("Company and employees saved successfully!");
    }

    // Read Operation
    public void readCompany(Scanner scanner) {
        System.out.print("Enter Company ID: ");
        Long companyId = scanner.nextLong();

        Company company = em.find(Company.class, companyId);
        if (company != null) {
            System.out.println("Company Name: " + company.getName());
            System.out.println("Employees:");
            for (Employee employee : company.getEmployees()) {
                System.out.println("- ID: " + employee.getId() + ", Name: " + employee.getName());
            }
        } else {
            System.out.println("Company not found.");
        }
    }

    // Update Operation
    public void updateCompanyName(Scanner scanner) {
        System.out.print("Enter Company ID: ");
        Long companyId = scanner.nextLong();

        Company company = em.find(Company.class, companyId);
        if (company != null) {
            System.out.print("Enter new Company Name: ");
            scanner.nextLine();
            String newName = scanner.nextLine();

            et.begin();
            company.setName(newName);
            et.commit();

            System.out.println("Company name updated successfully!");
        } else {
            System.out.println("Company not found.");
        }
    }

    // Delete Operation
    public void deleteCompany(Scanner scanner) {
        System.out.print("Enter Company ID: ");
        Long companyId = scanner.nextLong();

        Company company = em.find(Company.class, companyId);
        if (company != null) {
            et.begin();
            em.remove(company);
            et.commit();

            System.out.println("Company and its employees deleted successfully!");
        } else {
            System.out.println("Company not found.");
        }
    }
}
