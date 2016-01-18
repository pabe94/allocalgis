/*
 *
 * Este codigo se distribuye bajo licencia GPL
 * de GNU. Para obtener una c�pia integra de esta
 * licencia acude a www.gnu.org.
 * 
 * Este software se distribuye "como es". AGIL
 * solo  pretende desarrollar herramientas para
 * la promoci�n del GIS Libre.
 * AGIL no se responsabiliza de las perdidas econ�micas o de 
 * informaci�n derivadas del uso de este software.
 */

package org.agil.dxf.reader;

/**Implementacion de la entidad Dxf ATTRIB no contemplada
 * por la version original del visor.
 *
 * Segun la especificacion dxf, una entidad ATTRIB es un atributo visible
 * de un INSERT (Bloque insertado). Cuando una entidad INSERT tiene un grupo que especifica
 * que tiene atributos asociados, todas las entidades Dxf que encontremos hasta
 * la proxima entidad ENDSEQ seran entidades ATTRIB asociadas a ese INSERT.
 *
 */
import org.agil.dxf.math.Point3D;
public class DxfATTRIB extends DxfEntity {

	/**Punto de insercion del texto, definido
	por los grupos (#10,#20,#30)*/
	protected Point3D insertPoint = new Point3D();

	/**Altura del texto visible en pantalla, definida por los grupos #40*/
	protected double textHeight;

	/**Nombre del atributo, grupo #1*/
	protected String attribName;

	/**Valor del atributo, grupo #2*/
	protected String attribValue;


	public String getAttribName(){
		return attribName;
	}

	public String getAttribValue(){
		return attribValue;
	}

	/**Asigna valor para los grupos cuyo tipo de dato sea String:
	 * #2 (nombre del atributo) #1(valor)
	 */
	public boolean setGroup(short groupNumber,String value){

		switch(groupNumber){

			case 1:
				attribValue = value;
				return true;
			case 2:
				attribName = value;
				return true;
			default:
				return super.setGroup(groupNumber, value);
		}

	}

	/**Asigna valor para los grupos cuyo tipo de dato sea un float:
	 * De momento son #10,#20,#30 (coordenadas), #40 (altura del texto)
	 */
	public boolean setGroup(short groupNumber,double value){
		switch(groupNumber){

			case 10:
			case 20:
			case 30:
				setCoord(insertPoint, groupNumber/10, value);
				return true;
			case 40:
				textHeight=value;
				return true;
			 default:
				return super.setGroup(groupNumber, value);
		 }
	}

	public void convert(DxfConverter converter, DxfFile dxf, Object collector) {
		converter.convert(this, dxf, collector);
	}




}