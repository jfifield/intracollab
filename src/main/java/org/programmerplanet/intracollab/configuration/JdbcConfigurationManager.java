package org.programmerplanet.intracollab.configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * JDBC based subclass of <code>AbstractConfigurationManager</code> that loads
 * and stores configuration options in the database.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class JdbcConfigurationManager extends AbstractConfigurationManager {

	private static final Log log = LogFactory.getLog(JdbcConfigurationManager.class);

	private static final String OPTION_SELECT_SQL = "SELECT option_key, option_value FROM ic_system_option";
	private static final String OPTION_INSERT_SQL = "INSERT INTO ic_system_option (option_key, option_value) VALUES (?, ?)";
	private static final String OPTION_UPDATE_SQL = "UPDATE ic_system_option SET option_value = ? WHERE option_key = ?";
	private static final String KEY_COLUMN = "option_key";
	private static final String VALUE_COLUMN = "option_value";

	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	/**
	 * @see org.programmerplanet.intracollab.configuration.AbstractConfigurationManager#load()
	 */
	protected Map<String, String> load() {
		TransactionCallback callback = new TransactionCallback() {
			public Object doInTransaction(TransactionStatus status) {
				MapRowCallbackHandler mapRowCallbackHandler = new MapRowCallbackHandler(KEY_COLUMN, VALUE_COLUMN);
				log.info(OPTION_SELECT_SQL);
				jdbcTemplate.query(OPTION_SELECT_SQL, mapRowCallbackHandler);
				return mapRowCallbackHandler.getMap();
			}
		};
		Map configuration = (Map)transactionTemplate.execute(callback);
		return configuration;
	}

	/**
	 * @see org.programmerplanet.intracollab.configuration.AbstractConfigurationManager#store(java.util.Map)
	 */
	protected void store(final Map<String, String> configuration) {
		TransactionCallback callback = new TransactionCallback() {
			public Object doInTransaction(TransactionStatus status) {
				for (Map.Entry<String, String> entry : configuration.entrySet()) {
					saveOption(entry.getKey(), entry.getValue());
				}
				return null;
			}
		};
		transactionTemplate.execute(callback);
	}

	private void saveOption(String key, String value) {
		boolean updated = updateOption(key, value);
		if (!updated) {
			insertOption(key, value);
		}
	}

	private boolean updateOption(String key, String value) {
		log.info(OPTION_UPDATE_SQL);
		int affected = jdbcTemplate.update(OPTION_UPDATE_SQL, new Object[] { value, key });
		return (affected > 0);
	}

	private void insertOption(String key, String value) {
		log.info(OPTION_INSERT_SQL);
		jdbcTemplate.update(OPTION_INSERT_SQL, new Object[] { key, value });
	}

	private static class MapRowCallbackHandler implements RowCallbackHandler {

		private String keyColumn;
		private String valueColumn;
		private Map map = new HashMap();

		public MapRowCallbackHandler(String keyColumn, String valueColumn) {
			this.keyColumn = keyColumn;
			this.valueColumn = valueColumn;
		}

		public Map getMap() {
			return map;
		}

		public void processRow(ResultSet rs) throws SQLException {
			Object key = rs.getObject(keyColumn);
			Object value = rs.getObject(valueColumn);
			map.put(key, value);
		}

	};

}
