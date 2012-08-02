package br.com.digilabs.domain;

import javax.persistence.Entity;

@Entity
public class Carro extends BasicEntity {

	private static final long serialVersionUID = 8082041738138308985L;
	
	private String modelo;
	private Cor cor;
	
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Cor getCor() {
		return cor;
	}
	public void setCor(Cor cor) {
		this.cor = cor;
	}
	
	

}
