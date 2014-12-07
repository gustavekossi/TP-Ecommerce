package com.persistance;

import java.io.Serializable;

import javax.persistence.*;



@Entity
public class Articles implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7255770081065800548L;
	@Id @GeneratedValue
	private int id;
	private String nom;
	private double prix;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
}




