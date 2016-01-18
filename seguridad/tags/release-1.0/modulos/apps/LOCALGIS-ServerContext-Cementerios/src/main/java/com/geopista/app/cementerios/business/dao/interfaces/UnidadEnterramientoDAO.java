package com.geopista.app.cementerios.business.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.geopista.app.cementerios.business.vo.UnidadEnterramiento;
import com.geopista.app.cementerios.business.vo.UnidadEnterramientoExample;

public interface UnidadEnterramientoDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table cementerio.unidadenterramiento
	 * @ibatorgenerated  Thu Jun 02 17:40:09 CEST 2011
	 */
	int deleteByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table cementerio.unidadenterramiento
	 * @ibatorgenerated  Thu Jun 02 17:40:09 CEST 2011
	 */
	Integer insert(UnidadEnterramiento record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table cementerio.unidadenterramiento
	 * @ibatorgenerated  Thu Jun 02 17:40:09 CEST 2011
	 */
	Integer insertSelective(UnidadEnterramiento record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table cementerio.unidadenterramiento
	 * @ibatorgenerated  Thu Jun 02 17:40:09 CEST 2011
	 */
	List selectByExample(UnidadEnterramientoExample example) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table cementerio.unidadenterramiento
	 * @ibatorgenerated  Thu Jun 02 17:40:09 CEST 2011
	 */
	UnidadEnterramiento selectByPrimaryKey(Integer id) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table cementerio.unidadenterramiento
	 * @ibatorgenerated  Thu Jun 02 17:40:09 CEST 2011
	 */
	int updateByPrimaryKeySelective(UnidadEnterramiento record) throws SQLException;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table cementerio.unidadenterramiento
	 * @ibatorgenerated  Thu Jun 02 17:40:09 CEST 2011
	 */
	int updateByPrimaryKey(UnidadEnterramiento record) throws SQLException;

	int selectByLastSeqKey() throws SQLException;
	
	public List selectAll () throws SQLException;
	
	/**
	 * Lista todos los elementos de un cementerio concreto
	 * @return
	 */
	public List selectAllByCemetery (Integer idCementerio) throws SQLException;

}