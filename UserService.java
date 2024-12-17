package edu.famu.ecommerece.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.remoteconfig.internal.TemplateResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private Firestore firestore;
    public UserService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public TemplateResponse.UserResponse registerUser(Users user){
        try
        {
            ApiFuture<WriteResult> future = firestore.collection("users").document(user.getUserId()).set(user);
            future.get();
            return new TemplateResponse.UserResponse("User registered successfully!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new TemplateResponse.UserResponse("User registration failed: " + e.getMessage());
        }

    }

    public Users getUserDetails(String userId) {
        try
        {
            ApiFuture<DocumentSnapshot> future = firestore.collection("users").document(userId).get();
            DocumentSnapshot document = future.get();

            if (document.exists())
            {
                return document.toObject(Users.class);
            }
            else
            {
                throw new Exception("User not found.");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public TemplateResponse.UserResponse updateUserProfile(String userId, Users user) {
        try {
            ApiFuture<WriteResult> future = firestore.collection("users").document(userId).set(user);
            future.get();
            return new TemplateResponse.UserResponse("User profile updated successfully!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new TemplateResponse.UserResponse("Profile update failed: " + e.getMessage());
        }
    }
}
