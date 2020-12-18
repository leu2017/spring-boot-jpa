package com.nombreEmpresa.nombreProyecto.app.jpa.models.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "cliente")
public class ClienteEntity implements Serializable {
	

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El campo nombre no puede estar vacio")
	@Size(min = 3,max = 10,message = "El nombre debe tener un minimo de 4 caracteres y un maximo de 10")
	/*@Pattern(regexp = "[A-Z]{10}")//Para validar una expresion regular*/
	@Column(name = "nombre")
	private String nombre;
	
	@NotEmpty(message = "El campo apellido no puede estar vacio")
	@Size(min = 3,max = 10,message = "El apellido debe tener un minimo de 4 caracteres y un maximo de 10")
	@Column(name = "apellido")
	private String apellido;
	
	@NotEmpty(message = "El campo email no puede estar vacio")
	@Email(message = "correo con formato incorrecto")
	@Column(name = "email")
	private String email;
	
	@NotNull(message = "El campo fecha_nacimiento no puede estar vacio")
	@Column(name = "fecha_nacimiento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date fechaNacimiento;
	
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date createAt;
	
	@Column(name = "foto")
	private String foto;
	
	//mappedBy = "cliente"--->ClienteEntity cliente clase ClienteEntity 
	@OneToMany(mappedBy = "cliente",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Factura> facturas;
	
	public ClienteEntity() {
		facturas=new ArrayList<Factura>();
	}

	public ClienteEntity(Long id, String nombre, String apellido, String email,Date fechaNacimiento, Date createAt,String foto) {
		
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
		this.createAt = createAt;
		this.foto=foto;
		facturas=new ArrayList<Factura>();
	}
	/**
	 * Metodo en la tabla para crear la fecha, y con la anotacion
	 * crear la fecha justo antes de introducirla en la tabla de datos.
	 * 
	 * */
	@PrePersist
	public void prePersist() {
		createAt=new Date();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	public void addFacturas(Factura factura) {
		facturas.add(factura);
	}
	@Override
	public String toString() {
		return "Clase ClienteEntity";
	}
	/**
	 * Este es el numero de serie de esta clase, es static o sea asociado a esta
	 * clase y no a otra
	 */
	private static final long serialVersionUID = -1791159229555835666L;
}
