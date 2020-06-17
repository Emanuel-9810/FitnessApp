package com.example.myworkoutapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
  private TextView displayEmail;
  private TextView displayFullName;
  private EditText displayAge;
  private EditText displayWeight;
  private EditText displayHeight;
  private Button confirmButton;
  private Button signOutButton;
  private User currentUser;

  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private DocumentReference docRef;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_profiles, container, false);
    setUp(root);
    displayData();
    confirmButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        displayAndSaveEnteredData();
        Toast.makeText(getContext(), "Update Successful", Toast.LENGTH_SHORT).show();
      }
    });
    signOutButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), MainActivity.class));
        Toast.makeText(getContext(), "Sign out Successful", Toast.LENGTH_SHORT).show();
      }
    });
    return root;
  }

  private void setUp(View root) {
    displayEmail = root.findViewById(R.id.displayEmail);
    displayFullName = root.findViewById(R.id.displayFullName);
    displayAge = root.findViewById(R.id.displayAge);
    displayWeight = root.findViewById(R.id.displayWeight);
    displayHeight = root.findViewById(R.id.displayHeight);
    confirmButton = root.findViewById(R.id.confirm_button);
    signOutButton = root.findViewById(R.id.signOut_button);
    currentUser = MainActivity.getCurrentUser();
    docRef = db.collection("users").document(currentUser.getEmail());
  }

  private void displayData() {
    displayEmail.setText(currentUser.getEmail());
    displayFullName.setText(currentUser.getfullName());
    displayAge.setText(Integer.toString(currentUser.getAge()));
    displayWeight.setText(Integer.toString(currentUser.getWeight()));
    displayHeight.setText(Integer.toString(currentUser.getHeight()));
  }

  private void displayAndSaveEnteredData() {
    int age = Integer.parseInt(displayAge.getText().toString());
    int weight = Integer.parseInt(displayWeight.getText().toString());
    int height = Integer.parseInt(displayHeight.getText().toString());
    currentUser.setAge(age);
    currentUser.setWeight(weight);
    currentUser.setHeight(height);
    docRef.update("age", age);
    docRef.update("weight", weight);
    docRef.update("height", height);
  }

}
