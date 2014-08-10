package com.randi.dyned2.view;

import com.randi.dyned2.view.manager.RegisterManager;

public class RegisterScreen extends BasicScreen {

	public RegisterScreen() {
		super("Register");
		add(new RegisterManager());
	}
}