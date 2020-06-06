/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity {
  EditText userEditText;
  EditText passEditText;

  public void redirectUser() {
    if (ParseUser.getCurrentUser() !=  null) {
      Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
      startActivity(intent);

    }
  }
public void signUp(View view) {
  ParseUser.logInInBackground(userEditText.getText().toString(), passEditText.getText().toString(), new LogInCallback() {
    @Override
    public void done(ParseUser user, ParseException e) {
      if (e == null) {
        Log.i("Login", "Success");
        redirectUser();

      }else {
        ParseUser newUser = new ParseUser();
        newUser.setUsername(userEditText.getText().toString());
        newUser.setPassword(passEditText.getText().toString());

        newUser.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {
              Log.i("Signup", "Done");
              redirectUser();
            } else {
              Toast.makeText(MainActivity.this, e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();
            }

          }
        });
      }
    }
  });

}

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    userEditText = findViewById(R.id.userEditText);
    passEditText = findViewById(R.id.passEditText);

    setTitle("Twitter : Login");
    redirectUser();



    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}