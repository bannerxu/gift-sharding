package top.banner.config.shardingsphere;

import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommonService {

	@Resource
	private MyDataSConfig myDataSConfig;

	public List<Table> list() throws SQLException {
		String sql = "select table_name name, engine engine, table_comment comment, create_time createTime from information_schema.tables where table_schema = (select database())";
		List<Entity> entityList = getConnection(sql);
		return entityList.stream().map(e -> e.toBean(Table.class)).collect(Collectors.toList());
	}


	public CreateTableSql selectTableCreateSql(String tableName) throws SQLException {
		String sql = "SHOW CREATE TABLE " + tableName;
		List<Entity> entityList = getConnection(sql);
		if (!entityList.isEmpty()) {
			Entity e = entityList.get(0);
			log.info(e.getStr("Table"));
			CreateTableSql tsql = new CreateTableSql();
			tsql.setCreateTable(e.getStr("Create Table"));
			tsql.setTable(tableName);
			return tsql;
		}
		return null;
	}

	private List<Entity> getConnection(String sql) throws SQLException {
		DataSource ds = new SimpleDataSource(myDataSConfig.getJdbcUrl(), myDataSConfig.getUsername(), myDataSConfig.getPassword(), myDataSConfig.getDriverClassName());
		Connection connection = ds.getConnection();
		List<Entity> entityList = SqlExecutor.query(connection, sql, new EntityListHandler());
		return entityList;
	}


	public List<String> getlist() throws SQLException {
		List<Table> list = list();
		return list.stream().map(Table::getName).collect(Collectors.toList());

	}

}
