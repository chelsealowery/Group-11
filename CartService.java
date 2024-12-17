package edu.famu.ecommerece.services;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

@Service
public class CartService
{
    private Firestore firestore;
    private ProductService productService;

    public CartService(ProductService productService)
    {
        this.firestore = FirestoreClient.getFirestore();
        this.productService = productService;
    }

    public Cart getCartByUserId(String userId)
    {
        try
        {
            ApiFuture<DocumentSnapshot> future = firestore.collection("carts").document(userId).get();
            DocumentSnapshot document = future.get();

            if (document.exists())
            {
                return document.toObject(Cart.class);
            }
            else
            {
                return new Cart();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public CartResponse addProductToCart(String userId, String productId, int quantity)
    {
        try
        {
            Cart cart = getCartByUserId(userId);
            Product product = productService.getProductById(productId);

            if (product == null) {
                throw new Exception("Product not found.");
            }

            HashMap<String, Integer> products = cart.getProducts();
            products.put(productId, products.getOrDefault(productId, 0) + quantity);

            cart.setProducts(products);
            cart.setTotalAmount(calculateCartTotal(userId));

            firestore.collection("carts").document(userId).set(cart).get();
            return new CartResponse("Product added to cart successfully!");
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return new CartResponse("Failed to add product to cart: " + e.getMessage());
        }
    }

    public CartResponse removeProductFromCart(String userId, String productId)
    {
        try
        {
            Cart cart = getCartByUserId(userId);

            if (cart.getProducts().containsKey(productId))
            {
                cart.getProducts().remove(productId);
                cart.setTotalAmount(calculateCartTotal(userId));


                firestore.collection("carts").document(userId).set(cart).get();
                return new CartResponse("Product removed from cart successfully!");
            }
            else
            {
                throw new Exception("Product not found in cart.");
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return new CartResponse("Failed to remove product from cart: " + e.getMessage());
        }
    }

    public double calculateCartTotal(String userId)
    {
        try
        {
            Cart cart = getCartByUserId(userId);
            double total = 0.0;

            for (Map.Entry<String, Integer> entry : cart.getProducts().entrySet())
            {
                Product product = productService.getProductById(entry.getKey());
                if (product != null)
                {
                    total += product.getPrice().doubleValue() * entry.getValue();
                }
            }
            return total;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return 0.0;
        }
    }

    public CartResponse clearCart(String userId)
    {
        try
        {
            Cart cart = new Cart();
            firestore.collection("carts").document(userId).set(cart).get();
            return new CartResponse("Cart cleared successfully!");
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return new CartResponse("Failed to clear cart: " + e.getMessage());
        }
    }
}
