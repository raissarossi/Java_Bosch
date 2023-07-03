package application;

import components.CartButton;
import entities.Restaurant;
import classes.*;
import entities.Usuario;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class General extends JFrame {
    private Login login;
    private Home home;
    private Register register;
    private RestaurantsList restaurantsList;
    private MenuRest menuRest;
    private ArrayList<Restaurant> restaurants;
    private UserManager userManager;
    private CartButton cartButton;

    public General(){
        setSize(500, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        initializeComponents();
        addAllListeners();
        setAllRestaurants();
        setAllFoods();

        setContentPane(home);
        home.setVisible(true);
        setVisible(true);
    }

    private void initializeComponents() {
        login = new Login();
        home = new Home();
        register = new Register();
        restaurantsList = new RestaurantsList();
        menuRest = new MenuRest();
        restaurants = new ArrayList<>();
        userManager = new UserManager();
        cartButton = new CartButton();
    }

    private void setAllRestaurants() {
        restaurants.add(new Restaurant(0, "RaissasFoood"));
        restaurants.add(new Restaurant(1, "Dadora"));
    }

    private void setAllFoods() {
        restaurants.get(0).addFood("Lanchinho", 10F);
        restaurants.get(0).addFood("torta de limão", 20.99F);
        restaurants.get(1).addFood("torta de limão", 20.99F);
    }

    private void addAllListeners() {
        home.goLogin.addActionListener(e -> {
            home.setVisible(false);
            setContentPane(login);
            login.setVisible(true);
        });
        home.goRegister.addActionListener(e -> {
            home.setVisible(false);
            setContentPane(register);
            register.setVisible(true);
        });

        login.backHome.addActionListener(e -> {
            login.setVisible(false);
            setContentPane(home);
            home.setVisible(true);
        });
        login.enterBtn.addActionListener(e -> {
            String name = login.getName();
            String password = login.getPassword();
            if (userManager.isRegisteredUser(name, password)) {
                login.setVisible(false);
                setContentPane(restaurantsList);
                restaurantsList.setRestaurantes(restaurants);
                restaurantsList.refresh();
                restaurantsList.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não registrado!");
            }
        });

        register.enterBtn.addActionListener(e -> {
            register.setVisible(false);
            setContentPane(home);
            home.setVisible(true);
        });
        register.backHome.addActionListener(e -> {
            register.setVisible(false);
            setContentPane(home);
            home.setVisible(true);
        });
        register.enterBtn.addActionListener(e -> {
            String name = register.getName();
            String password = register.getPassword();
            int coordenadaX = register.getCoordenadaX();
            int coordenadaY = register.getCoordenadaY();
            userManager.createUser(name, password, coordenadaX, coordenadaY);
            System.out.println("Foi");
        });

        restaurantsList.listaRestaurants.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }

            String nomeRest = restaurantsList.listaRestaurants.getSelectedValue();
            for (Restaurant rest : restaurants) {
                if (Objects.equals(nomeRest, rest.getName())) {
                    menuRest.setRestaurant(rest);
                    break;
                }
            }

            setContentPane(menuRest);
            menuRest.refresh();
            restaurantsList.setVisible(false);
            menuRest.setVisible(true);
        });

        menuRest.backRest.addActionListener(e -> {
            menuRest.setVisible(false);
            setContentPane(restaurantsList);
            restaurantsList.setVisible(true);
        });
        menuRest.addButton.addActionListener(e -> {
            String selectedFood = menuRest.listaComidas.getSelectedValue();
            if (selectedFood != null) {
                System.out.println("Adicionou: " + selectedFood);
            }
        });

        cartButton.addActionListener(e -> {
            System.out.println("Carrinho");
        });

    }

}