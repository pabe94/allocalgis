/*
 * El presente software ha sido desarrollado por Balidea Consulting & Programming, con control de calidad y previo dise�o por Enxenio S.L., para la Diputaci�n de A Coru�a en el seno del proyecto LOURED (http://loured.es), inclu�do en el Plan AVANZA Local 2009-2011 del Ministerio de Industria, Energ�a y Turismo del Gobierno de Espa�a.  
 * Su distribuci�n se realiza bajo las condiciones establecidas por la licencia European Public License (EUPL), versi�n 1.1 o posteriores (http://http://joinup.ec.europa.eu/software/page/eupl/licence-eupl)
 */
package es.dc.a21l.visualizacion.cu.impl;

import org.dozer.Mapper;

import es.dc.a21l.base.cu.impl.TransformadorEntidadBase2DtoBase;
import es.dc.a21l.visualizacion.cu.EstiloVisualizacionMapaDto;
import es.dc.a21l.visualizacion.modelo.EstiloVisualizacionMapa;
import es.dc.a21l.visualizacion.modelo.RangosVisualizacionMapa;

public class EstiloVisualizacionMapa2EstiloVisualizacionMapaDtoTransformer extends TransformadorEntidadBase2DtoBase<EstiloVisualizacionMapa, EstiloVisualizacionMapaDto> {
	private Mapper mapper;
	
	public EstiloVisualizacionMapa2EstiloVisualizacionMapaDtoTransformer(Mapper mapper) {
		super(mapper);
		this.mapper = mapper;
	}
	
	@Override
	protected void aplicaPropiedadesEstendidas(EstiloVisualizacionMapa origen,EstiloVisualizacionMapaDto destino) {
		super.aplicaPropiedadesEstendidas(origen, destino);
	}

}
