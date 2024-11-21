package com.alibaba.excel.read.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisFinishException;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @Description: 指定读取的最大行数
 * @Author: yichengdong
 * @CreateDate: 2024/11/20 18:11
 */
public class MaxRowReadListener<T> implements ReadListener<T> {

	/**
	 * 读取的最大行ID（索引从0开始）
	 */
	private final int maxRowId;

	private final Consumer<Map<Integer, ReadCellData<?>>> headCconsumer;

	private final Consumer<T> dataConsumer;

	public MaxRowReadListener(int maxRowId,
							  Consumer<Map<Integer, ReadCellData<?>>> headCconsumer,
							  Consumer<T> dataConsumer) {
		this.maxRowId = maxRowId;
		this.headCconsumer = headCconsumer;
		this.dataConsumer = dataConsumer;
	}

	@Override
	public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
		if (headCconsumer != null) {
			headCconsumer.accept(headMap);
		}
		canReadNextRow(context);
	}


	@Override
	public void invoke(T data, AnalysisContext context) {
		if (dataConsumer != null) {
			dataConsumer.accept(data);
		}
		canReadNextRow(context);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {

	}

	private void canReadNextRow(AnalysisContext context) {
		ReadRowHolder readRowHolder = context.readRowHolder();
		int rowIndex = readRowHolder.getRowIndex();
		if (rowIndex < maxRowId) {
			return;
		}
		throw new ExcelAnalysisFinishException();
	}
}
