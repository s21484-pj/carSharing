package carsharing.repository;

import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;
import carsharing.exception.CarNotFoundException;
import carsharing.exception.CompanyNotFoundException;
import carsharing.exception.CustomerNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Database {

    final String JDBC_DRIVER = "org.h2.Driver";
    String DB_URL = "jdbc:h2:file:../task/src/carsharing/db/test";

    public Database() {
    }

    public void dropCustomerCarCompanyTables(String[] args) {
        Connection conn = null;
        Statement statement = null;
        Statement statement2 = null;
        Statement statement3 = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            statement2 = conn.createStatement();
            statement3 = conn.createStatement();
            String dropCompany = "DROP TABLE IF EXISTS company";
            String dropCar = "DROP TABLE IF EXISTS car";
            String dropCustomer = "DROP TABLE IF EXISTS customer";
            statement3.executeUpdate(dropCustomer);
            statement.executeUpdate(dropCar);
            statement2.executeUpdate(dropCompany);

            statement.close();
            statement2.close();
            statement3.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (statement2 != null) {
                    statement2.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (statement3 != null) {
                    statement3.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void createTable(String[] args, String tableName) {
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            String sql = null;
            if (tableName.equals("company")) {
                sql = "CREATE TABLE IF NOT EXISTS company " +
                        "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR(255) NOT NULL UNIQUE );";
            } else if (tableName.equals("car")) {
                sql = "CREATE TABLE IF NOT EXISTS car " +
                        "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR(255) NOT NULL UNIQUE, " +
                        "company_id INT NOT NULL, " +
                        "CONSTRAINT fk_company FOREIGN KEY (company_id) " +
                        "REFERENCES company (id));";
            } else if (tableName.equals("customer")) {
                sql = "CREATE TABLE IF NOT EXISTS customer " +
                        "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR(255) NOT NULL UNIQUE, " +
                        "rented_car_id INT, " +
                        "CONSTRAINT fk_car FOREIGN KEY (rented_car_id) " +
                        "REFERENCES car (id));";
            }
            statement.executeUpdate(sql);

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void insertRecordToCompanyTable(String[] args, String companyName) {
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            String insert = "INSERT INTO company (name) " +
                    "VALUES ('" + companyName + "');";
            statement.executeUpdate(insert);
            System.out.println("The company was created!");

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void insertRecordToCarTable(String[] args, String carName, int companyId) {
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            String insert = "INSERT INTO car (name, company_id) " +
                    "VALUES ('" + carName + "', " + companyId + ");";
            statement.executeUpdate(insert);
            System.out.println("The car was added!\n");

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void insertRecordToCustomerTable(String[] args, String customerName) {
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            String insert = "INSERT INTO customer (name) " +
                    "VALUES ('" + customerName + "');";
            statement.executeUpdate(insert);
            System.out.println("The customer was added!\n");

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public List<Company> readCompanyTable(String[] args) {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Company> companies = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            String select = "SELECT * FROM company";
            resultSet = statement.executeQuery(select);

            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt(1));
                company.setName(resultSet.getString(2));
                companies.add(company);
            }

            statement.close();
            resultSet.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored){}
        }
        return companies;
    }

    public Company findCompanyById(String[] args, int id) throws CompanyNotFoundException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Company company = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            String select = "SELECT * FROM company WHERE id = " + id + ";";
            resultSet = statement.executeQuery(select);

            while (resultSet.next()) {
                company = new Company();
                company.setId(resultSet.getInt(1));
                company.setName(resultSet.getString(2));
            }

            statement.close();
            resultSet.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored){}
        }
        if (company != null) {
            return company;
        } else {
            throw new CompanyNotFoundException("No company with given id.");
        }
    }

    public List<Car> findCarsByCompanyId(String[] args, int companyId) {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Car> cars = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            String select = "SELECT * FROM car WHERE company_id = " + companyId + ";";
            resultSet = statement.executeQuery(select);

            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt(1));
                car.setName(resultSet.getString(2));
                cars.add(car);
            }

            statement.close();
            resultSet.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored){}
        }
        return cars;
    }

    public List<Car> findAvailableCarsByCompanyId(String[] args, int companyId) {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Car> cars = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            String select = "SELECT * FROM car WHERE company_id = " + companyId + ";";
            resultSet = statement.executeQuery(select);

            List<Customer> customers = readCustomerTable(args);

            while (resultSet.next()) {
                Car car = new Car();
                boolean isRented = false;
                car.setId(resultSet.getInt(1));
                car.setName(resultSet.getString(2));
                for (var customer: customers) {
                    if (customer.getRentedCarId() == car.getId()) {
                        isRented = true;
                        break;
                    }
                }
                if (!isRented) {
                    cars.add(car);
                }
            }

            statement.close();
            resultSet.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored){}
        }
        return cars;
    }

    public Car findCarById(String[] args, int id) throws CarNotFoundException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Car car = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            String select = "SELECT * FROM car WHERE id = " + id + ";";
            resultSet = statement.executeQuery(select);

            while (resultSet.next()) {
                car = new Car();
                car.setId(resultSet.getInt(1));
                car.setName(resultSet.getString(2));
                car.setCompanyId(resultSet.getInt(3));
            }

            statement.close();
            resultSet.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored){}
        }
        if (car != null) {
            return car;
        } else {
            throw new CarNotFoundException("No car with this id");
        }
    }

    public Customer findCustomerById(String[] args, int id) throws CustomerNotFoundException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Customer customer = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            String select = "SELECT * FROM customer WHERE id = " + id + ";";
            resultSet = statement.executeQuery(select);

            while (resultSet.next()) {
                customer = new Customer();
                customer.setId(resultSet.getInt(1));
                customer.setName(resultSet.getString(2));
                customer.setRentedCarId(resultSet.getInt(3));
            }

            statement.close();
            resultSet.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored){}
        }
        if (customer != null) {
            return customer;
        } else {
            throw new CustomerNotFoundException("No customer for given id");
        }
    }

    public List<Customer> readCustomerTable(String[] args) {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Customer> customers = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            String select = "SELECT * FROM customer";
            resultSet = statement.executeQuery(select);

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt(1));
                customer.setName(resultSet.getString(2));
                customer.setRentedCarId(resultSet.getInt(3));
                customers.add(customer);
            }

            statement.close();
            resultSet.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ignored){}
        }
        return customers;
    }

    public void updateCustomerById(String[] args, int id) {
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            Optional<Customer> customer = Optional.ofNullable(findCustomerById(args, id));
            if (customer.isPresent()) {
                String update = "UPDATE customer SET rented_car_id = null WHERE id = " + id + ";";
                statement.executeUpdate(update);
            } else {
                throw new CustomerNotFoundException("No customer for given id");
            }
            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {}
        }
    }

    public void updateCustomerByIdRentCar(String[] args, int customerId, int carId) {
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(getDB_URL(args));
            conn.setAutoCommit(true);

            statement = conn.createStatement();
            Optional<Customer> customer = Optional.ofNullable(findCustomerById(args, customerId));
            if (customer.isPresent()) {
                String update = "UPDATE customer SET rented_car_id = " + carId + " WHERE id = " + customerId + ";";
                statement.executeUpdate(update);
            } else {
                throw new CustomerNotFoundException("No customer for given id");
            }
            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {}
        }
    }

    private String getDB_URL(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-databaseFileName")) {
                DB_URL = "jdbc:h2:file:../task/src/carsharing/db/" + args[i + 1];
            }
        }
        return DB_URL;
    }
}
