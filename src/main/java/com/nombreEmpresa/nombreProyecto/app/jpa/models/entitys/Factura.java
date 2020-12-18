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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "factura")
public class Factura implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3576429086598094866L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "observacion")
	private String observacion;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date createAt;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private ClienteEntity cliente;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "factura_id")
	private List<ItemFactura> itemsFactura;
	
	
	public Factura() {
		itemsFactura=new ArrayList<ItemFactura>();
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public ClienteEntity getCliente() {
		return cliente;
	}
	public void setCliente(ClienteEntity cliente) {
		this.cliente = cliente;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public List<ItemFactura> getItemsFactura() {
		return itemsFactura;
	}
	public void setItemsFactura(List<ItemFactura> itemsFactura) {
		this.itemsFactura = itemsFactura;
	}
	public void addItemsFactura(ItemFactura itemFactura) {
		itemsFactura.add(itemFactura);
	}
	public Double granTotal() {
		Double total=0.0;
		int size=itemsFactura.size();
		
		for(int i=0;i<size;i++) {
			total+=itemsFactura.get(i).calcularImporte();
		}
		return total;
		
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
	
}
