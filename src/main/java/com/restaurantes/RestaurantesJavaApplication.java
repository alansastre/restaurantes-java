package com.restaurantes;

import com.restaurantes.model.*;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.EmployeeRepository;
import com.restaurantes.repository.RestaurantRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Una clase con @Entity equivale a una tabla de base de datos
// Un objeto equivale a una fila en una tabla de base de datos

@SpringBootApplication
public class RestaurantesJavaApplication {

    public static void main(String[] args) {

        var context = SpringApplication.run(RestaurantesJavaApplication.class, args);

        // obtener los repositorios para poder hacer operaciones de base de datos con ellos
        // Los repositorios nos dan las operaciones CRUD (findAll, findById, save, delete)
        RestaurantRepository restaurantRepository = context.getBean(RestaurantRepository.class);
        EmployeeRepository employeeRepository = context.getBean(EmployeeRepository.class);
        DishRepository dishRepository = context.getBean(DishRepository.class);

        // crear un objeto restaurante: new
        Restaurant nuevoRestaurante = new Restaurant(); // objeto
        nuevoRestaurante.setName("Paco Bar");
        nuevoRestaurante.setAveragePrice(20.33);
        nuevoRestaurante.setNumberEmployees(5);

        // guardar el restaurante en base de datos usando el repositorio: .save()
        restaurantRepository.save(nuevoRestaurante);


        // Opción 1: crear un objeto vacío:
        Restaurant rest = new Restaurant();

        // Opción 2: crear un objeto con datos:
        Restaurant rest2 = new Restaurant("100 montaditos", 22.22, 5);
        restaurantRepository.save(rest2);

        // Opción 3: crearlo vacío y actualizarlo con métodos set
        Restaurant rest3 = new Restaurant();
        rest3.setName("Rest3");
        rest3.setAveragePrice(50.22);
        restaurantRepository.save(rest3);


        // Crear un empleado y guardarlo en base datos
        Employee emp1 = new Employee();
        employeeRepository.save(emp1);

        Employee emp2 = new Employee("Pepe", "García", 30, "7776645R");
        employeeRepository.save(emp2);

        System.out.println(emp1);
        System.out.println(emp2);

        // obtener todos los restaurantes de base de datos
        // SELECT * from restaurantes;
        List<Restaurant> restaurantes = restaurantRepository.findAll();
        // System.out.println(restaurantes);
//        for (int i = 0; i < restaurantes.size(); i++) { // size() nos dice el número de elementos en la lista
//            System.out.println(restaurantes.get(i));
//        }
        for (Restaurant restaurant : restaurantes) { // bucle foreach itera uno a uno los restaurantes sin crear un index
            System.out.println(restaurant);
        }


        List<Employee> empleados = employeeRepository.findAll();
        // System.out.println(empleados);
        for (Employee empleado : empleados) {
            System.out.println(empleado);
        }

        // saveAll
        Restaurant r1 = new Restaurant("R1", 10.0, 3);
        Restaurant r2 = new Restaurant("R2", 15.0, 4);

        // opción clásica para crear lista:
        List<Restaurant> sitiosParaComer = new ArrayList<>(); // crear una lista vacía
        sitiosParaComer.add(r1); // añadir un restaurante a la lista
        sitiosParaComer.add(r2); // añadir un restaurante a la lista
        List<String> alumnos = new ArrayList<>(); // crear una lista vacía
        List<Double> precios = new ArrayList<>(); // crear una lista vacía

        // opción moderna para crear lista:
        List<Restaurant> sitiosGuaposParaComer = List.of(r1, r2);
        restaurantRepository.saveAll(sitiosGuaposParaComer);


        // count () para contar cuantas filas hay en la tabla devuelve long
        long numeroRestaurantes = restaurantRepository.count();
        if (numeroRestaurantes > 0) {
            System.out.println("Hay para comer, todos tranquis, hay " + numeroRestaurantes + " restaurantes");
        } else {
            System.out.println("nos morimos de hambre");
        }

        // existById devuelve boolean
        long id = 1;
        boolean existe = restaurantRepository.existsById(id);
        if (existe)
            System.out.println("restaurante 1 sí existe");
        else
            System.out.println("restaurante 1 no existe");
        // restaurantRepository.existsById(2L); // Long ;



        // deleteAll borrar todas las filas de la tabla
        //restaurantRepository.deleteAll();



        // deleteById borrar una fila indicando su id, 1, 2, 3
        Long idABorrar = 1L;
        restaurantRepository.deleteById(idABorrar); // hard coded
        System.out.println("restaurante con id " + idABorrar + " existe : " + restaurantRepository.existsById(idABorrar));
        // restaurantRepository.deleteById(rest.getId());
        // delete, borra pasando el objeto
        restaurantRepository.delete(r2); // le pasamos un objeto restaurante
        // restaurantRepository.deleteByName("R1"); // requiere metodo personalizado en el repositorio





        // findById traer un restaurante/empleado por su id
        Long idABuscar = 2L;
        // Restaurant restaurantFromDatabase = restaurantRepository.findById(idABuscar);
        Optional<Restaurant> restaurantFromDatabase = restaurantRepository.findById(idABuscar);
        // var restaurantFromDatabase = restaurantRepository.findById(idABuscar);
        if (restaurantFromDatabase.isPresent()) {
            Restaurant restaurante2 = restaurantFromDatabase.get();
            System.out.println(restaurante2);
        }


        // Crear un restaurante español
        Restaurant restaurantSpain = new Restaurant();
        //restaurantSpain.setFoodType("Español");
        restaurantSpain.setName("La Taberna");
        restaurantSpain.setFoodType(FoodType.SPANISH);
        restaurantRepository.save(restaurantSpain);
        System.out.println(restaurantSpain);


        // crear un restaurante de comida japonesa
        Restaurant restaurantJapan =  new Restaurant();
        restaurantJapan.setFoodType(FoodType.JAPANESE);
        restaurantRepository.save(restaurantJapan);
        System.out.println(restaurantJapan);


        // Probar a intentar otro tipo de comida y ver que no deja
        //Restaurant restaurantItalian =  new Restaurant();
        //restaurantItalian.setFoodType(FoodType.ITALIAN); // da fallo porque no existe el valor ITALIAN en el enum FoodType


        // Probar fecha de startDAte del restuarante
        Restaurant smashBurguer = new Restaurant();
        smashBurguer.setName("Smash Burguer Madrid");
        smashBurguer.setStartDate(LocalDate.now()); // fecha actual
        restaurantRepository.save(smashBurguer);
        System.out.println(smashBurguer);

        // fecha futura
        Restaurant sidreria = new Restaurant();
        sidreria.setName("Sidreria");
        sidreria.setStartDate(LocalDate.of(2026, 6, 25));
        restaurantRepository.save(sidreria);
        System.out.println(sidreria);
        // LocalDate solo da año mes y día
        // LocalTime solo da hora minuto y segundo
        // LocalDateTime da año mes día hora minuto segundo


        // MANY TO ONE - ASOCIAR UN RESTAURANTE A DOS EMPLEADOS
        // Paso 1. crear restaurante y guardarlo
        Restaurant dominosPizza = new Restaurant();
        dominosPizza.setName("DominosPizza");
        dominosPizza.setFoodType(FoodType.SPANISH); // ponemos SPANISH para la prueba, aunque no lo sea
        restaurantRepository.save(dominosPizza);
        // paso 2. crear empleados, setRestaurant y guardar
        Employee juanito = new Employee();
        juanito.setFirstName("Juanito");
        juanito.setRestaurant(dominosPizza);
        juanito.setAge(18); // NO cumple el filtro de findByAgeGreaterThanEqual
        employeeRepository.save(juanito);
        System.out.println(juanito); // imprime el id del restaurante en el toSTring
        Employee patricia = new Employee();
        patricia.setFirstName("patricia");
        patricia.setRestaurant(dominosPizza);
        patricia.setAge(35); // SÍ cumple el filtro de findByAgeGreaterThanEqual
        employeeRepository.save(patricia);
        System.out.println(patricia);
        // Bucle for para iterar sobre todos los empleados imprimiendo el nombre del empleado y el nombre de su restaurante
        // si lo tiene
        List<Employee> trabajadores = employeeRepository.findAll();
        for (Employee trabajador : trabajadores) {
//            System.out.println(
//                    trabajador.getFirstName() +
//                    " trabaja en " +
//                    (trabajador.getRestaurant() != null ? trabajador.getRestaurant().getName() : "ningún sitio"));

            if (trabajador.getRestaurant() != null)  {
                System.out.println(trabajador.getFirstName() + " trabaja en " + trabajador.getRestaurant().getName());
            } else {
                System.out.println(trabajador.getFirstName() + " trabaja en ningún sitio");
            }
        }
        // probar a filtrar por nombre de restaurante
        List<Employee> empleadosDominos = employeeRepository.findByRestaurantName("DominosPizza");
        System.out.println(empleadosDominos);

        System.out.println("FILTRAR EMPLEADOS POR TIPO DE COMIDA DE RESTAURANTE:");
        for (var e : employeeRepository.findByRestaurant_FoodType(FoodType.SPANISH))
            System.out.println(e);

        System.out.println("FILTRAR EMPLEADOS POR EDAD MAYOR O IGUAL QUE");
        for (var e : employeeRepository.findByAgeGreaterThanEqual(20))
            System.out.println(e);

        System.out.println("TRAER TODOS LOS EMPLEADOS ORDENADOS POR NOMBRE ASCENDENTE A-Z");
        for (var e : employeeRepository.findByOrderByFirstNameAsc())
            System.out.println(e);

        String nombre = "Alan"; // string normal
        // Text block, string con triple comilla para tener varias líneas, ideal para queries largas en repositorios
        String descripcionLarga = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
                Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                """;

        // CREAR PLATOS Y GUARDARLOS
        Dish plato1 = new Dish(null, "Ensalada", "de puñetazos", 5.0, DishType.STARTER, restaurantSpain);
        Dish plato2 = new Dish(null, "Lentejas", "con chorizo", 8.0, DishType.MAIN, restaurantSpain);
        Dish plato3 = new Dish(null, "Tarta de queso", null, 7.50, DishType.DESSERT, restaurantSpain);
        Dish plato4 = new Dish(null, "Champán", null, 60.0, DishType.DESSERT, restaurantSpain);
        dishRepository.saveAll(List.of(plato1, plato2, plato3, plato4));

        // OPcion  1: crear consultas personalizadas en DishRepository
        // que traiga los platos con precio menor que 10 euros findAllByPrice...
        for (var plato: dishRepository.findByPriceLessThanEqual(7.99))
            System.out.println(plato);
        // que traiga los platos de un restaurante ordenados por precio ascendente findAllBy
        // que traiga aquellos platos que no contengan alergenos


        System.out.println("TRAER PLATOS DE UN RESTAURANTE ORDENADOS POR PRECIO ASCENDENTE:");
        Long restaurantId = restaurantSpain.getId();
        for (var plato: dishRepository.findByRestaurantIdOrderByPrice(restaurantId))
            System.out.println(plato);



        // Opción 2: crear un pedido





        // resumen
        // findAll
        // findById
        // existById
        // count()

        // save()
        // saveAll()

        // deleteById
        // deleteALl


    }

}







