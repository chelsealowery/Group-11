package edu.famu.ecommerece.services;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private Firestore firestore;
    public ProductService()
    {
        this.firestore = FirestoreClient.getFirestore();
    }

    public List<Product> getAllProducts() {
        try
        {
            ApiFuture<QuerySnapshot> future = firestore.collection("products").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            List<Product> products = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents)
            {
                products.add(document.toObject(Product.class));
            }
            return products;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Product getProductById(String productId) {
        try
        {
            ApiFuture<DocumentSnapshot> future = firestore.collection("products").document(productId).get();
            DocumentSnapshot document = future.get();

            if (document.exists())
            {return document.toObject(Product.class);}
            else
            {throw new Exception("Product not found.");}
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public ProductResponse addProduct(Product product) {
        try
        {
            ApiFuture<WriteResult> future = firestore.collection("products").document(product.getProductId()).set(product);
            future.get();
            return new ProductResponse("Product added successfully!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ProductResponse("Failed to add product: " + e.getMessage());
        }
    }

    public ProductResponse updateProduct(String productId, Product product) {
        try {
            ApiFuture<WriteResult> future = firestore.collection("products").document(productId).set(product);
            future.get();
            return new ProductResponse("Product updated successfully!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ProductResponse("Failed to update product: " + e.getMessage());
        }
    }

    public ProductResponse deleteProduct(String productId) {
        try
        {
            ApiFuture<WriteResult> future = firestore.collection("products").document(productId).delete();
            future.get();
            return new ProductResponse("Product deleted successfully!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ProductResponse("Failed to delete product: " + e.getMessage());
        }
    }









}//END OF CLASS
