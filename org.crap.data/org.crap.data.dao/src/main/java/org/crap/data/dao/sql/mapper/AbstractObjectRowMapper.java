package org.crap.data.dao.sql.mapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.jdbc.core.RowMapper;

/**
 * @since JDK 1.7
 * 
 * @author Crap
 * 
 * @version 1.4.3
 * 
 * 
 */
public abstract class AbstractObjectRowMapper<T> implements RowMapper<T> {
			
	private final Class<? extends T> mappedClass;

	private final HashMap<String, Integer> rsMap = new HashMap<String, Integer>();
	
	public AbstractObjectRowMapper(Class<? extends T> mappedClass) {
		this.mappedClass = mappedClass;
	}
	
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T entity = null;
		
		try {
			entity = mappedClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		ResultSetMetaData rsmd = rs.getMetaData();
		
		int columnCount = rsmd.getColumnCount();
		
		/*for (int index = 1; index <= columnCount; ++index) {
			rsMap.put(rsmd.getColumnLabel(index).toLowerCase().replace("_", ""), rs.getObject(index));
		}*/
		
		for (int index = 1; index <= columnCount; ++index) {
			rsMap.put(rsmd.getColumnLabel(index).toLowerCase().replace("_", ""), index);
		}
		
		Method[] methods = mappedClass.getMethods();
		for (Method method : methods) {
			String methodName = method.getName().replaceFirst("set", "");
			if(method.getName().startsWith("set") && hasColumn(methodName)){
				try {
					method.invoke(entity, rs.getObject(rsMap.get(methodName.toLowerCase())));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		/*Field [] fields =  mappedClass.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		for (Field field : fields) {
			try {
				
				if(hasColumn(field.getName())) {
					field.set(entity, rsMap.get(field.getName().toLowerCase()));
				}
				if(hasColumn(field.getName())) {
					field.set(entity, getTypeOfObject(rs, rsMap.get(field.getName().toLowerCase()), field.getType()));
				}
			} catch (IllegalArgumentException e) {
				log.error(e);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				log.error(e);
				e.printStackTrace();
			}
		}*/
		
		mapRowHandler(rs, entity);
		return entity;
	}
	
	protected Object getTypeOfObject(ResultSet rs, int index, Class<?> cls) throws SQLException{
		
		if(cls.equals(Integer.class) || cls.toString().equals("int"))
			return rs.getInt(index);

		return rs.getObject(index);
	}
	
	protected boolean hasColumn(String columnName){
		return rsMap.containsKey(columnName.toLowerCase());
	}
	
	protected abstract void mapRowHandler(ResultSet rs, T entity) throws SQLException;
	
	public Class<? extends T> getMappedClass() {
		return mappedClass;
	}
}
