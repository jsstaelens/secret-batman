package com.example.testmembership;

public class Utilisateur {

	private int id;
	private String pseudo;
	private String email;
	private String password;
	private int age;
	
	public Utilisateur(int age, String pseudo, String email, String password)
	{
		this.pseudo = pseudo;
		this.email = email;
		this.password = password;
		this.age = age;
	}
	public Utilisateur(int age, String pseudo, String email)
	{
		this.pseudo = pseudo;
		this.email = email;
		this.age = age;
	}
	
	public Utilisateur(int id, int age, String pseudo, String email)
	{
		this.id = id;
		this.pseudo = pseudo;
		this.email = email;
		this.age = age;
	}
	
	
	public Utilisateur(int id, int age, String pseudo, String email, String password)
	{
		this.id = id;
		this.pseudo = pseudo;
		this.email = email;
		this.password = password;
		this.age = age;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getPseudo()
	{
		return this.pseudo;
	}
	
	public void setPseudo(String pseudo)
	{
		this.pseudo = pseudo;
	}
	
	public int getAge()
	{
		return this.age;
	}
	
	public void setAge(int age)
	{
		this.age = age;
	}
	
	public String toString()
	{
		return "id: "+ this.id  + " pseudo: " + this.pseudo;
	}
	
}
