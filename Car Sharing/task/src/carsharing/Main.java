package carsharing;

import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;
import carsharing.exception.CarNotFoundException;
import carsharing.exception.CompanyNotFoundException;
import carsharing.repository.Database;

import java.util.List;
import java.util.Scanner;

public class Main {
    static final Scanner intScanner = new Scanner(System.in);
    static final Scanner strScanner = new Scanner(System.in);

    public static void main(String[] args) {
        Database database = new Database();
//        database.dropCustomerCarCompanyTables(args);
        database.createTable(args, "company");
        database.createTable(args, "car");
        database.createTable(args, "customer");
        while (true) {
            printMenu();
            int switchOne = intScanner.nextInt();
            System.out.println();
            switch (switchOne) {
                case 1:
                    while (true) {
                        printMenu2();
                        int switchTwo = intScanner.nextInt();
                        System.out.println();
                        switch (switchTwo) {
                            case 1:
                                List<Company> companies = database.readCompanyTable(args);
                                if (companies.size() > 0) {
                                    System.out.println("Choose the company:");
                                    for (int i = 0; i < companies.size(); i++) {
                                        System.out.print(i + 1);
                                        System.out.println(". " + companies.get(i).getName());
                                    }
                                    System.out.println("0. Back");
                                    int chooseCompany = intScanner.nextInt();
                                    if (chooseCompany == 0) {
                                        break;
                                    } else {
                                        Company company;
                                        if (companies.size() >= chooseCompany - 1) {
                                            company = companies.get(chooseCompany - 1);
                                        } else {
                                            System.out.println("Error");
                                            break;
                                        }
                                        System.out.println("\n'" + company.getName() + "' company");
                                        while (true) {
                                            printMenu3();
                                            int switchThree = intScanner.nextInt();
                                            switch (switchThree) {
                                                case 1:
                                                    List<Car> cars = database.findCarsByCompanyId(args, company.getId());
                                                    if (cars.size() > 0) {
                                                        System.out.println("\nCar list:");
                                                        for (int i = 0; i < cars.size(); i++) {
                                                            System.out.print(i + 1);
                                                            System.out.println(". " + cars.get(i).getName());
                                                        }
                                                        System.out.println();
                                                    } else {
                                                        System.out.println("\nThe car list is empty!\n");
                                                    }
                                                    break;
                                                case 2:
                                                    System.out.println("\nEnter the car name:");
                                                    String carName = strScanner.nextLine();
                                                    database.insertRecordToCarTable(args, carName, company.getId());
                                                    break;
                                                case 0:
                                                    break;
                                            }
                                            if (switchThree == 0) {
                                                break;
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("The company list is empty!");
                                }
                                System.out.println();
                                break;
                            case 2:
                                System.out.println("Enter the company name:");
                                String companyName = strScanner.nextLine();
                                database.insertRecordToCompanyTable(args, companyName);
                                System.out.println();
                                break;
                            case 0:
                                break;
                        }
                        if (switchTwo == 0) {
                            break;
                        }
                    }
                    break;
                case 2:
                    List<Customer> customers = database.readCustomerTable(args);
                    if (customers.size() > 0) {
                        System.out.println("Customer list:");
                        customers.forEach(customer -> {
                            System.out.print(customer.getId());
                            System.out.println(". " + customer.getName());
                        });
                        System.out.println("0. Back");
                    } else {
                        System.out.println("The customer list is empty!\n");
                        break;
                    }
                    int chooseCustomer = intScanner.nextInt();
                    Customer customer;
                    if (chooseCustomer == 0) {
                        break;
                    } else if (customers.size() >= chooseCustomer - 1) {
                        customer = customers.get(chooseCustomer - 1);
                    } else {
                        System.out.println("Wrong input");
                        break;
                    }
                    while (true) {
                        customers = database.readCustomerTable(args);
                        customer = customers.get(chooseCustomer - 1);
                        printMenu4();
                        int chooseAction = intScanner.nextInt();
                        switch (chooseAction) {
                            case 1:
                                if (customer.getRentedCarId() == 0) {
                                    List<Company> companies = database.readCompanyTable(args);
                                    if (companies.size() > 0) {
                                        System.out.println("\nChoose company");
                                        for (int i = 0; i < companies.size(); i++) {
                                            System.out.print(i + 1);
                                            System.out.println(". " + companies.get(i).getName());
                                        }
//                                        companies.forEach(company -> {
//                                            System.out.print(company.getId());
//                                            System.out.println(". " + company.getName());
//                                        });
                                        System.out.println("0. Back");
                                        int chooseCompany = intScanner.nextInt();
                                        if (chooseCompany == 0) {
                                            break;
                                        }
                                        List<Car> cars = database.findAvailableCarsByCompanyId(args, chooseCompany);
                                        if (cars.size() == 0) {
                                            Company company = null;
                                            try {
                                                company = database.findCompanyById(args, chooseCompany);
                                                System.out.println("No available cars in the '" + company.getName() + "' company");
                                            } catch (CompanyNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            System.out.println("\nChoose a car:");
                                            for (int i = 0; i < cars.size(); i++) {
                                                System.out.print(i + 1);
                                                System.out.println(". " + cars.get(i).getName());
                                            }
                                            System.out.println("0. Back");
                                            int chooseCar = intScanner.nextInt();
                                            if (chooseCar == 0) {
                                                break;
                                            } else {
                                                database.updateCustomerByIdRentCar(args, customer.getId(), chooseCar);
                                                System.out.println("\nYou rented '" + cars.get(chooseCar - 1).getName() + "'");
                                            }
                                        }
                                    } else {
                                        System.out.println("The company list is empty!");
                                    }
                                } else {
                                    System.out.println("You've already rented a car!");
                                }
                                break;
                            case 2:
                                if (customer.getRentedCarId() != 0) {
                                    database.updateCustomerById(args, customer.getId());
                                    System.out.println("\nYou've returned a rented car!");
                                } else {
                                    System.out.println("\nYou didn't rent a car!");
                                }
                                break;
                            case 3:
                                if (customer.getRentedCarId() != 0) {
                                    try {
                                        Car car = database.findCarById(args, customer.getRentedCarId());
                                        Company company = database.findCompanyById(args, car.getCompanyId());
                                        System.out.println("\nYour rented car:\n" +
                                                car.getName() + "\n" +
                                                "Company:\n" +
                                                company.getName());
                                    } catch (CarNotFoundException | CompanyNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    System.out.println("\nYou didn't rent a car!");
                                }
                                break;
                            case 0:
                                break;
                        }
                        if (chooseAction == 0) {
                            break;
                        }
                    }
                    break;
                case 3:
                    System.out.println("Enter the customer name:");
                    String customerName = strScanner.nextLine();
                    database.insertRecordToCustomerTable(args, customerName);
                    break;
                case 0:
                    break;
            }
            if (switchOne == 0) {
                break;
            }
        }
    }

    static void printMenu() {
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
    }

    static void printMenu2() {
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
    }

    static void printMenu3() {
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
    }

    static void printMenu4() {
        System.out.println("\n1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");
    }
}
