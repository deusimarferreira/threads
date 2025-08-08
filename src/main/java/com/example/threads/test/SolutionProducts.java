package com.example.threads.test;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import lombok.val;

class Node{
    IProduct product;
    int quantity;
    Node(IProduct product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}

interface ICompany {
    List<IUser> getUsers();
    void setUsers(List<IUser> users);
    List<Node> getProducts();
    void setProducts(List<Node> products);
    void addProduct(IProduct product, int quantity);
    void addUser(IUser user);
    void makeOrder(List<Node> products, IUser user);
}

interface IProduct {
    int getId();
    void setId(int id);
    String getName();
    void setName(String name);
    double getPrice();
    void setPrice(double price);
    double getShippingCost();
    void setShippingCost(double shippingCost);
}

interface IUser {
    int getId();
    void setId(int id);
    String getName();
    void setName(String name);
    double getBalance();
    void setBalance(double balance);
    List<Node> getOrders();
    void setOrders(List<Node> orders);
}

class Product implements IProduct {
    private int id;
    private String name;
    private double price;
    private double shippingCost;

    public Product(int id, String name, double price, double shippingCost) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.shippingCost = shippingCost;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getShippingCost() {
        return shippingCost;
    }
    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }
}

class User implements IUser {
    private int id;
    private String name;
    private double balance;
    private List<Node> orders;

    public User(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.orders = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public List<Node> getOrders() {
        return orders;
    }
    public void setOrders(List<Node> orders) {
        this.orders = orders;
    }
}

class Company implements ICompany {
    private List<Node> products;
    private List<IUser> users;

    public Company() {
        this.products = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public List<Node> getProducts() {
        return products;
    }
    public void setProducts(List<Node> products) {
        this.products = products;
    }

    public List<IUser> getUsers() {
        return users;
    }
    public void setUsers(List<IUser> users) {
        this.users = users;
    }

    @Override
    public void addProduct(IProduct product, int quantity) {
        Node node = new Node(product, quantity);
        boolean isNew = true;
        for (int i = 0; i < this.products.size(); i++) {
            if (this.products.get(i).product.getName().equals(node.product.getName())) {
                int value = this.products.get(i).quantity;
                node.quantity += value;
                this.products.set(i, node);
                isNew = false;
            }
        }

        if (isNew)
            this.products.add(node);
    }

    @Override
    public void addUser(IUser user) {
        this.users.add(user);
    }

    @Override
    public void makeOrder(List<Node> products, IUser user) {
        int total = this.products.size();
        double shippingCost = products.stream().reduce((n, a) -> {if (a.product.getShippingCost() > n.product.getShippingCost()) {return a;} return n;}).map(n -> n.product.getShippingCost()).get();
        double order = products.stream().map(n -> (n.quantity * n.product.getPrice())).reduce((n, a) -> n + a).get().doubleValue() + shippingCost;
        if (user.getBalance() >= order) {
            for (Node actual : products) {
                for (int i = 0; i < total; i++) {
                    Node node = this.products.get(i);
                    if (actual.product.getId() == node.product.getId()) {
                        if (node.quantity >= actual.quantity) {
                            node.quantity -= actual.quantity;
                            this.products.set(i, node);
                        }
                    }
                }   
            }

            this.users = this.users.stream().map(u -> {
                if (u.getId() == user.getId())
                    u.setBalance(u.getBalance() - order);
                return u;
            }).collect(Collectors.toList());
            user.setOrders(products);
        }
    }
}

public class SolutionProducts {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\clientes\\bradesco\\dev\\git\\threads\\src\\main\\java\\com\\example\\threads\\test\\input.txt"));
        PrintWriter writer = new PrintWriter("C:\\clientes\\bradesco\\dev\\git\\threads\\src\\main\\java\\com\\example\\threads\\test\\output.txt", "UTF-8");
        Company company = new Company();
        int productCount = Integer.parseInt(reader.readLine().trim());
        for (int i = 0; i < productCount; i++) {
            String[] a = reader.readLine().trim().split(",");
            company.addProduct(new Product(Integer.parseInt(a[0]), a[1], Integer.parseInt(a[2]), Integer.parseInt(a[3])), Integer.parseInt(a[4]));
        }
        int userCount = Integer.parseInt(reader.readLine().trim());
        for (int i = 0; i < userCount; i++) {
            String[] a = reader.readLine().trim().split(",");
            company.addUser(new User(Integer.parseInt(a[0]), a[1], Integer.parseInt(a[2])));
        }
        int orderCount = Integer.parseInt(reader.readLine().trim());
        for (int i = 0; i < orderCount; i++) {
            String[] a = reader.readLine().trim().split(",");
            IUser u = company.getUsers().stream().filter(x -> x.getId() == Integer.parseInt(a[0])).findFirst().orElse(null);
            if(u == null) {
                throw new Exception("User not found");
            }
            List<Node> orderProducts = new ArrayList<>();
            for (int j = 1; j < a.length; j++) {
                String[] b = a[j].split("\\|");
                Node curr = company.getProducts().stream().filter(x -> x.product.getId() == Integer.parseInt(b[0])).findFirst().orElse(null);
                if(curr != null) {
                    Node c = new Node(curr.product, Integer.parseInt(b[1]));
                    orderProducts.add(c);
                }   
            }
            company.makeOrder(orderProducts, u);
        }
        List<String> output = company.getProducts().stream()
            .sorted(Comparator.comparing(x -> x.product.getId()))
            .map(x -> x.product.getName() + ":" + x.quantity)
            .collect(Collectors.toList());
        // System.out.println(String.join("\n", output));
        writer.write(String.join("\n", output));
        writer.flush();
        writer.close();
    }
}