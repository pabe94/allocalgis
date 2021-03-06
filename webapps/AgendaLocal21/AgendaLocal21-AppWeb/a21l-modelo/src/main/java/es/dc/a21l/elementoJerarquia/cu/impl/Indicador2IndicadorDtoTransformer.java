/*
 * El presente software ha sido desarrollado por Balidea Consulting & Programming, con control de calidad y previo dise�o por Enxenio S.L., para la Diputaci�n de A Coru�a en el seno del proyecto LOURED (http://loured.es), inclu�do en el Plan AVANZA Local 2009-2011 del Ministerio de Industria, Energ�a y Turismo del Gobierno de Espa�a.  
 * Su distribuci�n se realiza bajo las condiciones establecidas por la licencia European Public License (EUPL), versi�n 1.1 o posteriores (http://http://joinup.ec.europa.eu/software/page/eupl/licence-eupl)
 */
package es.dc.a21l.elementoJerarquia.cu.impl;

import org.dozer.Mapper;

import es.dc.a21l.base.cu.impl.TransformadorEntidadBase2DtoBase;
import es.dc.a21l.elementoJerarquia.cu.IndicadorDto;
import es.dc.a21l.elementoJerarquia.modelo.Indicador;

public class Indicador2IndicadorDtoTransformer extends TransformadorEntidadBase2DtoBase<Indicador, IndicadorDto> {

	public Indicador2IndicadorDtoTransformer(Mapper mapper) {
		super(mapper);
	}
	
	@Override
	protected void aplicaPropiedadesEstendidas(Indicador origen,IndicadorDto destino) {
		super.aplicaPropiedadesEstendidas(origen, destino);
		if(origen.getCategoria()!=null){
			destino.setIdCategoria(origen.getCategoria().getId());
		}else{
			destino.setIdCategoria(null);
		}
	}

}
