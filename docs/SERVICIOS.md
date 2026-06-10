

## Servicios


Cuando el controlador empieza a crecer y tener cálculos y lógica de negocio pesada con miles de líneas de código lo ideal es mover los cálculos y lógica pesada a una clase Service.

Objetivo es que las clases Controller tienen que gestionar peticiones HTTP y devolver la vista, no hacer cálculos pesados.

OrderController --> OrderService --> OrderRepository

````java
import org.springframework.scheduling.annotation.Scheduled;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderLineRepository orderLineRepository;
    
    public Order recalculate(Order order) {

    }
}

````