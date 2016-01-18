package com.geopista.protocol.inventario;

import java.io.Serializable;

public class InventarioEIELBean implements Serializable{
	
	private String nombreEIEL;
	private String estadoEIEL;
	private String gestionEIEL;
	private String tipoEIEL;
	private String unionClaveEIEL;
	private long idBien;
	private Integer epigInventario;
	private String titularidadMunicipal;
	private Integer idMunicipio;
	
	
	public long getIdBien() {
		return idBien;
	}
	public void setIdBien(long idBien) {
		this.idBien = idBien;
	}
	public Integer getEpigInventario() {
		return epigInventario;
	}
	public void setEpigInventario(Integer epigInventario) {
		this.epigInventario = epigInventario;
	}
	public String getTitularidadMunicipal() {
		return titularidadMunicipal;
	}
	public void setTitularidadMunicipal(String titularidadMunicipal) {
		this.titularidadMunicipal = titularidadMunicipal;
	}
	public Integer getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	public String getUnionClaveEIEL() {
		return unionClaveEIEL;
	}
	public void setUnionClaveEIEL(String unionClaveEIEL) {
		this.unionClaveEIEL = unionClaveEIEL;
	}
	public String getNombreEIEL() {
		return nombreEIEL;
	}
	public void setNombreEIEL(String nombreEIEL) {
		this.nombreEIEL = nombreEIEL;
	}
	public String getEstadoEIEL() {
		return estadoEIEL;
	}
	public void setEstadoEIEL(String estadoEIEL) {
		this.estadoEIEL = estadoEIEL;
	}
	public String getGestionEIEL() {
		return gestionEIEL;
	}
	public void setGestionEIEL(String gestionEIEL) {
		this.gestionEIEL = gestionEIEL;
	}
	public String getTipoEIEL() {
		return tipoEIEL;
	}
	public void setTipoEIEL(String tipoEIEL) {
		this.tipoEIEL = tipoEIEL;
	}
	

}