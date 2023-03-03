package top.banner.config;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Range;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import top.banner.utils.DateUtils;

import java.util.*;

public class YearMonthShardingAlgorithm implements StandardShardingAlgorithm<Date> {

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
		StringBuilder resultTableName = new StringBuilder();
		String logicTableName = shardingValue.getLogicTableName();
		//表名精确匹配，表名加上截取的时间
		resultTableName.append(logicTableName)
				//时间戳秒级转毫秒级转成date类型
				.append("_").append(DateUtils.format(shardingValue.getValue(), DateUtils.YEAR_MONTH_NUMBER));
		return ShardingAlgorithmTool.shardingTablesCheckAndCreatAndReturn(logicTableName, resultTableName.toString());
	}

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
		Range<Date> valueRange = shardingValue.getValueRange();
		Date lowerDate = valueRange.lowerEndpoint();
		Date upperDate = valueRange.upperEndpoint();
		List<String> tableNameList = new ArrayList<>();
		System.out.println(DateUtil.rangeToList(DateUtil.beginOfDay(lowerDate), DateUtil.endOfDay(upperDate), DateField.DAY_OF_YEAR));
		for (DateTime dateTime : DateUtil.rangeToList(DateUtil.beginOfDay(lowerDate), DateUtil.endOfDay(upperDate), DateField.DAY_OF_YEAR)) {
			String resultTableName = shardingValue.getLogicTableName() + DateUtil.format(dateTime, "_yyyyMM");
			if (ShardingAlgorithmTool.shardingTablesExistsCheck(resultTableName)) {
				tableNameList.add(resultTableName);
			}
		}
		return tableNameList;
	}

	@Override
	public Properties getProps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(Properties props) {
		// TODO Auto-generated method stub

	}

}
