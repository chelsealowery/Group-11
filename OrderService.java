package edu.famu.ecommerece.services;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private Firestore firestore;
    private CartService cartService;

    public OrderService(CartService cartService)
    {
        this.firestore = FirestoreClient.getFirestore();
        this.cartService = cartService;
    }

    public OrderResponse placeOrder(String userId, Order orderRequest)
    {
        try
        {
            Cart userCart = cartsService.getCartByUserId(userId);
            if (userCart == null || userCart.getProducts().isEmpty())
            {
                throw new Exception("Cart is empty. Cannot place an order.");
            }

            Order newOrder = new Order();
            newOrder.setOrderId(firestore.collection("orders").document().getId());
            newOrder.setUserId(userId);
            newOrder.setProducts(userCart.getProducts());
            newOrder.setTotalAmount(cartsService.calculateCartTotal(userId));
            newOrder.setOrderStatus("Pending");
            newOrder.setShippingInfo(orderRequest.getShippingInfo());
            newOrder.setCreatedAt(orderRequest.getCreatedAt());

            firestore.collection("orders").document(newOrder.getOrderId()).set(newOrder).get();

            cartsService.clearCart(userId);

            return new OrderResponse("Order placed successfully!", newOrder.getOrderId());
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return new OrderResponse("Failed to place order: " + e.getMessage());
        }
    }

    public List<Order> getOrdersByUserId(String userId)
    {
        try
        {
            ApiFuture<QuerySnapshot> future = firestore.collection("orders")
                    .whereEqualTo("userId", userId)
                    .get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            List<Order> userOrders = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents)
            {
                userOrders.add(document.toObject(Order.class));
            }
            return userOrders;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list if error occurs
        }
    }

    public Order getOrderById(String orderId)
    {
        try
        {
            ApiFuture<DocumentSnapshot> future = firestore.collection("orders").document(orderId).get();
            DocumentSnapshot document = future.get();

            if (document.exists())
            {
                return document.toObject(Order.class);
            }
            else
            {
                throw new Exception("Order not found.");
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public OrderResponse cancelOrder(String orderId)
    {
        try {
            Order order = getOrderById(orderId);
            if (order == null)
            {
                throw new Exception("Order not found.");
            }

            if (!"Pending".equalsIgnoreCase(order.getOrderStatus()))
            {
                throw new Exception("Only pending orders can be canceled.");
            }

            order.setOrderStatus("Canceled");
            firestore.collection("orders").document(orderId).set(order).get();

            return new OrderResponse("Order canceled successfully!");
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return new OrderResponse("Failed to cancel order: " + e.getMessage());
        }
    }
}