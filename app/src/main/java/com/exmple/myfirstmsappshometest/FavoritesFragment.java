package com.exmple.myfirstmsappshometest;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exmple.myfirstmsappshometest.adapters.FavoritesNewsRecyclerView;
import com.exmple.myfirstmsappshometest.adapters.OnNewsListener;
import com.exmple.myfirstmsappshometest.models.NewsModel;
import com.exmple.myfirstmsappshometest.viewmodels.NewsListViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;
import java.util.concurrent.Executor;


public class FavoritesFragment extends Fragment implements OnNewsListener {

    private RecyclerView recyclerView;
    private FavoritesNewsRecyclerView favoritesNewsRecyclerViewAdapter;
    private NewsListViewModel newsListViewModel;

    private TextView signInTitle, getSignInSubtext, favoritesTitle;
    private ImageView googleSignInButton;
    private static final int RC_SIGN_IN = 1;


    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        signOut();

        recyclerView.setVisibility(View.INVISIBLE);
        favoritesTitle.setVisibility(View.INVISIBLE);

        signInTitle.setVisibility(View.VISIBLE);
        getSignInSubtext.setVisibility(View.VISIBLE);
        googleSignInButton.setVisibility(View.VISIBLE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);


        recyclerView = rootView.findViewById(R.id.favorites_recyclerview);

        signInTitle = rootView.findViewById(R.id.sign_in_title);
        getSignInSubtext = rootView.findViewById(R.id.sign_in_subtext);
        googleSignInButton = rootView.findViewById(R.id.google_signin_button);
        favoritesTitle = rootView.findViewById(R.id.favorites_title);

        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        newsListViewModel = new ViewModelProvider(this).get(NewsListViewModel.class);

        configureRecyclerView();
        observeAnyChange();
        searchNewsApi("articles");

        return rootView;
    }

    private void signIn() {
        Log.d("TAG", "signIn: ");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                Toast.makeText(getContext(), "Please while we are getting your auth result...", Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                Toast.makeText(getContext(), "Error eccord: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInWithCredential:success");
                            Toast.makeText(getContext(), "Login To Google Successes", Toast.LENGTH_SHORT).show();

                            recyclerView.setVisibility(View.VISIBLE);
                            favoritesTitle.setVisibility(View.VISIBLE);

                            signInTitle.setVisibility(View.INVISIBLE);
                            getSignInSubtext.setVisibility(View.INVISIBLE);
                            googleSignInButton.setVisibility(View.INVISIBLE);

                        } else {
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            String message = task.getException().toString();
                            Toast.makeText(getContext(), "Not Authenticated : " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void observeAnyChange() {

        newsListViewModel.getNews().observe(getViewLifecycleOwner(), new Observer<List<NewsModel>>() {
            @Override
            public void onChanged(List<NewsModel> newsModels) {
                //Observing for any data changed
                if (newsModels != null) {
                    favoritesNewsRecyclerViewAdapter.setmNews(newsModels);
                }
            }
        });
    }

    private void searchNewsApi(String query) {
        newsListViewModel.searchNewsApi(query);
    }

    private void configureRecyclerView() {
        favoritesNewsRecyclerViewAdapter = new FavoritesNewsRecyclerView(this);
        recyclerView.setAdapter(favoritesNewsRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onNewsClick(int position) {
        NewsModel NewsModel = favoritesNewsRecyclerViewAdapter.getSelectedNews(position);

        Uri uri = Uri.parse(NewsModel.getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onFavoritesClick(int position) {

    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(getContext(), "you have signed out from google", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error Occord: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}